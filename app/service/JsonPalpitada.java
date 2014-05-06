package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import play.libs.Json;
import models.Match;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonPalpitada {
	public static ObjectNode matchToJson(Match match) {
		ObjectNode json = Json.newObject();
		json.put("id", match.id);
		json.put("stage", match.stage.names.get(0).name);
		if (match.group != null) 
			json.put("group", match.group.name); 
		else 
			json.put("group", "");
		json.put("stadium", match.stadium.name);
		ObjectNode matchTeamA = Json.newObject();
		ObjectNode teamA = Json.newObject();
		teamA.put("name", match.matchTeamA.team.names.get(0).name);
		teamA.put("logo", match.matchTeamA.team.logo);
		matchTeamA.put("team", teamA);
		matchTeamA.put("goals", match.matchTeamA.goals);
		matchTeamA.put("penaltyGoals", match.matchTeamA.penaltyGoals);
		ObjectNode matchTeamB = Json.newObject();
		ObjectNode teamB = Json.newObject();
		teamB.put("name", match.matchTeamB.team.names.get(0).name);
		teamB.put("logo", match.matchTeamB.team.logo);
		matchTeamB.put("team", teamB);
		matchTeamB.put("goals", match.matchTeamB.goals);
		matchTeamB.put("penaltyGoals", match.matchTeamB.penaltyGoals);
		json.put("matchTeamA", matchTeamA);
		json.put("matchTeamB", matchTeamB);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		json.put("datetime", df.format(match.datetime));
		json.put("finished", match.finished);
		
		return json;
	}
}
