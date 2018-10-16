package com.example.demo.springfullstack.repositories;

import com.example.demo.springfullstack.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
