package com.pandora.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pandora.todosimple.models.User;


@Respository // notation
// extends faz herança
public interface UserRepository extends JpaRepository<User, Long> {
    
}
