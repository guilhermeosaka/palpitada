package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TeamNameKey {
	public String language;
	@Column(name = "team_id")
	public Long teamId;
	
	public TeamNameKey(String language, Long teamId) {
		this.language = language;
		this.teamId = teamId;
	}
	
	public int hashCode() {
        return language.hashCode() + (int)(long)teamId;
    }

    public boolean equals(Object other) {
        if (other != null && (other instanceof TeamNameKey)) {
        	TeamNameKey otherPK = (TeamNameKey)other;
            return otherPK.teamId == this.teamId && otherPK.language == this.language;
        }
        return false;
    }
}
