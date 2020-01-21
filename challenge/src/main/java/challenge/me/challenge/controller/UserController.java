package challenge.me.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.exceptions.UserNotFoundException;
import challenge.me.challenge.model.User;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
	
    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // get specific user by id
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value="id") int userId) {
    	Optional<User> user = userRepository.findById(userId);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("id-" + userId);
    	}
    	
    	return user.get();
    }
    
    // get user by email
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email){
    	Optional<User> user = userRepository.findByEmail(email);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("email-" + email);
    	}
    	
    	return user.get();
    }
    
    // create a user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        
    	User savedUser = userRepository.save(user);
    	ResponseEntity<User> responseEntity = new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    	return responseEntity;
    }
    
    // update specific user
    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user) {
    	
    	if(!userRepository.findById(user.getId()).isPresent()) {
    		throw new UserNotFoundException("id-" + user.getId());
    	}
    	
    	User savedUser = userRepository.save(user);
    	
    	return savedUser;
    }
    
    // delete specific user
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") int userId) {
    	Optional<User> user = userRepository.findById(userId);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("id-" + userId);
    	}
    	
    	userRepository.delete(user.get());
    }
}
