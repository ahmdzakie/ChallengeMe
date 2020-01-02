package challenge.me.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.ChallengeRepository;
import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.model.Challenge;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ChallengeController {

    @Autowired
    private ChallengeRepository challengeRepository;
	
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/challenges")
    public List < Challenge > getChallenges() {
        return challengeRepository.findAll();
    }
    
    @PostMapping("/user/{creatorId}/challenges")
    public Challenge createChallenges(@PathVariable(value = "creatorId") int creatorId, @Valid @RequestBody Challenge challenge) {
    	return userRepository.findById(creatorId).map(user -> {
            challenge.setCreator(user);
            return challengeRepository.save(challenge); 
        }).get();
    }
    
}
