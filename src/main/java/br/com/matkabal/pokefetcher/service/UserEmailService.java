package br.com.matkabal.pokefetcher.service;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.UserEmail;
import br.com.matkabal.pokefetcher.repositories.UserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEmailService {

    @Autowired
    private UserEmailRepository userEmailRepository;

    public UserEmail saveUserEmail(UserEmail userEmail) throws RuntimeException{
        Optional<UserEmail> subUserEmail = userEmailRepository.findById(userEmail.getEmail());
        if(subUserEmail.isPresent()){
            throw new PokemonException("User already exists");
        }
        return userEmailRepository.save(userEmail);
    }
}
