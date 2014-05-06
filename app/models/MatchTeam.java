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
	
	@ManyToOne
	public Team team;
	
	public Long goals;
	public Long penaltyGoals;
	
	public MatchTeam(Team team) {
		this.team = team;
	}
	
	public static Finder<Long,MatchTeam> find = new Finder<Long,MatchTeam>(
			Long.class, MatchTeam.class
    );
	
	public static MatchTeam create(Team team) {
		MatchTeam matchTeam = new MatchTeam(team);
		matchTeam.save();
		
		return matchTeam;
	}
}
