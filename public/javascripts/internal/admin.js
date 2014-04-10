$(document).ready(function() {
	teamOption($("#team-a"));
	teamOption($("#team-b"));
	optionLabel($(".stage"), stages);
	optionLabel($(".group"), groups);
	optionLabel($(".stadium"), stadiums);
	
	// Shows group select box if the stage selected is group
	$("#register-match").find(".stages").change(function() {
		var value = $(this).val();
		
		if (value == "")
			return;
		
		if (value != 1) //hard coded
			$("#register-match").find(".groups").fadeOut();
		else
			$("#register-match").find(".groups").fadeIn();
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
		
		ajax.controllers.Admin.registerMatch(stageId, groupId, stadiumId, teamAId, teamBId, datetime).ajax({
		    success : function(data) {
		    	stages = data;
		    	alert("success");
		    },
			error: function(data) {
				alert("error");
			}
		});
	});
})