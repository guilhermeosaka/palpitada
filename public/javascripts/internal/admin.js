$(document).ready(function() {
	teamOption($("#team-a"));
	teamOption($("#team-b"));
	optionLabel($(".stage"), stages);
	optionLabel($(".group"), groups);
	optionLabel($(".stadium"), stadiums);
	matchList($(".match-list"), matches);
	
	// Shows group select box if the stage selected is group
	$("#register-match").find(".stage").change(function() {
		var value = $(this).val();
		
		if (value == "")
			return;
		
		if (value != 1) //hard coded
			$("#register-match").find(".group").fadeOut();
		else
			$("#register-match").find(".group").fadeIn();
	});
	
	$('.datetime').datetimepicker({
	  format: 'd/m/Y H:i',
	  inline:true,
	  lang:'en'
	});
	
	//Button click events
	$("#register-match").find(".register").click(function() {
		var stageId = $("#register-match").find(".stage").find(":selected").val();
		var groupId = $("#register-match").find(".group").find(":selected").val();
		if (stageId != 1) groupId = 0; //hard coded
		var stadiumId = $("#register-match").find(".stadium").find(":selected").val();
		var teamAId = $("#register-match").find("#team-a").data("ddslick").selectedData.value;
		var teamBId = $("#register-match").find("#team-b").data("ddslick").selectedData.value;
		var datetime = $("#register-match").find(".datetime").val();
		if (datetime == "") datetime = moment().format('DD/MM/YYYY hh:mm')
		
		if (teamAId === teamBId) {
			messageDialog("error", "You gotta choose different teams");
			return;
		}
		
		ajax.controllers.Admin.registerMatch(stageId, groupId, stadiumId, teamAId, teamBId, datetime).ajax({
		    success : function(data) {
		    	messageDialog("success", data)
		    	matches = getMatches();
		    	matchList($(".match-list"), matches);
		    },
			error: function(data) {
				messageDialog("error", "Erro inesperado");
			}
		});
	});
})