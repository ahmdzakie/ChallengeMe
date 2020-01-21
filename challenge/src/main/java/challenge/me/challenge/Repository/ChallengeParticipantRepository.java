package challenge.me.challenge.Repository;

import challenge.me.challenge.model.ChallengeParticipant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Integer>{
	 ArrayList<ChallengeParticipant> findByChallengeId(int challengeId);
	 ArrayList<ChallengeParticipant> findByParticipantId(int participantId);
}