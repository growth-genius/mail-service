package com.gg.mailservice.modules.repository;

import com.gg.mailservice.modules.entity.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Email, Long> {
    
    Optional<Email> findByEmailAddress(String email);

}
