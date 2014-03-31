import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
			Map<String, String> germanyNames = new HashMap<>();
			germanyNames.put("en", "Germany");
			germanyNames.put("pt", "Alemanha");
			Team germany = Team.create(germanyNames, "DE.png", Team.GroupTeam.H);
			Map<String, String> algeriaNames = new HashMap<>();
			algeriaNames.put("en", "Algeria");
			algeriaNames.put("pt", "Argélia");
			Team algeria = Team.create(algeriaNames, "DZ.png", Team.GroupTeam.H);
			Map<String, String> argentinaNames = new HashMap<>();
			argentinaNames.put("en", "Argentina");
			argentinaNames.put("pt", "Argentina");
			Team argentina = Team.create(argentinaNames, "AR.png", Team.GroupTeam.F);
			Map<String, String> australiaNames = new HashMap<>();
			australiaNames.put("en", "Australia");
			australiaNames.put("pt", "Austrália");
			Team australia = Team.create(australiaNames, "AU.png", Team.GroupTeam.B);
			Map<String, String> belgiumNames = new HashMap<>();
			belgiumNames.put("en", "Belgium");
			belgiumNames.put("pt", "Bélgica");
			Team belgium = Team.create(belgiumNames, "BE.png", Team.GroupTeam.H);
			Map<String, String> bosniaAndHerzegovinaNames = new HashMap<>();
			bosniaAndHerzegovinaNames.put("en", "Bosnia and Herzegovina");
			bosniaAndHerzegovinaNames.put("pt", "Bósnia e Herzegovina");
			Team bosniaAndHerzegovina = Team.create(bosniaAndHerzegovinaNames, "BA.png", Team.GroupTeam.F);
			Map<String, String> brazilNames = new HashMap<>();
			brazilNames.put("en", "Brazil");
			brazilNames.put("pt", "Brasil");
			Team brazil = Team.create(brazilNames, "BR.png", Team.GroupTeam.A);
			Map<String, String> cameroonNames = new HashMap<>();
			cameroonNames.put("en", "Cameroon");
			cameroonNames.put("pt", "Camarões");
			Team cameroon = Team.create(cameroonNames, "CM.png", Team.GroupTeam.A);
			Map<String, String> chileNames = new HashMap<>();
			chileNames.put("en", "Chile");
			chileNames.put("pt", "Chile");
			Team chile = Team.create(chileNames, "CL.png", Team.GroupTeam.B);
			Map<String, String> colombiaNames = new HashMap<>();
			colombiaNames.put("en", "Colombia");
			colombiaNames.put("pt", "Colômbia");
			Team colombia = Team.create(colombiaNames, "CO.png", Team.GroupTeam.C);
			Map<String, String> koreaRepublicNames = new HashMap<>();
			koreaRepublicNames.put("en", "Korea Republic");
			koreaRepublicNames.put("pt", "Coréia do Sul");
			Team koreaRepublic = Team.create(koreaRepublicNames, "KR.png", Team.GroupTeam.H);
			Map<String, String> cotedIvoireNames = new HashMap<>();
			cotedIvoireNames.put("en", "Côte d'Ivoire");
			cotedIvoireNames.put("pt", "Costa do Marfim");
			Team cotedIvoire = Team.create(cotedIvoireNames, "CI.png", Team.GroupTeam.C);
			Map<String, String> costaRicaNames = new HashMap<>();
			costaRicaNames.put("en", "Costa Rica");
			costaRicaNames.put("pt", "Costa Rica");
			Team costaRica = Team.create(costaRicaNames, "CR.png", Team.GroupTeam.D);
			Map<String, String> croatiaNames = new HashMap<>();
			croatiaNames.put("en", "Croatia");
			croatiaNames.put("pt", "Croácia");
			Team croatia = Team.create(croatiaNames, "HR.png", Team.GroupTeam.A);
			Map<String, String> ecuadorNames = new HashMap<>();
			ecuadorNames.put("en", "Ecuador");
			ecuadorNames.put("pt", "Equador");
			Team ecuador = Team.create(ecuadorNames, "EC.png", Team.GroupTeam.E);
			Map<String, String> spainNames = new HashMap<>();
			spainNames.put("en", "Spain");
			spainNames.put("pt", "Espanha");
			Team spain = Team.create(spainNames, "ES.png", Team.GroupTeam.B);
			Map<String, String> usaNames = new HashMap<>();
			usaNames.put("en", "USA");
			usaNames.put("pt", "EUA");
			Team usa = Team.create(usaNames, "US.png", Team.GroupTeam.G);
			Map<String, String> franceNames = new HashMap<>();
			franceNames.put("en", "France");
			franceNames.put("pt", "França");
			Team france = Team.create(franceNames, "FR.png", Team.GroupTeam.E);
			Map<String, String> ghanaNames = new HashMap<>();
			ghanaNames.put("en", "Ghana");
			ghanaNames.put("pt", "Gana");
			Team ghana = Team.create(ghanaNames, "GH.png", Team.GroupTeam.G);
			Map<String, String> greeceNames = new HashMap<>();
			greeceNames.put("en", "Greece");
			greeceNames.put("pt", "Grécia");
			Team greece = Team.create(greeceNames, "GR.png", Team.GroupTeam.C);
			Map<String, String> netherlandsNames = new HashMap<>();
			netherlandsNames.put("en", "Netherlands");
			netherlandsNames.put("pt", "Holanda");
			Team netherlands = Team.create(netherlandsNames, "NR.png", Team.GroupTeam.B);
			Map<String, String> hondurasNames = new HashMap<>();
			hondurasNames.put("en", "Honduras");
			hondurasNames.put("pt", "Honduras");
			Team honduras = Team.create(hondurasNames, "HN.png", Team.GroupTeam.E);
			Map<String, String> englandNames = new HashMap<>();
			englandNames.put("en", "England");
			englandNames.put("pt", "Inglaterra");
			Team england = Team.create(englandNames, "EN.png", Team.GroupTeam.D);
			Map<String, String> iranNames = new HashMap<>();
			iranNames.put("en", "Iran");
			iranNames.put("pt", "Irã");
			Team iran = Team.create(iranNames, "IR.png", Team.GroupTeam.F);
			Map<String, String> italyNames = new HashMap<>();
			italyNames.put("en", "Italy");
			italyNames.put("pt", "Itália");
			Team italy = Team.create(italyNames, "IT.png", Team.GroupTeam.D);
			Map<String, String> japanNames = new HashMap<>();
			japanNames.put("en", "Japan");
			japanNames.put("pt", "Japão");
			Team japan = Team.create(japanNames, "JP.png", Team.GroupTeam.C);
			Map<String, String> mexicoNames = new HashMap<>();
			mexicoNames.put("en", "Mexico");
			mexicoNames.put("pt", "México");
			Team mexico = Team.create(mexicoNames, "MX.png", Team.GroupTeam.A);
			Map<String, String> nigeriaNames = new HashMap<>();
			nigeriaNames.put("en", "Nigeria");
			nigeriaNames.put("pt", "Nigéria");
			Team nigeria = Team.create(nigeriaNames, "NG.png", Team.GroupTeam.F);
			Map<String, String> portugalNames = new HashMap<>();
			portugalNames.put("en", "Portugal");
			portugalNames.put("pt", "Portugal");
			Team portugal = Team.create(portugalNames, "PT.png", Team.GroupTeam.G);
			Map<String, String> russiaNames = new HashMap<>();
			russiaNames.put("en", "Russia");
			russiaNames.put("pt", "Rússia");
			Team russia = Team.create(russiaNames, "RU.png", Team.GroupTeam.H);
			Map<String, String> switzerlandNames = new HashMap<>();
			switzerlandNames.put("en", "Switzerland");
			switzerlandNames.put("pt", "Suíça");
			Team switzerland = Team.create(switzerlandNames, "CH.png", Team.GroupTeam.E);
			Map<String, String> uruguayNames = new HashMap<>();
			uruguayNames.put("en", "Uruguay");
			uruguayNames.put("pt", "Uruguai");
			Team uruguay = Team.create(uruguayNames, "UY.png", Team.GroupTeam.G);
		}
	}
}