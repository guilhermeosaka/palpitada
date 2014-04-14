package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "group_team")
public class Group extends Model {
	@Id
	@GeneratedValue
	public Long id;
	public String name;
	
	@ManyToOne
	public List<Team> teams;
	
	@ManyToOne
	public List<Match> matches;
	
	public Group(String name) {
		this.name = name;
	}
	
	public static Finder<Long,Group> find = new Finder<Long,Group>(
		Long.class, Group.class
    );
	
	public static Group create(String name) {
		Group group = new Group(name);
		group.save();
		
		return group;
	}
}
