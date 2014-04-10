package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Match;
import models.Stadium;
import models.Stage;
import models.Team;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.matches;
import views.html.admin.results;

public class Admin extends Controller {
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Matches() {
		return ok(matches.render());
	}
	
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Results() {
		return ok(results.render());
	}
	
	public static Result registerMatch(Long stageId, Long groupId, Long stadiumId, Long teamAId, Long teamBId, String datetime) {
		Stage stage = Stage.find.byId(stageId);
		models.Group group = models.Group.find.byId(groupId);
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
		
		return ok();
	}
}
