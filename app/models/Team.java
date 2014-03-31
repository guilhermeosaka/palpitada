package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Transactional;

@Entity
@Table(name = "team")
public class Team extends Model {
	
	public enum GroupTeam {
		A,
		B,
		C,
		D,
		E,
		F,
		G,
		H
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	public Long id;
	public String logo; //Path of the logo (eg. "logo.png")
	
	@OneToMany
	public List<TeamName> names; //Multi-language

	@Enumerated(EnumType.STRING)
	public GroupTeam groupTeam;
	
	public Team(String logo, GroupTeam groupTeam) {
		this.logo = logo;
		this.groupTeam = groupTeam;
	}
	
	public static Finder<Long,Team> find = new Finder<Long,Team>(
		Long.class, Team.class
    ); 
	
	@Transactional
	public static Team create(Map<String, String> names, String logo, GroupTeam group) {
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