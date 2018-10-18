package com.example.demo.springfullstack.services;

import com.example.demo.springfullstack.entities.Role;
import com.example.demo.springfullstack.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){
        return this.roleRepository.findAll();
    }
}
