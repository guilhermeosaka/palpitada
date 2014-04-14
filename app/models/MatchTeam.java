package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "matchteam")
public class MatchTeam extends Model {
	@Id
	@GeneratedValue
	public Long id;
	
	@OneToOne
	public Match match;
	@ManyToOne
	public Team team;
	
	public Long goals;
	public Long penaltyGoals;
	
	public MatchTeam(Match match, Team team) {
		this.match = match;
		this.team = team;
	}
	
	public static Finder<Long,MatchTeam> find = new Finder<Long,MatchTeam>(
			Long.class, MatchTeam.class
    );
	
	public static MatchTeam create(Match match, Team team) {
		MatchTeam matchTeam = new MatchTeam(match, team);
		matchTeam.save();
		
		return matchTeam;
	}
}
