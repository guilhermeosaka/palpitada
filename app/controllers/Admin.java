package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Match;
import models.MatchTeam;
import models.Stadium;
import models.Stage;
import models.Team;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.JsonPalpitada;
import views.html.admin.matches;
import views.html.admin.results;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Admin extends Controller {
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Matches() {
		return ok(matches.render());
	}
	
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Results() {
		return ok(results.render());
	}
	
	public static Result addMatch(Long stageId, Long groupId, Long stadiumId, Long teamAId, Long teamBId, String datetime) {
		if (teamAId == teamBId) {
			return ok(Messages.get("palpitada.register_match_error.duplicated_team"));
		}
		Stage stage = Stage.find.byId(stageId);
		models.Group group = models.Group.find.byId(groupId);
		if (groupId == 0)
			group = null;
		Stadium stadium = Stadium.find.byId(stadiumId);
		Team teamA = Team.find.byId(teamAId);
		Team teamB = Team.find.byId(teamBId);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date matchDate = new Date();
		try {
			matchDate = (Date)formatter.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Match match = Match.create(stage, group, stadium, teamA, teamB, matchDate);
		
		ObjectNode result = Json.newObject();
		result.put("message", Messages.get("palpitada.register_match_success"));
		result.put("match", JsonPalpitada.matchToJson(match));
		
		return ok(result);
	}
	
	public static Result removeMatch(Long matchId) {
		Match match = Match.find.byId(matchId);
		MatchTeam matchTeamA = match.matchTeamA;
		MatchTeam matchTeamB = match.matchTeamB;
		match.delete();
		matchTeamA.delete();
		matchTeamB.delete();
		
		return ok(Messages.get("palpitada.delete_match_success"));
	}
}
