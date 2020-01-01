package challenge.me.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.User2;
import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.model.User;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
//	@RequestMapping("/users")
//	public List<User2> getUsers (){
//		User2 user1 = new User2("Ahmed", "zakiahmed91", "1", "zakiahmed91@gmail.com", "password");
//		return Arrays.asList(user1);
//	}
//	
//	@RequestMapping("/user")
//	public User2 getUser (){
//		User2 user1 = new User2("Ahmed", "zakiahmed91", "1", "zakiahmed91@gmail.com", "password");
//		return user1;
//	}
	
    @GetMapping("/users")
    public List < User > getInstructors() {
        return userRepository.findAll();
    }
}
