package com.scm.smartContactManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.scm.smartContactManager.entities.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByEmailToken(String emailToken);
}
