$(document).ready(function() {
//	if ($("#login-error").is(":visible")) {
//	} else {
//	}
	
	/* It can be done creating a plugin (remember note) */
	//Validation - Email
	$('input.email').each(function() {
    	initializeInput($('input.email'), $("input.email").formance('validate_email'));
	});
    $('input.email').on('keyup change', function() {
		validate($(this), $(this).formance('validate_email'), "has-warning", $("#invalid-email").text());
    });
    $('input.email').on('blur', function () {
    	validate($(this), $(this).formance('validate_email'), "has-error", $("#invalid-email").text());
    });

	//Validation - Minimum characters: 1
    if ($('input.min-1').get().length > 0)
    	initializeInput($('input.min-1'), inBounds($("input.min-1").val().length, 1));
    $('input.min-1').on('keyup change', function () {
    	validate($(this), inBounds($(this).val().length, 1), "has-warning", $("#min-1").text());
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
    });
    $('input.min-4').on('blur', function () {
    	validate($(this), inBounds($(this).val().length, 4), "has-error", $("#min-4").text());
    });
    
    //Validation - Passwords match
    $('input.pass-confirm').each(function() {
    	if ($(this).parent().parent().parent().find("p.error").siblings().text() == $("#diff-pass").text()) {
    		alert("zxc") 
    		initializeInput($(this), false);
    	}
    //is("p.error")
	});
    $('input.pass-confirm').on('keyup change', function () {
    	validate($(this), confirmPass($(this).val(), $(this).parent().parent().prev().find("input").val()), "has-warning", $("#pass-confirm").text());
    });
    $('input.pass-confirm').on('blur', function () {
    	validate($(this), confirmPass($(this).val(), $(this).parent().parent().prev().find("input").val()), "has-error", $("#pass-confirm").text());
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
    console.log('a')
	console.log(actual);
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