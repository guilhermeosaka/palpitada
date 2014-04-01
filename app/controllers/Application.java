package controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	            routes.javascript.Application.voteFavorite()
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
			json.put("group", team.groupTeam.toString());
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
		List<Team.GroupTeam> groups = Arrays.asList(Team.GroupTeam.values());
		
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		int id = 1;
		for (Team.GroupTeam group: groups) {
			ObjectNode json = Json.newObject();
			json.put("id", id++);
			json.put("name", group.toString());
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