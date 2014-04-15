import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import models.Group;
import models.SecurityRole;
import models.Stadium;
import models.Stage;
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
			//Groups
			Group groupA = Group.create("A");
			Group groupB = Group.create("B");
			Group groupC = Group.create("C");
			Group groupD = Group.create("D");
			Group groupE = Group.create("E");
			Group groupF = Group.create("F");
			Group groupG = Group.create("G");
			Group groupH = Group.create("H");
			
			Map<String, String> germanyNames = new HashMap<>();
			germanyNames.put("en", "Germany");
			germanyNames.put("pt", "Alemanha");
			Team germany = Team.create(germanyNames, "DE.png", groupH);
			Map<String, String> algeriaNames = new HashMap<>();
			algeriaNames.put("en", "Algeria");
			algeriaNames.put("pt", "Argélia");
			Team algeria = Team.create(algeriaNames, "DZ.png", groupH);
			Map<String, String> argentinaNames = new HashMap<>();
			argentinaNames.put("en", "Argentina");
			argentinaNames.put("pt", "Argentina");
			Team argentina = Team.create(argentinaNames, "AR.png", groupF);
			Map<String, String> australiaNames = new HashMap<>();
			australiaNames.put("en", "Australia");
			australiaNames.put("pt", "Austrália");
			Team australia = Team.create(australiaNames, "AU.png", groupB);
			Map<String, String> belgiumNames = new HashMap<>();
			belgiumNames.put("en", "Belgium");
			belgiumNames.put("pt", "Bélgica");
			Team belgium = Team.create(belgiumNames, "BE.png", groupH);
			Map<String, String> bosniaAndHerzegovinaNames = new HashMap<>();
			bosniaAndHerzegovinaNames.put("en", "Bosnia and Herzegovina");
			bosniaAndHerzegovinaNames.put("pt", "Bósnia e Herzegovina");
			Team bosniaAndHerzegovina = Team.create(bosniaAndHerzegovinaNames, "BA.png", groupF);
			Map<String, String> brazilNames = new HashMap<>();
			brazilNames.put("en", "Brazil");
			brazilNames.put("pt", "Brasil");
			Team brazil = Team.create(brazilNames, "BR.png", groupA);
			Map<String, String> cameroonNames = new HashMap<>();
			cameroonNames.put("en", "Cameroon");
			cameroonNames.put("pt", "Camarões");
			Team cameroon = Team.create(cameroonNames, "CM.png", groupA);
			Map<String, String> chileNames = new HashMap<>();
			chileNames.put("en", "Chile");
			chileNames.put("pt", "Chile");
			Team chile = Team.create(chileNames, "CL.png", groupB);
			Map<String, String> colombiaNames = new HashMap<>();
			colombiaNames.put("en", "Colombia");
			colombiaNames.put("pt", "Colômbia");
			Team colombia = Team.create(colombiaNames, "CO.png", groupC);
			Map<String, String> koreaRepublicNames = new HashMap<>();
			koreaRepublicNames.put("en", "Korea Republic");
			koreaRepublicNames.put("pt", "Coréia do Sul");
			Team koreaRepublic = Team.create(koreaRepublicNames, "KR.png", groupH);
			Map<String, String> cotedIvoireNames = new HashMap<>();
			cotedIvoireNames.put("en", "Côte d'Ivoire");
			cotedIvoireNames.put("pt", "Costa do Marfim");
			Team cotedIvoire = Team.create(cotedIvoireNames, "CI.png", groupC);
			Map<String, String> costaRicaNames = new HashMap<>();
			costaRicaNames.put("en", "Costa Rica");
			costaRicaNames.put("pt", "Costa Rica");
			Team costaRica = Team.create(costaRicaNames, "CR.png", groupD);
			Map<String, String> croatiaNames = new HashMap<>();
			croatiaNames.put("en", "Croatia");
			croatiaNames.put("pt", "Croácia");
			Team croatia = Team.create(croatiaNames, "HR.png", groupA);
			Map<String, String> ecuadorNames = new HashMap<>();
			ecuadorNames.put("en", "Ecuador");
			ecuadorNames.put("pt", "Equador");
			Team ecuador = Team.create(ecuadorNames, "EC.png", groupE);
			Map<String, String> spainNames = new HashMap<>();
			spainNames.put("en", "Spain");
			spainNames.put("pt", "Espanha");
			Team spain = Team.create(spainNames, "ES.png", groupB);
			Map<String, String> usaNames = new HashMap<>();
			usaNames.put("en", "USA");
			usaNames.put("pt", "EUA");
			Team usa = Team.create(usaNames, "US.png", groupG);
			Map<String, String> franceNames = new HashMap<>();
			franceNames.put("en", "France");
			franceNames.put("pt", "França");
			Team france = Team.create(franceNames, "FR.png", groupE);
			Map<String, String> ghanaNames = new HashMap<>();
			ghanaNames.put("en", "Ghana");
			ghanaNames.put("pt", "Gana");
			Team ghana = Team.create(ghanaNames, "GH.png", groupG);
			Map<String, String> greeceNames = new HashMap<>();
			greeceNames.put("en", "Greece");
			greeceNames.put("pt", "Grécia");
			Team greece = Team.create(greeceNames, "GR.png", groupC);
			Map<String, String> netherlandsNames = new HashMap<>();
			netherlandsNames.put("en", "Netherlands");
			netherlandsNames.put("pt", "Holanda");
			Team netherlands = Team.create(netherlandsNames, "NR.png", groupB);
			Map<String, String> hondurasNames = new HashMap<>();
			hondurasNames.put("en", "Honduras");
			hondurasNames.put("pt", "Honduras");
			Team honduras = Team.create(hondurasNames, "HN.png", groupE);
			Map<String, String> englandNames = new HashMap<>();
			englandNames.put("en", "England");
			englandNames.put("pt", "Inglaterra");
			Team england = Team.create(englandNames, "EN.png", groupD);
			Map<String, String> iranNames = new HashMap<>();
			iranNames.put("en", "Iran");
			iranNames.put("pt", "Irã");
			Team iran = Team.create(iranNames, "IR.png", groupF);
			Map<String, String> italyNames = new HashMap<>();
			italyNames.put("en", "Italy");
			italyNames.put("pt", "Itália");
			Team italy = Team.create(italyNames, "IT.png", groupD);
			Map<String, String> japanNames = new HashMap<>();
			japanNames.put("en", "Japan");
			japanNames.put("pt", "Japão");
			Team japan = Team.create(japanNames, "JP.png", groupC);
			Map<String, String> mexicoNames = new HashMap<>();
			mexicoNames.put("en", "Mexico");
			mexicoNames.put("pt", "México");
			Team mexico = Team.create(mexicoNames, "MX.png", groupA);
			Map<String, String> nigeriaNames = new HashMap<>();
			nigeriaNames.put("en", "Nigeria");
			nigeriaNames.put("pt", "Nigéria");
			Team nigeria = Team.create(nigeriaNames, "NG.png", groupF);
			Map<String, String> portugalNames = new HashMap<>();
			portugalNames.put("en", "Portugal");
			portugalNames.put("pt", "Portugal");
			Team portugal = Team.create(portugalNames, "PT.png", groupG);
			Map<String, String> russiaNames = new HashMap<>();
			russiaNames.put("en", "Russia");
			russiaNames.put("pt", "Rússia");
			Team russia = Team.create(russiaNames, "RU.png", groupH);
			Map<String, String> switzerlandNames = new HashMap<>();
			switzerlandNames.put("en", "Switzerland");
			switzerlandNames.put("pt", "Suíça");
			Team switzerland = Team.create(switzerlandNames, "CH.png", groupE);
			Map<String, String> uruguayNames = new HashMap<>();
			uruguayNames.put("en", "Uruguay");
			uruguayNames.put("pt", "Uruguai");
			Team uruguay = Team.create(uruguayNames, "UY.png", groupG);
		}
		
		//Stage
		if (Stage.find.findRowCount() == 0) {
			Map<String, String> groupStageNames = new HashMap<>();
			groupStageNames.put("en", "Group");
			groupStageNames.put("pt", "Grupo");
			Stage groupStage = Stage.create(groupStageNames);
			
			Map<String, String> roundOf16Names = new HashMap<>();
			roundOf16Names.put("en", "Round of 16");
			roundOf16Names.put("pt", "Oitavas de final");
			Stage roundOf16 = Stage.create(roundOf16Names);
			
			Map<String, String> quarterFinalsNames = new HashMap<>();
			quarterFinalsNames.put("en", "Quarter-finals");
			quarterFinalsNames.put("pt", "Quartas de final");
			Stage quarterFinals = Stage.create(quarterFinalsNames);
			
			Map<String, String> semiFinalsNames = new HashMap<>();
			semiFinalsNames.put("en", "Semi-finals");
			semiFinalsNames.put("pt", "Semi final");
			Stage semiFinals = Stage.create(semiFinalsNames);
			
			Map<String, String> finalNames = new HashMap<>();
			finalNames.put("en", "Final");
			finalNames.put("pt", "Final");
			Stage finalStage = Stage.create(finalNames);
			
			Map<String, String> unimportantNames = new HashMap<>();
			unimportantNames.put("en", "Unimportant match");
			unimportantNames.put("pt", "Partida sem importância");
			Stage unimportantStage = Stage.create(unimportantNames);
		}
		
		//Stadium
		if (Stadium.find.findRowCount() == 0) {
			Stadium mineirao = Stadium.create("Estádio Mineirão", "mineirao.png", "Belo Horizonte", "Minas Gerais", (long)57483);
			Stadium nacional = Stadium.create("Estádio Nacional", "nacional.png", "Brasília", "Distrito Federal", (long)68009);
			Stadium pantanal = Stadium.create("Arena Pantanal", "pantanal.png", "Cuiabá", "Mato Grosso", (long)57483);
			Stadium baixada = Stadium.create("Arena da Baixada", "baixada.png", "Curitiba", "Paraná", (long)41456);
			Stadium castelao = Stadium.create("Estádio Castelão", "castelao.png", "Fortaleza", "Ceará", (long)58704);
			Stadium amazonia = Stadium.create("Arena Amazônia", "amazonia.png", "Manaus", "Amazonas", (long)42374);
			Stadium dunas = Stadium.create("Estádio das Dunas", "dunas.png", "Natal", "Rio Grande do Norte", (long)42086);
			Stadium beirario = Stadium.create("Estádio Beira-Rio", "beirario.png", "Porto Alegre", "Rio Grande do Sul", (long)48849);
			Stadium pernambuco = Stadium.create("Arena Pernambuco", "pernambuco.png", "Recife", "Pernambuco", (long)42849);
			Stadium maracana = Stadium.create("Estádio do Maracanã", "maracana.png", "Rio de Janeiro", "Rio de Janeiro", (long)73531);
			Stadium fonteNova = Stadium.create("Arena Fonte Nova", "fontenova.png", "Salvador", "Bahia", (long)52048);
			Stadium saoPaulo = Stadium.create("Arena de São Paulo", "saopaulo.png", "São Paulo", "São Paulo", (long)65807);
		}
	}
}