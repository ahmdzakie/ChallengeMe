package challenge.me.challenge.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "challenge")
public class Challenge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "challenge_id")
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "span")
	private int span;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
	private User creator;		
	
	public Challenge() {

	}

	public Challenge(String description, Type type, int span, User creator) {
        this.description = description;
        this.type = type;
        this.span = span;
        this.creator = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Enum<Type> getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getSpan() {
		return span;
	}

	public void setSpan(int span) {
		this.span = span;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}

