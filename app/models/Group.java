package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "groupteam")
public class Group extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;
	public String name;
	
	public Group(String group) {
		this.name = group;
	}
	
	public static Finder<Long,Group> find = new Finder<Long,Group>(
		Long.class, Group.class
    ); 
	
	public static List<Group> all() {
		return find.all();
	}
}
