package br.com.matkabal.pokefetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class PokeFetcher {

    public static void main(String[] args){
        SpringApplication.run(PokeFetcher.class, args);
    }
}
