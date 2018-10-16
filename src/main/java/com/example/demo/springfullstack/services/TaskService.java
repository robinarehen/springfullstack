package com.example.demo.springfullstack.services;

import com.example.demo.springfullstack.entities.Task;
import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task, User user){

        task.setUser(user);

        this.taskRepository.save(task);
    }

    public List<Task> findUserTask(User user){
        return this.taskRepository.findByUser(user);
    }
}
