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
@Table(name = "stagename")
public class StageName extends Model {
	
	@EmbeddedId
	public StageNameKey key;
	public String name;
	
	public StageName(StageNameKey key, String name) {
		this.key = key;
		this.name = name;
	}
	
	@MapsId("stageId")
	@ManyToOne
	@JoinColumn(name = "stage_id", referencedColumnName = "id", insertable=false, updatable=false)
	public Stage stage;
	
	public static Finder<Stage,StageName> find = new Finder<Stage,StageName>(
			Stage.class, StageName.class
    ); 
}
