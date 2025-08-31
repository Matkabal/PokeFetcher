package br.com.matkabal.pokefetcher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEmail {

    @Id
    private String email;
    private String name;
}
