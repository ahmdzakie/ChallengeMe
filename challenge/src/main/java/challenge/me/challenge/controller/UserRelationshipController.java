package challenge.me.challenge.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.me.challenge.Repository.UserRelationshipRepository;
import challenge.me.challenge.Repository.UserRepository;
import challenge.me.challenge.exceptions.UserNotFoundException;
import challenge.me.challenge.model.User;
import challenge.me.challenge.model.UserRelationship;
import challenge.me.challenge.model.UserRelationshipId;
import challenge.me.challenge.model.UserRelationshipType;

@RestController
public class UserRelationshipController {

	@Autowired
    private UserRelationshipRepository userRelationshipRepository;
	
	@Autowired
    private UserRepository userRepository;

    
    // get relationship between two users
	// GET users/relationship?user1&user2
	@GetMapping("/relationships/users/{userId1}/{userId2}")
	public UserRelationship getRelationshipBetweenUsers(@PathVariable(value="userId1") int firstUserId, @PathVariable(value="userId2") int secondUserId) {
		
		Optional<User> firstUser = userRepository.findById(firstUserId);
		if(!firstUser.isPresent()) {
			throw new UserNotFoundException("id-" + firstUserId);
		}
		
		if(firstUserId == secondUserId) {
			return new UserRelationship(new UserRelationshipId(firstUser.get(), firstUser.get()), UserRelationshipType.same_user);
		}
		
		Optional<User> secondUser = userRepository.findById(secondUserId);
		if(!secondUser.isPresent()) {
			throw new UserNotFoundException("id-" + secondUserId);
		}
		
		Optional<UserRelationship> userRelationship;
		if(firstUserId < secondUserId) {
			userRelationship = userRelationshipRepository.findByIdFirstUserIdAndIdSecondUserId(firstUserId, secondUserId);
		}
		else {
			userRelationship = userRelationshipRepository.findByIdFirstUserIdAndIdSecondUserId(secondUserId, firstUserId);
		}
		
		if(!userRelationship.isPresent()) {
			return new UserRelationship(new UserRelationshipId(firstUser.get(), secondUser.get()), UserRelationshipType.not_related);
		}
		
		return userRelationship.get();
	}
	
	// get all friends of a specific user
	@GetMapping("/users/{id}/friends")
	public ArrayList<User> getFriends(@PathVariable(value="id") int userId){
		
		ArrayList<UserRelationship> relatedUsers = getRelationships(userId);
		
		Stream<UserRelationship> friendships = relatedUsers.stream().filter(relationship -> relationship.getRelationshipType().equals(UserRelationshipType.friends));
		
		return getRelatedUsers(friendships, userId);
	}
	
	// get all friend requests sent to a specific user
	@GetMapping("/users/{id}/friendRequests")
	public ArrayList<User> getFriendshipRequests(@PathVariable(value="id") int userId){
			
			ArrayList<UserRelationship> relatedUsers = getRelationships(userId);
			
			Stream<UserRelationship> friendRequests = relatedUsers.stream().filter(relationship -> (relationship.getId().getFirstUser().getId() == userId && relationship.getRelationshipType().equals(UserRelationshipType.pending_second_first))
					|| (relationship.getId().getSecondUser().getId() == userId && relationship.getRelationshipType().equals(UserRelationshipType.pending_first_second)));
			
			return getRelatedUsers(friendRequests, userId);
	}
	
	// get all users associated with specific user by certain relationship
	protected ArrayList<UserRelationship> getRelationships(int userId) {
		
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-" + userId);
		}
		
		ArrayList<UserRelationship> relatedUsers = userRelationshipRepository.findByIdFirstUserIdOrIdSecondUserId(userId, userId);
		
		return relatedUsers;
	}
	
	protected ArrayList<User> getRelatedUsers(Stream<UserRelationship> relationships, int userId){
	
		ArrayList<User> otherUsers = new ArrayList<User>();
		relationships.forEach(
				f -> {if(f.getId().getFirstUser().getId() != userId ) {otherUsers.add(f.getId().getFirstUser());}
				else {otherUsers.add(f.getId().getSecondUser());}}
				);
		
		return otherUsers;
	}
	
	// add new relationship between two specific users (add)
	// creates a relationship of pending
	// POST /users/relationships
	@PostMapping("/relationships/users/{subjectId}/{objectId}/add")
	public ResponseEntity<UserRelationship> addUser(@PathVariable(value="subjectId") int subjectUserId, @PathVariable(value = "objectId") int objectUserId){
		Optional<User> subjectUser = userRepository.findById(subjectUserId);
		if(!subjectUser.isPresent()) {
			throw new UserNotFoundException("id-" + subjectUserId);
		}
		
		if(subjectUserId == objectUserId) {
			throw new RuntimeException("Error 409 - Conflict");
		}
		
		Optional<User> objectUser = userRepository.findById(objectUserId);
		if(!objectUser.isPresent()) {
			throw new UserNotFoundException("id-" + objectUserId);
		}
		
		UserRelationship newRelationship;
		UserRelationshipId relationshipId;
		if(subjectUserId < objectUserId) {
			relationshipId = new UserRelationshipId(subjectUser.get(), objectUser.get());
			newRelationship = new UserRelationship(relationshipId, UserRelationshipType.pending_first_second);
		}
		else {
			relationshipId = new UserRelationshipId(objectUser.get(), subjectUser.get());
			newRelationship = new UserRelationship(relationshipId, UserRelationshipType.pending_second_first);
		}
		
		
		Optional<UserRelationship> userRelationship = userRelationshipRepository.findByIdFirstUserIdAndIdSecondUserId(relationshipId.getFirstUser().getId(), relationshipId.getSecondUser().getId());
		if(userRelationship.isPresent()) {
			throw new RuntimeException("Error 409 - Conflict");
		}
		

		UserRelationship savedRelationship = userRelationshipRepository.save(newRelationship);
		
		return new ResponseEntity<UserRelationship>(savedRelationship, HttpStatus.CREATED);
	}
	
	
	
	// update existing relationship between two specific users
	// accept
	@PutMapping("/relationships/users/{subjectId}/{objectId}/accept")
	public UserRelationship acceptFriendship(@PathVariable(value="subjectId") int subjectUserId, @PathVariable(value = "objectId") int objectUserId){
		Optional<User> subjectUser = userRepository.findById(subjectUserId);
		if(!subjectUser.isPresent()) {
			throw new UserNotFoundException("id-" + subjectUserId);
		}
		
		if(subjectUserId == objectUserId) {
			throw new RuntimeException("Error 409 - Conflict");
		}
		
		Optional<User> objectUser = userRepository.findById(objectUserId);
		if(!objectUser.isPresent()) {
			throw new UserNotFoundException("id-" + objectUserId);
		}
		
		Optional<UserRelationship> friendRequest;
		if(subjectUserId < objectUserId) {
			friendRequest = userRelationshipRepository.findByIdFirstUserIdAndIdSecondUserIdAndRelationshipType(subjectUserId, objectUserId, UserRelationshipType.pending_second_first);
		}
		else {
			friendRequest = userRelationshipRepository.findByIdFirstUserIdAndIdSecondUserIdAndRelationshipType(objectUserId, subjectUserId, UserRelationshipType.pending_first_second);
		}
			
		if(!friendRequest.isPresent()) {
			throw new RuntimeException("Error 404 - UserRelationshipNotFound");
		}
		
		friendRequest.get().setRelationshipType(UserRelationshipType.friends);
		UserRelationship savedRelationship = userRelationshipRepository.save(friendRequest.get());
		
		return savedRelationship;
	}
	
	
	
	// delete relationship between two specific users
	@DeleteMapping("/relationships")
	public void deleteUserRelationship(@RequestBody UserRelationship userRelationship) {
		userRelationshipRepository.delete(userRelationship);
	}
    
}


