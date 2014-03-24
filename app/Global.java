import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Group;
import models.SecurityRole;
import models.Team;
import play.Application;
import play.GlobalSettings;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		PlayAuthenticate.setResolver(new Resolver() {

			@Override
			public Call login() {
				// Your login page
				return routes.Application.login();
			}

			@Override
			public Call afterAuth() {
				// The user will be redirected to this page after authentication
				// if no original URL was saved
				return routes.Application.index();
			}

			@Override
			public Call afterLogout() {
				return routes.Application.index();
			}

			@Override
			public Call auth(final String provider) {
				// You can provide your own authentication implementation,
				// however the default should be sufficient for most cases
				return com.feth.play.module.pa.controllers.routes.Authenticate
						.authenticate(provider);
			}

			@Override
			public Call askMerge() {
				return routes.Account.askMerge();
			}

			@Override
			public Call askLink() {
				return routes.Account.askLink();
			}

			@Override
			public Call onException(final AuthException e) {
				if (e instanceof AccessDeniedException) {
					return routes.Signup
							.oAuthDenied(((AccessDeniedException) e)
									.getProviderKey());
				}

				// more custom problem handling here...
				return super.onException(e);
			}
		});

		initialData();
	}

	private void initialData() {
		if (SecurityRole.find.findRowCount() == 0) {
			for (final String roleName : Arrays
					.asList(controllers.Application.USER_ROLE)) {
				final SecurityRole role = new SecurityRole();
				role.roleName = roleName;
				role.save();
			}
		}
		
		//Groups
		String[] groups = {"A", "B", "C", "D", "E", "F", "G", "H"};
		if (Group.find.findRowCount() == 0) {
			for (final String letter : groups) {
				final Group group = new Group(letter);
				group.save();
			}
		}
		
		//Teams
		if (Team.find.findRowCount() == 0) {
			new Team("Alemanha", "DE.png", new Group("G")).save();
            new Team("Argélia", "DZ.png", new Group("H")).save();
            new Team("Argentina", "AR.png", new Group("F")).save();
            new Team("Austrália", "AU.png", new Group("B")).save();
            new Team("Bélgica", "BE.png", new Group("H")).save();
            new Team("Bósnia", "BA.png", new Group("F")).save();
            new Team("Brasil", "BR.png", new Group("A")).save();
            new Team("Camarões", "CM.png", new Group("A")).save();
            new Team("Chile", "CL.png", new Group("B")).save();
            new Team("Colômbia", "CO.png", new Group("C")).save();
            new Team("Coréia do Sul", "KR.png", new Group("H")).save();
            new Team("Costa do Marfim", "CI.png", new Group("C")).save();
            new Team("Costa Rica", "CR.png", new Group("D")).save();
            new Team("Croácia", "HR.png", new Group("A")).save();
            new Team("Equador", "EC.png", new Group("E")).save();
            new Team("Espanha", "ES.png", new Group("B")).save();
            new Team("Estados Unidos", "US.png", new Group("G")).save();
            new Team("França", "FR.png", new Group("E")).save();
            new Team("Gana", "GH.png", new Group("G")).save();
            new Team("Grécia", "GR.png", new Group("C")).save();
            new Team("Holanda", "NL.png", new Group("B")).save();
            new Team("Honduras", "HN.png", new Group("E")).save();
            new Team("Inglaterra", "EN.png", new Group("D")).save();
            new Team("Irã", "IR.png", new Group("F")).save();
            new Team("Itália", "IT.png", new Group("D")).save();
            new Team("Japão", "JP.png", new Group("C")).save();
            new Team("México", "MX.png", new Group("A")).save();
            new Team("Nigéria", "NG.png", new Group("F")).save();
            new Team("Portugal", "PT.png", new Group("G")).save();
            new Team("Rússia", "RU.png", new Group("H")).save();
            new Team("Suíça", "CH.png", new Group("E")).save();
            new Team("Uruguai", "UY.png", new Group("D")).save();
		}
	}
}