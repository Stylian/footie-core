package main.java.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.DataAccessObject;
import main.java.PropertyUtils;
import main.java.dtos.Season;
import main.java.dtos.Stats;
import main.java.dtos.Team;
import main.java.dtos.rounds.QualsRound;

public class SeasonService {

	final static Logger logger = Logger.getLogger(SeasonService.class);

	private Session session;

	public SeasonService(Session session) {
		this.session = session;
	}

	public void createSeason() {

		Properties properties = PropertyUtils.load();
		String strSeasonNum = properties.getProperty("season");

		int year = Integer.parseInt(strSeasonNum) + 1;
		properties.setProperty("season", Integer.toString(year));
		PropertyUtils.save(properties);

		logger.info("creating season " + year);

		Season season = new Season(year);

		TeamsService teamService = new TeamsService(session);

		List<Team> teams = teamService.listAll();

		for (Team team : teams) {

			new Stats(season, team);

		}

		DataAccessObject<Season> dao = new DataAccessObject<>(session);
		dao.save(season);

	}

	public Season loadCurrentSeason() {

		Properties properties = PropertyUtils.load();
		String strSeasonNum = properties.getProperty("season");

		DataAccessObject<Season> dao = new DataAccessObject<>(session);
		Season season = dao.listByField("GROUPS", "SEASON_YEAR", strSeasonNum).get(0);

		return season;

	}

	public void createQualsRound() {

		TeamsService teamService = new TeamsService(session);
		List<Team> teams = teamService.listAll();

		Season season = loadCurrentSeason();

		QualsRound qualsRound = new QualsRound(season, "1st Qualifying Round");

		// former champion promotes directly , to add later to groups round
		Team formerChampion = teams.remove(0); // TODO

		// top 3 seeded teams promote directly excluding champion, to add later to
		// groups round
		List<Team> top3Seeders = new ArrayList<>();
		top3Seeders.add(teams.remove(0)); // TODO
		top3Seeders.add(teams.remove(0));
		top3Seeders.add(teams.remove(0));

		// 2nd round needs 16 teams so
		int diff = teams.size() - 16;
		
		// so bottom 2*diff go to 1st quals, others directly to 2nd quals
		List<Team> quals1 = new ArrayList<>();
		for(int index = 0; index < 2*diff; index++) {
			quals1.add(teams.remove(0)); // TODO
		}
		
		// remaining go to 2nd quals
		List<Team> quals2 = teams;
		
		for(Team t : quals1)
			System.out.println(t);
		
		System.out.println("---------------");
		
		for(Team t : quals2)
			System.out.println(t);
		
		System.out.println("---------------");

		for(Team t : top3Seeders)
			System.out.println(t);
		
		System.out.println("---------------");
		System.out.println(formerChampion);
		
		
		DataAccessObject<Season> seasonDao = new DataAccessObject<>(session);
		seasonDao.save(season);

	}

}
