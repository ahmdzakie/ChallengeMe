package challenge.me.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Challenge {
	@Autowired
	private User challenger;
	private String description;
}
