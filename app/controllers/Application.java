package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Match;
import models.Stadium;
import models.Stage;
import models.Team;
import models.User;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.Session;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import views.html.index;
import views.html.login;
import views.html.profile;
import views.html.restricted;
import views.html.signup;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;

public class Application extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";
	public static final String ADMIN_ROLE = "admin";
	
	public static Result index() {
		return ok(index.render());
	}

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = User.findByAuthUserIdentity(currentAuthUser);
		return localUser;
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result restricted() {
		final User localUser = getLocalUser(session());
		return ok(restricted.render(localUser));
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result profile() {
		final User localUser = getLocalUser(session());
		return ok(profile.render(localUser));
	}

	public static Result login() {
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result doLogin() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(login.render(filledForm));
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin(ctx());
		}
	}

	public static Result signup() {
		return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword()))
				.as("text/javascript");
	}

	public static Result doSignup() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(signup.render(filledForm));
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup
			return UsernamePasswordAuthProvider.handleSignup(ctx());
		}
	}

	public static String formatTimestamp(final long t) {
		return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
	}
	
	//Ajax calls
	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	        Routes.javascriptRouter("ajax",
	            routes.javascript.Application.getTeams(),
	            routes.javascript.Application.getStages(),
	            routes.javascript.Application.getGroups(),
	            routes.javascript.Application.getStadiums(),
	            routes.javascript.Application.getMatches(),
	            routes.javascript.Application.voteFavorite(),
	            routes.javascript.Admin.registerMatch()
	        )
	    );
	}
	
	public static Result getTeams() {
		List<Team> teams = Team.find.all();

		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		for (Team team : teams) {
			ObjectNode json = Json.newObject();
			json.put("id", team.id);
			json.put("name", team.names.get(0).name);
			json.put("logo", team.logo);
			ObjectNode group = Json.newObject();
			group.put("id", team.group.id);
			group.put("name", team.group.name);
			json.put("group", group);
			result.add(json);
		}
		
		return ok(result);
	}
	
	public static Result getStages() {
		List<Stage> stages = Stage.find.all();
		
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		for (Stage stage : stages) {
			ObjectNode json = Json.newObject();
			json.put("id", stage.id);
			json.put("name", stage.names.get(0).name);
			result.add(json);
		}
		
		return ok(result);
	}
	 
	public static Result getGroups() {
		List<models.Group> groups = models.Group.find.all();
		
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		for (models.Group group : groups) {
			ObjectNode json = Json.newObject();
			json.put("id", group.id);
			json.put("name", group.name);
			result.add(json);
		}
		
		return ok(result);
	}
	
	public static Result getStadiums() {
		List<Stadium> stadiums = Stadium.find.all();
		
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		for (Stadium stadium : stadiums) {
			ObjectNode json = Json.newObject();
			json.put("id", stadium.id);
			json.put("name", stadium.name);
			json.put("image", stadium.image);
			json.put("city", stadium.city);
			json.put("state", stadium.state);
			json.put("capactiy", stadium.capacity);
			result.add(json);
		}
		
		return ok(result);
	}
	
	public static Result getMatches() {
		List<Match> matches = Match.find.all();
		
		if (matches.isEmpty()) {
			return ok();
		}
		
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		for (Match match : matches) {
			ObjectNode json = Json.newObject();
			json.put("id", match.id);
			json.put("stage", match.stage.id);
			json.put("group", match.group.id);
			json.put("stadium", match.stadiums.id);
			ObjectNode matchTeamA = Json.newObject();
			matchTeamA.put("team", match.matchTeamA.team.id);
			matchTeamA.put("goals", match.matchTeamA.goals);
			matchTeamA.put("penaltyGoals", match.matchTeamA.penaltyGoals);
			ObjectNode matchTeamB = Json.newObject();
			matchTeamB.put("team", match.matchTeamB.team.id);
			matchTeamB.put("goals", match.matchTeamB.goals);
			matchTeamB.put("penaltyGoals", match.matchTeamB.penaltyGoals);
			json.put("matchTeamA", matchTeamA);
			json.put("matchTeamB", matchTeamB);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			json.put("datetime", df.format(match.datetime));
			result.add(json);
		}
		
		return ok(result);
	}
	
	public static Result voteFavorite(Long id) {
		Team team = Team.find.byId(id);
		User user = getLocalUser(session());
		user.team = team;
		user.save();
		return ok(index.render());
	}
}

//List<Team> teams = Team.find.all();
//List<JsonNode> jsonTeams = new ArrayList<>();
//for (Team team : teams) {
//	jsonTeams.add(Json.toJson(team));	
//}
//
//return ok(Json.toJson(jsonTeams));