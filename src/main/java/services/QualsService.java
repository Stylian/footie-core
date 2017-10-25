package main.java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.DataAccessObject;
import main.java.PropertyUtils;
import main.java.Utils;
import main.java.dtos.Group;
import main.java.dtos.Matchup;
import main.java.dtos.Season;
import main.java.dtos.Team;
import main.java.dtos.enums.MatchupFormat;
import main.java.dtos.enums.MatchupTieStrategy;
import main.java.dtos.rounds.QualsRound;
import main.java.tools.CoefficientsOrdering;

public class QualsService {

	final static Logger logger = Logger.getLogger(QualsService.class);

	private Session session;

	public QualsService(Session session) {
		this.session = session;
	}
	
	public void setUpQualsRound1() {

		SeasonService seasonService = new SeasonService(session);
		Season season = seasonService.loadCurrentSeason();

		QualsRound roundQuals1 = (QualsRound) season.getRounds().get(0);
		
		setUpQualsRound(roundQuals1);
		
		Properties properties = PropertyUtils.load();
		properties.setProperty("round", "1");
		PropertyUtils.save(properties);
		
	}
	
	public void setUpQualsRound(QualsRound qualsRound) {

		List<Team> teams = qualsRound.getTeams();
		
		DataAccessObject<Group> groupDao = new DataAccessObject<>(session);
		Group master = groupDao.listByField("GROUPS", "NAME", "master").get(0);
		
		Collections.sort(teams, new CoefficientsOrdering(master));
		
		System.out.println("quals " + Utils.toString(teams));
		
		List<Team> strong = new ArrayList<>();
		List<Team> weak = new ArrayList<>();
		
		while(teams.size() > 0) {
			
			strong.add(teams.remove(0));
			weak.add(teams.remove(teams.size() - 1));
			
		}
		
		System.out.println("strong: " + Utils.toString(strong));
		System.out.println("weak: " + Utils.toString(weak));
		
		Collections.shuffle(strong);
		Collections.shuffle(weak);
		
		while(strong.size() > 0) {
			
			qualsRound.addMatchup(new Matchup(
					strong.remove(0),
					weak.remove(0),
					MatchupFormat.FORMAT_IN_OUT_SINGLE,
					MatchupTieStrategy.REPLAY_GAMES
				));
			
		}
		
		System.out.println("matchups " + Utils.toString(qualsRound.getMatchups()));
		
		DataAccessObject<QualsRound> roundDao = new DataAccessObject<>(session);
		roundDao.save(qualsRound);
		
	}
	
}
