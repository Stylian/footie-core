package main.java.dtos.games;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import main.java.dtos.Team;
import main.java.dtos.groups.RobinGroup;

@Entity
@DiscriminatorValue(value = "GG")
public class GroupGame extends Game {

	@ManyToOne(cascade = CascadeType.ALL)
	private RobinGroup robinGroup;
	
	public GroupGame() {
	}
	
	public GroupGame(Team homeTeam, Team awayTeam, RobinGroup robinGroup) {
		super(homeTeam, awayTeam);
		this.robinGroup = robinGroup;
	}

	public RobinGroup getRobinGroup() {
		return robinGroup;
	}
	
}