package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "team")
public class Team extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;
	public String name;
	public String logo; //Path of the logo (eg. "logo.png")
	@ManyToOne
	public Group group;
	
	public Team(String name, String logo, Group group) {
		this.name = name;
		this.logo = logo;
		this.group = group;
	}
	
	public static Finder<Long,Team> find = new Finder<Long,Team>(
		Long.class, Team.class
    ); 
}