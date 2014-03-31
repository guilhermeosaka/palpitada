package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.*;

public class Admin extends Controller {
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Matches() {
		return ok(matches.render());
	}
	
	//@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result Results() {
		return ok(results.render());
	}
}
