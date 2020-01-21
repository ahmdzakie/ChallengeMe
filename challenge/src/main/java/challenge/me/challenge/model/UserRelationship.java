package challenge.me.challenge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_relationship")
public class UserRelationship implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private UserRelationshipId id;
	
	//@Id
	//@ManyToOne
	//@JoinColumn(name = "first_user_id")
	//@Column(name = "first_user_id")
	//private int firstUserId;
	
	
	//@Id
	//@ManyToOne
	//@JoinColumn(name = "second_user_id")
	//@Column(name = "second_user_id")
	//private int secondUserId;
	
	@Column(name = "relationship_type")
	@Enumerated(EnumType.STRING)
	private UserRelationshipType relationshipType; //TODO
	
	public UserRelationship() {
		// TODO Auto-generated constructor stub
	}

	public UserRelationship(UserRelationshipId userRelationshipId, UserRelationshipType relationshipType) {
		super();
		this.id = userRelationshipId;
		this.relationshipType = relationshipType;
	}

	public UserRelationshipId getId() {
		return id;
	}

	public void setId(UserRelationshipId userRelationshipId) {
		this.id = userRelationshipId;
	}

	public UserRelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(UserRelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public UserRelationship(int firstUserId, int secondUserId, UserRelationshipType relationshipType) {
		super();
		this.firstUserId = firstUserId;
		this.secondUserId = secondUserId;
		this.relationshipType = relationshipType;
	}

	public int getFirstUserId() {
		return firstUserId;
	}

	public void setFirstUserId(int firstUserId) {
		this.firstUserId = firstUserId;
	}

	public int getSecondUserId() {
		return secondUserId;
	}

	public void setSecondUserId(int secondUserId) {
		this.secondUserId = secondUserId;
	}

	public UserRelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(UserRelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
*/
}
