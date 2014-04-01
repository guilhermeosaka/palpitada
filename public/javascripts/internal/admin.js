$(document).ready(function() {
	teamOption($("#team-picker-left"));
	teamOption($("#team-picker-right"));
	optionLabel($(".stages"), stages);
	optionLabel($(".groups"), groups);
	
	$("#register-match").find(".stages").change(function() {
		var value = $(this).val();
		
		if (value == "")
			return;
		
		if (value != 1)
			$("#register-match").find(".groups").fadeOut();
		else
			$("#register-match").find(".groups").fadeIn();
	});
	
	$('.datetime').datetimepicker({
	  format:'d.m.Y H:i',
	  inline:true,
	  lang:'pt'
	});
})