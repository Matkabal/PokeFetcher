package br.com.matkabal.pokefetcher.controller;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.Pokemon;
import br.com.matkabal.pokefetcher.service.PokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/api/pokemon/")
public class PokeController {

    @Autowired
    private PokeService pokeService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getPokemon(@PathVariable String name){
        try{
            Pokemon pokemon = pokeService.getPokemon(name);
            return ResponseEntity.status(HttpStatus.OK).body(pokemon);
        }catch (PokemonException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", exception.getMessage(),
                    "message", "Use the name of an existing pokemon"
            ));
        }

    }
}
