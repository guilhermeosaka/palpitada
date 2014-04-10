package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "stadium")
public class Stadium extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	public String name;
	public String image;
	public String city;
	public String state;
	public Long capacity;
	
	public Stadium(String name, String image, String city, String state, Long capacity) {
		this.name = name;
		this.image = image;
		this.city = city;
		this.state = state;
		this.capacity = capacity;
	}
	
	public static Finder<Long,Stadium> find = new Finder<Long,Stadium>(
		Long.class, Stadium.class
    );
	
	public static Stadium create(String name, String image, String city, String state, Long capacity) {
		Stadium stadium = new Stadium(name, image, city, state, capacity);
		stadium.save();
		
		return stadium;
	}
}
