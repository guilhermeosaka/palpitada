import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
		
		//Teams
		if (Team.find.findRowCount() == 0) {
//			Team Germany = new Team("DE.png", Team.GroupTeam.G);
//			new TeamName(Germany, "en", "Germany").save();
//			new TeamName(Germany, "pt", "Alemanha").save();
			Map<String, String> germanyNames = new HashMap<>();
			germanyNames.put("en", "Germany");
			germanyNames.put("pt", "Alemanha");
			Team.createAndSave(germanyNames, "DE.png", Team.GroupTeam.G);
//            new Team("Argélia", "DZ.png", Team.GroupTeam.H).save();
//            new Team("Argentina", "AR.png", Team.GroupTeam.F).save();
//            new Team("Austrália", "AU.png", Team.GroupTeam.B).save();
//            new Team("Bélgica", "BE.png", Team.GroupTeam.H).save();
//            new Team("Bósnia", "BA.png", Team.GroupTeam.F).save();
//            new Team("Brasil", "BR.png", Team.GroupTeam.A).save();
//            new Team("Camarões", "CM.png", Team.GroupTeam.A).save();
//            new Team("Chile", "CL.png", Team.GroupTeam.B).save();
//            new Team("Colômbia", "CO.png", Team.GroupTeam.C).save();
//            new Team("Coréia do Sul", "KR.png", Team.GroupTeam.H).save();
//            new Team("Costa do Marfim", "CI.png", Team.GroupTeam.C).save();
//            new Team("Costa Rica", "CR.png", Team.GroupTeam.D).save();
//            new Team("Croácia", "HR.png", Team.GroupTeam.A).save();
//            new Team("Equador", "EC.png", Team.GroupTeam.E).save();
//            new Team("Espanha", "ES.png", Team.GroupTeam.B).save();
//            new Team("Estados Unidos", "US.png", Team.GroupTeam.G).save();
//            new Team("França", "FR.png", Team.GroupTeam.E).save();
//            new Team("Gana", "GH.png", Team.GroupTeam.G).save();
//            new Team("Grécia", "GR.png", Team.GroupTeam.C).save();
//            new Team("Holanda", "NL.png", Team.GroupTeam.B).save();
//            new Team("Honduras", "HN.png", Team.GroupTeam.E).save();
//            new Team("Inglaterra", "EN.png", Team.GroupTeam.D).save();
//            new Team("Irã", "IR.png", Team.GroupTeam.F).save();
//            new Team("Itália", "IT.png", Team.GroupTeam.D).save();
//            new Team("Japão", "JP.png", Team.GroupTeam.C).save();
//            new Team("México", "MX.png", Team.GroupTeam.A).save();
//            new Team("Nigéria", "NG.png", Team.GroupTeam.F).save();
//            new Team("Portugal", "PT.png", Team.GroupTeam.G).save();
//            new Team("Rússia", "RU.png", Team.GroupTeam.H).save();
//            new Team("Suíça", "CH.png", Team.GroupTeam.E).save();
//            new Team("Uruguai", "UY.png", Team.GroupTeam.D).save();
		}
	}
}