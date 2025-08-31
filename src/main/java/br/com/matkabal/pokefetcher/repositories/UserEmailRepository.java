package br.com.matkabal.pokefetcher.repositories;

import br.com.matkabal.pokefetcher.model.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailRepository extends JpaRepository<UserEmail, String> {
}
