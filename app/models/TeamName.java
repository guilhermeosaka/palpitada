package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class TeamName extends Model {
	@EmbeddedId
	public TeamNameKey key;
	public String name;
	
	@MapsId("teamId")
	@ManyToOne
	@JoinColumn(name = "team_id", referencedColumnName = "id", insertable=false, updatable=false)
	public Team team;

	public TeamName(TeamNameKey key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public static Finder<TeamNameKey,TeamName> find = new Finder<TeamNameKey,TeamName>(
			TeamNameKey.class, TeamName.class
    ); 
}
