package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import play.db.ebean.Model;

@Entity
public class TeamName extends Model {
	@EmbeddedId
	public TeamNameKey key;
	public String name;

	public TeamName(TeamNameKey key, String name) {
		this.key = key;
		this.name = name;
	}
	
	@MapsId("teamId")
	@ManyToOne
	@JoinColumn(name = "team_id", referencedColumnName = "id", insertable=false, updatable=false)
	public Team team;
}