package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StageNameKey {
	
	public String language;
	@Column(name = "stage_id")
	public Long stageId;
	
	public StageNameKey(String language, Long stageId) {
		this.language = language;
		this.stageId = stageId;
	}
	
	public int hashCode()
    {
        return language.hashCode() + (int)(long)stageId;
    }

    public boolean equals(Object other)
    {
        if (other != null && (other instanceof StageNameKey))
        {
        	StageNameKey otherPK = (StageNameKey)other;
            return otherPK.stageId == this.stageId && otherPK.language == this.language;
        }
        return false;
    }
}
