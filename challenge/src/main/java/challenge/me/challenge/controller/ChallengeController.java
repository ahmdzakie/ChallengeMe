package challenge.me.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.ChallengeRepository;
import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.exceptions.ChallengeNotFoundException;
import challenge.me.challenge.exceptions.UserNotFoundException;
import challenge.me.challenge.model.Challenge;
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
public class ChallengeController {

    @Autowired
    private ChallengeRepository challengeRepository;
	
    @Autowired
    private UserRepository userRepository;
    
    
    // get all challenges
    @GetMapping("/challenges")
    public List < Challenge > getAllChallenges() {
        return challengeRepository.findAll();
    }
    
    // get a specific challenge
    @GetMapping("/challenges/{id}")
    public Challenge getChallenge(@PathVariable(value="id") int challengeId) {
    	
    	Optional<Challenge> challenge = challengeRepository.findById(challengeId);
    	if(!challenge.isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challengeId);
    	}
    	
    	return challenge.get();
    }
    
    // get all challenges created by specific user
    @GetMapping("/users/{id}/challenges")
    public List<Challenge> getChallengesByCreator(@PathVariable(value="id") int creatorId) {
    	
    	Optional<User> user = userRepository.findById(creatorId);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("id-" + creatorId);
    	}
    	
    	List<Challenge> challenges = challengeRepository.findByCreatorId(creatorId);
    	
    	return challenges;
    }
    
    
    // create a challenge
    @PostMapping("/users/{id}/challenges")
    public ResponseEntity<Challenge> createChallenge(@PathVariable(value = "id") int creatorId, @Valid @RequestBody Challenge challenge) {
    	
    	Optional<User> user = userRepository.findById(creatorId);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("id-" + creatorId);
    	}
    	
    	challenge.setCreator(user.get());
    	
    	Challenge savedChallenge = challengeRepository.save(challenge);
    	
    	ResponseEntity<Challenge> responseEntity = new ResponseEntity<Challenge>(savedChallenge, HttpStatus.CREATED);
    	
    	return responseEntity;
    }
    
    
    // update a specific challenge
    @PutMapping("/users/{userId}/challenges/{challengeId}")
    public Challenge updateChallenge(@PathVariable(value="userId") int creatorId, @Valid @RequestBody Challenge challenge) {
    	
    	if(!challengeRepository.findById(challenge.getId()).isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challenge.getId());
    	}
    	
    	Optional<User> user = userRepository.findById(creatorId);
    	if(!user.isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challenge.getId());
    	}
    	
    	challenge.setCreator(user.get());
    	Challenge savedChallenge = challengeRepository.save(challenge);
    	
    	return savedChallenge;
    }
    
    // delete a specific challenge
    @DeleteMapping("/challenges/{id}")
    public void deleteChallenge(@PathVariable(value="id") int challengeId) {
    	
    	Optional<Challenge> challenge = challengeRepository.findById(challengeId);
    	if(!challenge.isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challengeId);
    	}
    	
    	challengeRepository.delete(challenge.get());
    }
}
