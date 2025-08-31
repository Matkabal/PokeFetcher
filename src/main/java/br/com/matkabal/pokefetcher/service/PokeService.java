package br.com.matkabal.pokefetcher.service;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PokeService {

    @Value("${pokeapi.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Pokemon getPokemon(String name) throws PokemonException{
        try{
            String url = baseUrl + "/pokemon/" + name;
            return restTemplate.getForObject(url, Pokemon.class);
        }catch (HttpClientErrorException exception){
            throw new PokemonException("Pokemon doesn't exist");
        }
    }
}
