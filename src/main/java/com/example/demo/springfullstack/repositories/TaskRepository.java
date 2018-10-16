package com.example.demo.springfullstack.repositories;

import com.example.demo.springfullstack.entities.Task;
import com.example.demo.springfullstack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}
