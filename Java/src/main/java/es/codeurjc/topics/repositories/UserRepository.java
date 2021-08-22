package es.codeurjc.topics.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.topics.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findById(Long userId);

}
