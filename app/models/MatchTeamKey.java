package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MatchTeamKey {
	
	@Column(name = "match_id")
	public Long matchId;
	@Column(name = "team_id")
	public Long teamId;

	public MatchTeamKey(Long matchId, Long teamId) {
		this.matchId = matchId;
		this.teamId = teamId;
	}
	
	public int hashCode() {
        return (int)(long)matchId + (int)(long)teamId;
    }

    public boolean equals(Object other) {
        if (other != null && (other instanceof MatchTeamKey)) {
        	MatchTeamKey otherPK = (MatchTeamKey)other;
            return otherPK.matchId == this.matchId && otherPK.teamId == this.teamId;
        }
        return false;
    }
}
