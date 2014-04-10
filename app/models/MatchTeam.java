package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "matchteam")
public class MatchTeam extends Model {
	@EmbeddedId
	public MatchTeamKey key;
	
	@MapsId("matchid")
	@ManyToOne
	@JoinColumn(name = "match_id", referencedColumnName = "id", insertable=false, updatable=false)
	public Match match;
	
	@MapsId("teamid")
	@ManyToOne
	@JoinColumn(name = "team_id", referencedColumnName = "id", insertable=false, updatable=false)
	public Team team;
	
	public MatchTeam(MatchTeamKey key) {
		this.key = key;
	}
	
	public static Finder<MatchTeamKey,MatchTeam> find = new Finder<MatchTeamKey,MatchTeam>(
			MatchTeamKey.class, MatchTeam.class
    );
	
	public static MatchTeam create(Match match, Team team) {
		MatchTeamKey key = new MatchTeamKey(match.id, team.id);
		MatchTeam matchTeam = new MatchTeam(key);
		matchTeam.save();
		
		return matchTeam;
	}
}
