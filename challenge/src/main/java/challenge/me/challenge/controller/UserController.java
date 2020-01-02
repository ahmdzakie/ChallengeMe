package challenge.me.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.model.User;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
	
    @GetMapping("/users")
    public List < User > getUsers() {
        return userRepository.findAll();
    }
    
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}
