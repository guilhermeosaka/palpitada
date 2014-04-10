package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "matches")
public class Match extends Model {

	@Id
	@GeneratedValue
	public Long id;
	
	public Stage stage;
	public Group group;
	public Stadium stadium;
	public MatchTeam teamA;
	public MatchTeam teamB;
	public Date date;
	
	public Match(Stage stage, Group group, Stadium stadium, Date date) {
		this.stage = stage;
		this.group = group;
		this.stadium = stadium;
		this.date = date;
	}
	
	public static Finder<Long,Match> find = new Finder<Long,Match>(
		Long.class, Match.class
    );
	
	public static Match create(Stage stage, Group group, Stadium stadium, Team teamA, Team teamB, Date date) {
		Match match = new Match(stage, group, stadium, date);
		match.save();
	
		MatchTeam matchTeamA = MatchTeam.create(match, teamA);
		MatchTeam matchTeamB = MatchTeam.create(match, teamB);
		
		return match;
	}
}
