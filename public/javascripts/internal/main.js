// Global variables
var teams = {};
var stages = {};
var groups = {};
var stadiums = {};
var matches = {};

//Months 
var monthsEN = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
var monthsPT = ["JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"];

$(document).ready(function() {
	teams = getTeams();
	stages = getStages();
	groups = getGroups();
	stadiums = getStadiums();
	matches = getMatches();
	
	//Team-picker
	if ($("div").hasClass("team-picker")) {
		teamOptionLabel($("#team-picker"));
		$("#vote-team").click(function() {
			var ddData = $('#team-picker').data('ddslick');
			vote(ddData.selectedData.value);
		});
	}
	
	/* It can be done creating a plugin (remember note) */
	//Validation - Email
	$('input.email').each(function() {
    	initializeInput($('input.email'), $("input.email").formance('validate_email'));
	});
    $('input.email').on('keyup change', function() {
		validate($(this), $(this).formance('validate_email'), "has-warning", $("#invalid-email").text());
		$('div.alert-error').fadeOut();
    });
    $('input.email').on('blur', function () {
    	validate($(this), $(this).formance('validate_email'), "has-error", $("#invalid-email").text());
    });

	//Validation - Minimum characters: 1
    if ($('input.min-1').get().length > 0)
    	initializeInput($('input.min-1'), inBounds($("input.min-1").val().length, 1));
    $('input.min-1').on('keyup change', function () {
    	validate($(this), inBounds($(this).val().length, 1), "has-warning", $("#min-1").text());
    	$('div.alert-error').fadeOut();
    });
    $('input.min-1').on('blur', function () {
    	validate($(this), inBounds($(this).val().length, 1), "has-error", $("#min-1").text());
    });
    
	//Validation - Minimum characters: 4
    $('input.min-4').each(function() {
    	initializeInput($(this), inBounds($(this).val().length, 4));
	});
    $('input.min-4').on('keyup change', function () {
    	validate($(this), inBounds($(this).val().length, 4), "has-warning", $("#min-4").text());
    	$('div.alert-error').fadeOut();
    });
    $('input.min-4').on('blur', function () {
    	validate($(this), inBounds($(this).val().length, 4), "has-error", $("#min-4").text());
    });
    
    //Validation - Password confirmation
    $('input.pass-confirm').each(function() {
		$(this).parent().removeClass("has-success has-warning has-error");
		$(this).parent().prev().hide();
    	if ($(this).parent().parent().parent().find("p.error").children().text() == $("#diff-pass").text()) {
    		initializeInput($(this), false);
			$(this).parent().prev().html($("#diff-pass").text());
    	}
    //is("p.error")
	});
    $('input.pass-confirm').on('keyup change', function () {
    	validate($(this), confirmPass($(this).val(), $(this).parent().parent().prev().find("input").val()), "has-warning", $("#diff-pass").text());
    	$('div.alert-error').fadeOut();
    });
    $('input.pass-confirm').on('blur', function () {
    	validate($(this), confirmPass($(this).val(), $(this).parent().parent().prev().find("input").val()), "has-error", $("#diff-pass").text());
    });
    
    //Validation - Password
    $('input.password').each(function() {
		$(this).parent().removeClass("has-success has-warning has-error");
		$(this).parent().prev().hide();
    	if ($(this).parent().prev().text().length > 0) {
    		initializeInput($(this), false);
    	}
    //is("p.error")
	});
});

function validate(input, valid, invalid, message) {
	input.parent().removeClass("has-success has-warning has-error");
	input.parent().prev().fadeOut();
	if (valid) {
		input.parent().addClass("has-success");
    	input.siblings().fadeIn();
	} else {
		input.parent().addClass(invalid);
		input.siblings().fadeOut();
		if (invalid === "has-error") {
			input.parent().prev().html(message);
			input.parent().prev().fadeIn();
		}
	}
}

function initializeInput(input, isValid) {
	if (input.parent().parent().parent().children().hasClass("error")) {
		if (isValid) {
			input.parent().addClass("has-success");
			input.siblings().fadeIn();
		} else {
			input.parent().prev().show();
			input.parent().addClass('has-error');
			input.siblings().fadeOut();
		}
	}
}

// Returns boolean whether the input has the minimum size required
function inBounds(actual, min) {
	if (actual < min) 
		return false;
	else
		return true;
}

function confirmPass(actual, current) {
	if (actual != current || current == "")
		return false;
	else
		return true;
}

//Fills options with flags only
function teamOption(element) {
	var ddData = [];
	for (var i in teams) {
		if (teams[i].id == 7)
			ddData.push( { value: teams[i].id, imageSrc: "/assets/images/external/flags/" + teams[i].logo, selected: true } );
		else 
			ddData.push( { value: teams[i].id, imageSrc: "/assets/images/external/flags/" + teams[i].logo } );
	}
	
	element.ddslick({
		data: ddData,
		width: 100,
		imagePosition: "left"
	});
}

//Fills options with flags and label
function teamOptionLabel(element) {
	var ddData = [];
	for (var i in teams) {
		if (teams[i].id == 7)
			ddData.push( { text: teams[i].name, value: teams[i].id, imageSrc: "assets/images/external/flags/" + teams[i].logo, selected: true } );
		else 
			ddData.push( { text: teams[i].name, value: teams[i].id, imageSrc: "assets/images/external/flags/" + teams[i].logo } );
	}
	
	element.ddslick({
		data: ddData,
		width: 300,
		imagePosition: "left"
	});
}

function optionLabel(element, data) {
	for (var i in data) {
		element
			.append($("<option></option>")
			.attr("value", data[i].id)
			.text(data[i].name));
	}
	element.selectric();
} 

function matchList(element, data) {
	var matches = data;
	$(".inner-separator").css("height", Object.size(matches) * 130 + "px");
	element.empty();
	for (var i in matches) {
		var match = matches[i]
		var datetime = Date.parseString(match.datetime, "dd/MM/yyyy HH:mm");
		var $match = $("<li>", {id: match.id, class: "match col-1-1"});
		var $datetime = $("<div>", {class: "date col-1-3"});
		var $day = $("<div>", {class: "day", text: datetime.getUTCDate()});
		var $monthYear = $("<div>", {class: "month-year"});
		var $month = $("<div>", {class: "month", text: monthsEN[datetime.getUTCMonth()]});
		var $year = $("<div>", {class: "year", text: datetime.getUTCFullYear()});
		var hours = datetime.getHours();
		if (hours < 10)
			hours = "0" + hours;
		var minutes = datetime.getMinutes();
		if (minutes < 10)
			minutes = "0" + minutes;
		var $time = $("<div>", {class: "time", text: hours + "h" + minutes})
		$monthYear.append($month, $year);
		$datetime.append($day, $monthYear, $time);
		
		var title = stages[match.stage].name;
		if (matches[i].group != "")
			title += " " + groups[match.group].name;
		var $stage = $("<div>", {class: "stage col-2-3", text: title});
		
		var teamA = teams[match.matchTeamA.team];
		var teamB = teams[match.matchTeamB.team];
		var $teams = $("<div>", {class: "teams col-1-1"});
		var $teamAImage = $("<img>", {src: "/assets/images/external/flags/" + teamA.logo});
		$teamAImage.tooltip({
			placement: "bottom",
			title: teamA.name
		});
		var $teamBImage = $("<img>", {src: "/assets/images/external/flags/" + teamB.logo});
		var $teamA = $("<div>", {class: "teamA"});
		var $teamB = $("<div>", {class: "teamB"});
		$teamBImage.tooltip({
			placement: "bottom",
			title: teamB.name
		});
		$teamA.append($teamAImage);
		$teamB.append($teamBImage);
		$teams.append($teamA, "X", $teamB);
//		$teams.append(teams[match.matchTeamA.team].name, $teamAImage,
//				"X",
//				$teamBImage, teams[match.matchTeamB.team].name);
//		
		$match.append($datetime, $stage, $teams);
		element.append($match);
	}
}

//Dialog
function messageDialog(type, message) {
	switch(type) {
	case "success":
		$("#dialog-message")
			.removeClass("error")
			.text(message)
			.addClass("success");
		$(".bs-example-modal-sm").modal("show");
		break;
	case "error":
		$("#dialog-message")
			.removeClass("success")
			.text(message)
			.addClass("error");
		$(".bs-example-modal-sm").modal("show");
		break;
	default:
		alert("error");
	}
}

//Ajax call + loaders (GET)
function getTeams() {
	ajax.controllers.Application.getTeams().ajax({
		async: false,
	    success : function(data) {
	    	for (var i in data) {
	    		var team = data[i];
	    		teams[team.id] = team;
	    	}
	    },
		error: function(data) {
			alert("Erro ao obter os times");
		}
	});
	
	return teams;
}

function getStages() {
	ajax.controllers.Application.getStages().ajax({
		async: false,
	    success : function(data) {
	    	for (var i in data) {
	    		var stage = data[i];
	    		stages[stage.id] = stage;
	    	}
	    },
		error: function(data) {
			alert("Erro ao obter as fases");
		}
	});
	
	return stages;
}

function getStadiums() {
	ajax.controllers.Application.getStadiums().ajax({
		async: false,
	    success : function(data) {
	    	for (var i in data) {
	    		var stadium = data[i];
	    		stadiums[stadium.id] = stadium;
	    	}
	    },
		error: function(data) {
			alert("Erro ao obter os estádios");
		}
	});
	
	return stadiums;
}

function getGroups() {
	ajax.controllers.Application.getGroups().ajax({
		async: false,
		success : function(data) {
			for (var i in data) {
	    		var group = data[i];
	    		groups[group.id] = group;
	    	}
		},
		error: function(data) {
			alert("Erro ao obter os grupos");
		}
	});
	
	return groups;
}

function getMatches() {
	ajax.controllers.Application.getMatches().ajax({
		async: false,
		success : function(data) {
			for (var i in data) {
	    		var match = data[i];
	    		matches[match.id] = match;
	    	}
		},
		error: function(data) {
			alert("Erro ao obter os jogos");
		}
	});
	
	return matches;
}

//Ajax call + loaders (POST)
function vote(teamId) {
	ajax.controllers.Application.voteFavorite(teamId).ajax({
		success : function(data) {
			//window.location.replace("/");
			//$(".team-picker").fadeOut();
			location.reload();
		},
		error : function(data) {
			alert("Erro ao salvar favorito");
		}
	});
}


//Helpers
Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};