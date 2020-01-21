package challenge.me.challenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.ChallengeParticipantRepository;
import challenge.me.challenge.Repository.ChallengeRepository;
import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.exceptions.ChallengeNotFoundException;
import challenge.me.challenge.exceptions.UserNotFoundException;
import challenge.me.challenge.model.Challenge;
import challenge.me.challenge.model.ChallengeParticipant;
import challenge.me.challenge.model.User;


@RestController
public class ChallengeParticipantController {

	@Autowired
    private ChallengeParticipantRepository challengeParticipantRepository;
	
	@Autowired
    private ChallengeRepository challengeRepository;
	
	@Autowired
    private UserRepository userRepository;

    
    // get all participants in specific challenge
    @GetMapping("/challenges/{id}/participants")
    public ArrayList<ChallengeParticipant> getAllParticipantsInChallenge(@PathVariable(value="id") int challengeId) {
    	
    	Optional<Challenge> challenge = challengeRepository.findById(challengeId);
    	if(!challenge.isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challengeId);
    	}
    	
    	ArrayList<ChallengeParticipant> participants = challengeParticipantRepository.findByChallengeId(challengeId);
    	
    	return participants;
    }
    
    // get all challenges for specific participant
    @GetMapping("/participants/{id}/challenges")
    public ArrayList<ChallengeParticipant> getAllChallengesForParticipant(@PathVariable(value="id") int userId) {
    	
    	Optional<User> user = userRepository.findById(userId);
    	if(!user.isPresent()) {
    		throw new UserNotFoundException("id-" + userId);
    	}
    	
    	ArrayList<ChallengeParticipant> participants = challengeParticipantRepository.findByParticipantId(userId);
    	
    	return participants;
    }
    
    
    
    // create one participant in a challenge
    @PostMapping("/users/{challengerId}/challenges/{challengeId}/participants/{participantId}")
    public ResponseEntity<ChallengeParticipant> createChallengeParticipant(@PathVariable(value="challengeId") int challengeId, @PathVariable(value="challengerId") int challengerId, @PathVariable int participantId) {
    	
    	ArrayList<Integer> participants = new ArrayList<Integer>();
    	participants.add(participantId);
    	ResponseEntity<List<ChallengeParticipant>> responseEntity = createChallengeParticipants(challengeId, challengerId, participants);
    	
    	return new ResponseEntity<ChallengeParticipant>(responseEntity.getBody().get(0), responseEntity.getStatusCode());
    }
    
    // create many participants in a challenge using participant ids
    @PostMapping("/users/{challengerId}/challenges/{challengeId}/participants")
    public ResponseEntity<List<ChallengeParticipant>> createChallengeParticipants(@PathVariable(value="challengeId") int challengeId, @PathVariable(value="challengerId") int challengerId, @RequestBody ArrayList<Integer> participantsIds) {
    	
		Optional<Challenge> challenge = challengeRepository.findById(challengeId);
    	Optional<User> challenger = userRepository.findById(challengerId);
    	
    	if(!challenge.isPresent()) {
    		throw new ChallengeNotFoundException("id-" + challengeId);
    	}
    	if(!challenger.isPresent()) {
    		throw new UserNotFoundException("id-" + challengerId);
    	}
    	
    	List<ChallengeParticipant> challengeParticipants = participantsIds.stream().map(participantId -> {
    		Optional<User> participant = userRepository.findById(participantId);
    		if(!participant.isPresent()) {
        		throw new UserNotFoundException("id-" + participantId);
        	}
    		return new ChallengeParticipant(challenge.get(), challenger.get(), participant.get());
    	}).collect(Collectors.toList());
    	
    	return createMultipleChallengeParticipants(challengeParticipants);
    }
    
    
    // create many participants in a challenge using participant objects
    @PostMapping("/challenges/{challengeId}/participants")
    public ResponseEntity<List<ChallengeParticipant>> createMultipleChallengeParticipants(@RequestBody List<ChallengeParticipant> challengeParticipants) {
    	
    	List<ChallengeParticipant> savedChallengeParticipants = challengeParticipantRepository.saveAll(challengeParticipants);
    	
    	ResponseEntity<List<ChallengeParticipant>> responseEntity = new ResponseEntity<List<ChallengeParticipant>>(savedChallengeParticipants, HttpStatus.CREATED);
    	
    	return responseEntity;
    }
    
    
    
    
    
    
    
    // delete challenge participant
    @DeleteMapping("/challenge/{challengeId}/participants/{challengeParticipantId}")
    public void deleteChallengeParticipant(@PathVariable(value="challengeParticipantId") int challengeParticipantId) {
    	
    	challengeParticipantRepository.deleteById(challengeParticipantId);
    }
    
}


