package br.com.matkabal.pokefetcher.service;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static br.com.matkabal.pokefetcher.config.RabbitConfig.*;

@Service
@Slf4j
public class PokeService {

    @Value("${pokeapi.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public Pokemon getPokemon(String name) {
        try{
            String url = baseUrl + "/pokemon/" + name;
            return restTemplate.getForObject(url, Pokemon.class);
        }catch (HttpClientErrorException exception){
            throw new PokemonException();
        }
    }

    public boolean publishPokemon(Pokemon pokemon){
        try {
            String id = UUID.randomUUID().toString();

            MessagePostProcessor mpp = msg -> {
                msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                msg.getMessageProperties().setHeader("x-source", "poke-fetcher");
                msg.getMessageProperties().setHeader("x-event", ROUTING_KEY);
                msg.getMessageProperties().setMessageId(id);
                return msg;
            };

            CorrelationData correlation = new CorrelationData(id);

            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, pokemon, mpp, correlation);
            return true;
        } catch (Exception e) {
            log.error("[Error in PokeService][{}]",e.getMessage(), e);
            throw new PokemonException("Pokemon do not send");
        }
    }
}


