package br.com.matkabal.pokefetcher.exceptions;

import br.com.matkabal.pokefetcher.model.Pokemon;

public class PokemonException extends RuntimeException{
    public PokemonException(){
        super();
    }
    public PokemonException(String message){
        super(message);
    }
}
