package challenge.me.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChallengeNotFoundException extends RuntimeException{

	public ChallengeNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public ChallengeNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
