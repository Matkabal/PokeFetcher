package br.com.matkabal.pokefetcher.controller;

import br.com.matkabal.pokefetcher.exceptions.PokemonException;
import br.com.matkabal.pokefetcher.model.UserEmail;
import br.com.matkabal.pokefetcher.service.UserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/user/email")
public class UserEmailController {

    @Autowired
    private UserEmailService userEmailService;

    @PostMapping("/add")
    public ResponseEntity<UserEmail> registerEmail(@RequestBody UserEmail email){

        try{
            UserEmail userEmail = userEmailService.saveUserEmail(email);
            return ResponseEntity.status(HttpStatus.CREATED).body(userEmail);
        }catch (PokemonException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }
}
