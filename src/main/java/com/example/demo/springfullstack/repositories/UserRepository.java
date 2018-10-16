package com.example.demo.springfullstack.repositories;

import com.example.demo.springfullstack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
