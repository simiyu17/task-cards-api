
package com.card.auth.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author simiyu
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(@NotBlank String userName);
}
