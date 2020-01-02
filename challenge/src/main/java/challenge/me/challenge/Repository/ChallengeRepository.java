package challenge.me.challenge.Repository;

import challenge.me.challenge.model.Challenge;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{
	 Challenge findByCreatorId(int creatorId);
}