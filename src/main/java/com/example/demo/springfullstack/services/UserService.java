package com.example.demo.springfullstack.services;

import com.example.demo.springfullstack.entities.Role;
import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User create(User user) {

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {

            List<Role> roles = Arrays.asList(new Role("USER"));

            user.setRoles(roles);
        }

        return this.userRepository.save(user);
    }

    public User findOne(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean isUserExists(String email) {
        return this.userRepository.existsById(email);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findByName(String name) {
        name = ("%").concat(name).concat("%");

        return this.userRepository.findByNameLike(name);
    }
}
