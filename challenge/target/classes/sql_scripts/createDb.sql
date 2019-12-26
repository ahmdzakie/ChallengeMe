CREATE DATABASE challenge_me;

USE challenge_me;

CREATE TABLE user(
	user_id INT AUTO_INCREMENT
	, first_name VARCHAR(100)
	, last_name VARCHAR(100)
	, email VARCHAR(50) UNIQUE
	, password VARCHAR(20)
	, birth_date DATETIME
	, register_date DATETIME
	, PRIMARY KEY(user_id)
);



# Lookup table #
CREATE TABLE user_relationship_type(
	relationship_type_id INT AUTO_INCREMENT
	, relationship_type_description VARCHAR(100)
	, PRIMARY KEY(relationship_type_id)
);
#


CREATE TABLE user_relationship(
	first_user_id INT
	, second_user_id INT
	, relationship_type INT
	, PRIMARY KEY(first_user_id, second_user_id)
	, CONSTRAINT user_id_order CHECK (first_user_id < second_user_id)
	, FOREIGN KEY (first_user_id) REFERENCES user(user_id)
	, FOREIGN KEY (second_user_id) REFERENCES user(user_id)
	, FOREIGN KEY (relationship_type) REFERENCES user_relationship_type(relationship_type_id)
);


CREATE TABLE challenge(
	challenge_id INT AUTO_INCREMENT
	, description VARCHAR(500)
	, type ENUM ('one_time', 'cyclic')
	, span INT
	, creator_id INT
	, PRIMARY KEY (challenge_id)
	, FOREIGN KEY (creator_id) REFERENCES user(user_id)	
);


CREATE TABLE cyclic_challenge(
	challenge_id INT
	, cycle_days INT
	, start_count INT
	, is_incremental BOOL
	, increment_by INT
	, measuring_unit ENUM ('seconds', 'minutes', 'counts', 'meters', 'kilometers')
	, PRIMARY KEY (challenge_id)
	, FOREIGN KEY (challenge_id) REFERENCES challenge(challenge_id)
);


CREATE TABLE challenge_participant(
	challenge_participant_id INT AUTO_INCREMENT
	, challenge_id INT
	, challenger_id INT
	, participant_id INT
	, PRIMARY KEY(challenge_participant_id)
	, UNIQUE KEY (challenge_id, participant_id)
);


CREATE TABLE challenge_breakdown(
	challenge_breakdown_id INT
	, challenge_participant_id INT
	, challenge_id INT
	, is_done BOOL
	, PRIMARY KEY (challenge_participant_id, challenge_breakdown_id)
	, FOREIGN KEY (challenge_participant_id) REFERENCES challenge_participant(challenge_participant_id)
	, FOREIGN KEY (challenge_id) REFERENCES challenge(challenge_id)
);



CREATE TABLE challenge_participant_status(
	challenge_participant_id INT
	, play_status ENUM ('in_progress', 'failed', 'done')
	, init_status ENUM ('pending', 'accepted', 'rejected')
	, completion_percentage INT
	, PRIMARY KEY (challenge_participant_id)
	, FOREIGN KEY (challenge_participant_id) REFERENCES challenge_participant(challenge_participant_id)
);