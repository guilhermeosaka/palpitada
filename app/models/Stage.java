package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Transactional;

@Entity
@Table(name = "stage")
public class Stage extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@OneToMany
	public List<StageName> names; //Multi-language
	
	@OneToMany
	public List<Match> matches;
	
	public static Finder<Long,Stage> find = new Finder<Long,Stage>(
		Long.class, Stage.class
    );
	
	@Transactional
	public static Stage create(Map<String, String> names) {
		Stage stage = new Stage();
		stage.save();
		for (Map.Entry<String, String> name : names.entrySet()) {
			StageNameKey stageNameKey = new StageNameKey(name.getKey(), stage.id);
			StageName stageName = new StageName(stageNameKey, name.getValue());
			stageName.save();
		}
		
		return stage;
	}
}
