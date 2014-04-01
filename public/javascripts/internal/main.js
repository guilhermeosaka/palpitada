// Global variables
var teams;
var stages;
var groups;

$(document).ready(function() {
	teams = getTeams();
	stages = getStages();
	groups = getGroups();
	
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

//Ajax call + loaders (GET)
function getTeams() {
	var teams;
	ajax.controllers.Application.getTeams().ajax({
		async: false,
	    success : function(data) {
	    	teams = data;
	    },
		error: function(data) {
			alert("error");
		}
	});
	
	return teams;
}

function getStages() {
	var stages;
	ajax.controllers.Application.getStages().ajax({
		async: false,
	    success : function(data) {
	    	stages = data;
	    },
		error: function(data) {
			alert("error");
		}
	});
	
	return stages;
}

function getGroups() {
	var groups;
	ajax.controllers.Application.getGroups().ajax({
		async: false,
		success : function(data) {
			groups = data;
		},
		error: function(data) {
			alert("error");
		}
	});
	
	return groups;
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
			alert("errado");
		}
	});
}