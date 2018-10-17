package com.example.demo.springfullstack;

import com.example.demo.springfullstack.entities.Role;
import com.example.demo.springfullstack.entities.Task;
import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.services.TaskService;
import com.example.demo.springfullstack.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringFullStackApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;


    @Before
    public void initDB(){

        {
            User user = new User("robin@g.c","Robin","abc123");
            if (!this.userService.isUserExists(user.getEmail())) {
                this.userService.create(user);
            }
        }
        {
            User user = new User("robinAdmin@g.c","Robin Admin","abc123");
            user.setRoles(Arrays.asList(new Role("ADMIN")));
            if (!this.userService.isUserExists(user.getEmail())) {
                this.userService.create(user);
            }
        }

        Task task = new Task("19/10/2018","08:00","18:00","You need to work all day");
        User user = this.userService.findOne("robin@g.c");
        this.taskService.addTask(task,user);
    }

    @Test
    public void testTask(){

        User user = this.userService.findOne("robin@g.c");
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmail(),"robin@g.c");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password = "abc123";

        Assert.assertTrue(passwordEncoder.matches(password,user.getPassword()));

        List<Task> tasks = this.taskService.findUserTask(user);
        Assert.assertNotNull(tasks);
    }


}