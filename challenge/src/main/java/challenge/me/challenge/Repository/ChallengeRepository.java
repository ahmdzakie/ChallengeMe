package challenge.me.challenge.Repository;

import challenge.me.challenge.model.Challenge;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{
	 List<Challenge> findByCreatorId(int creatorId);
}