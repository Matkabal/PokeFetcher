package br.com.matkabal.pokefetcher.controller;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.Pokemon;
import br.com.matkabal.pokefetcher.service.PokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/api/pokemon/")
@Slf4j
public class PokeController {

    @Autowired
    private PokeService pokeService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getPokemon(@PathVariable String name){
        try{
            Pokemon pokemon = pokeService.getPokemon(name);
            boolean isSend = pokeService.publishPokemon(pokemon);
            if(isSend){
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        name, "data about the pokemon sent to the email of registered users"
                ));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "error", "Unable to send email",
                        "message", "Pokemon exists, but unable to send email"
                ));
            }
        }catch (PokemonException exception){
            log.error("[Error in PokeController]", exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", exception.getMessage(),
                    "message", "Use the name of an existing pokemon"
            ));
        }

    }
}
