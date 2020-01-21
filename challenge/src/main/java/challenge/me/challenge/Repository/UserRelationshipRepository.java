package challenge.me.challenge.Repository;

import challenge.me.challenge.model.UserRelationship;
import challenge.me.challenge.model.UserRelationshipId;
import challenge.me.challenge.model.UserRelationshipType;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UserRelationshipId>{
	
	// UserRelationshipType findBy first user and second user
	Optional<UserRelationship> findByIdFirstUserIdAndIdSecondUserId(int firstUserId, int secondUserId);
	
	// ArrayList<UserRelationship> findBy first user or second user
	ArrayList<UserRelationship> findByIdFirstUserIdOrIdSecondUserId(int firstUserId, int secondUserId);
	
	Optional<UserRelationship> findByIdFirstUserIdAndIdSecondUserIdAndRelationshipType(int firstUserId, int secondUserId, UserRelationshipType relationshipType);
}