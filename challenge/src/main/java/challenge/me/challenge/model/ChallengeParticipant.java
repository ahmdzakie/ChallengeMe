package challenge.me.challenge.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "challenge_participant")
public class ChallengeParticipant {

	@Id
	@GeneratedValue
	@Column(name = "challenge_participant_id")
	private int challengeParticipantId;
	
	
	@ManyToOne
	@JoinColumn(name = "challenge_id")
	private Challenge challenge;
	
	@ManyToOne
	@JoinColumn(name = "challenger_id")
	private User challenger;
	
	
	@ManyToOne
	@JoinColumn(name = "participant_id")	
	private User participant;
	
	
	public ChallengeParticipant() {
	}

	public ChallengeParticipant(Challenge challenge, User challenger, User participant) {
		super();
		this.challenge = challenge;
		this.challenger = challenger;
		this.participant = participant;
	}



	public int getChallengeParticipantId() {
		return challengeParticipantId;
	}


	public void setChallengeParticipantId(int challengeParticipantId) {
		this.challengeParticipantId = challengeParticipantId;
	}


	public Challenge getChallenge() {
		return challenge;
	}


	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}


	public User getChallenger() {
		return challenger;
	}


	public void setChallenger(User challenger) {
		this.challenger = challenger;
	}


	public User getParticipant() {
		return participant;
	}


	public void setParticipant(User participant) {
		this.participant = participant;
	}
	
}
