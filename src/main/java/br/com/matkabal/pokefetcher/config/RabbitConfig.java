package br.com.matkabal.pokefetcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitConfig {

    @Autowired
    private JacksonConfig jacksonConfig;

    public static final String EXCHANGE = "pokemon.exchange";
    public static final String ROUTING_KEY = "pokemon.created";
    public static final String QUEUE = "pokemon.queue";

    public static final String DLX = "pokemon.dlx";
    public static final String DLQ = "pokemon.queue.dlq";
    public static final String DLQ_ROUTING_KEY = "pokemon.created.dlq";

    @Bean
    public TopicExchange pokemonExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX, true, false);
    }

    @Bean
    public Queue pokemonDlq() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding bindDlq(Queue pokemonDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(pokemonDlq)
                .to(deadLetterExchange)
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public Queue pokemonQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArguments(Map.of(
                        "x-dead-letter-exchange", DLX,
                        "x-dead-letter-routing-key", DLQ_ROUTING_KEY
                ))
                .build();
    }

    @Bean
    public Binding bindPokemonQueue(Queue pokemonQueue, TopicExchange pokemonExchange) {
        return BindingBuilder.bind(pokemonQueue)
                .to(pokemonExchange)
                .with(ROUTING_KEY);
    }



    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        template.setMandatory(true);

        template.setConfirmCallback((CorrelationData correlation, boolean ack, String cause) -> {
            if (!ack) {
                log.error("[PUBLISH-NACK] correlation=" + correlation + " cause=" + cause);
            }
        });

        template.setReturnsCallback(returned -> {
            log.error("[RETURNED] replyCode=" + returned.getReplyCode() +
                    " replyText=" + returned.getReplyText() +
                    " exchange=" + returned.getExchange() +
                    " routingKey=" + returned.getRoutingKey());
        });

        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            CachingConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setDefaultRequeueRejected(false);
        factory.setPrefetchCount(10);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(4);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
