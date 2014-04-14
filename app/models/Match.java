package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "matches")
public class Match extends Model {

	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public Stage stage;
	@ManyToOne
	public Group group;
	@ManyToOne
	public Stadium stadiums;
	@OneToOne
	public MatchTeam matchTeamA;
	@OneToOne
	public MatchTeam matchTeamB;
	public Date datetime;
	
	public Match(Stage stage, Group group, Stadium stadium, Date datetime) {
		this.stage = stage;
		this.group = group;
		this.stadiums = stadium;
		this.datetime = datetime;
	}
	
	public static Finder<Long,Match> find = new Finder<Long,Match>(
		Long.class, Match.class
    );
	
	public static Match create(Stage stage, Group group, Stadium stadium, Team teamA, Team teamB, Date datetime) {
		Match match = new Match(stage, group, stadium, datetime);
		match.save();
	
		MatchTeam matchTeamA = MatchTeam.create(match, teamA);
		MatchTeam matchTeamB = MatchTeam.create(match, teamB);
		
		match.matchTeamA = matchTeamA;
		match.matchTeamB = matchTeamB;
		match.save();
		
		return match;
	}
}
