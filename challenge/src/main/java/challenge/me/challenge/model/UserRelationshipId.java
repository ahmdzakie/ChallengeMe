package challenge.me.challenge.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserRelationshipId implements Serializable {

	//private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "first_user_id")
	private User firstUser;
	
	@ManyToOne
	@JoinColumn(name = "second_user_id")
	private User secondUser;
	
	public UserRelationshipId() {
	}

	
	
	public UserRelationshipId(User firstUser, User secondUser) {
		super();
		this.firstUser = firstUser;
		this.secondUser = secondUser;
	}



	public User getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(User firstUser) {
		this.firstUser = firstUser;
	}

	public User getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(User secondUser) {
		this.secondUser = secondUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstUser == null) ? 0 : firstUser.hashCode());
		result = prime * result + ((secondUser == null) ? 0 : secondUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRelationshipId other = (UserRelationshipId) obj;
		if (firstUser == null) {
			if (other.firstUser != null)
				return false;
		} else if (!firstUser.equals(other.firstUser))
			return false;
		if (secondUser == null) {
			if (other.secondUser != null)
				return false;
		} else if (!secondUser.equals(other.secondUser))
			return false;
		return true;
	}
	
	
	
	
	/*
	private int firstUserId;
	
	private int secondUserId;
	
	public UserRelationshipId() {
	}

	public UserRelationshipId(int firstUserId, int secondUserId) {
		super();
		this.firstUserId = firstUserId;
		this.secondUserId = secondUserId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstUserId;
		result = prime * result + secondUserId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRelationshipId other = (UserRelationshipId) obj;
		if (firstUserId != other.firstUserId)
			return false;
		if (secondUserId != other.secondUserId)
			return false;
		return true;
	}

	*/
		

}
