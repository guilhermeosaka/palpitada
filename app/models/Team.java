package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Transactional;

@Entity
@Table(name = "team")
public class Team extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	public String logo; //Path of the logo (eg. "logo.png")
	
	@OneToMany
	public List<TeamName> names; //Multi-language

	@ManyToOne
	@Column(name = "group_team")
	public Group group;
	
	public Team(String logo, Group group) {
		this.logo = logo;
		this.group = group;
	}
	
	@OneToMany
	public List<MatchTeam> matches;
	
	public static Finder<Long,Team> find = new Finder<Long,Team>(
		Long.class, Team.class
    ); 
	
	@Transactional
	public static Team create(Map<String, String> names, String logo, Group group) {
		Team team = new Team(logo, group);
		team.save();
		for (Map.Entry<String, String> name : names.entrySet()) {
			TeamNameKey teamNameKey = new TeamNameKey(name.getKey(), team.id);
			TeamName teamName = new TeamName(teamNameKey, name.getValue());
			teamName.save();
		}
		
		return team;
	}
}