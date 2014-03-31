$(document).ready(function() {
	teamOption($("#team-picker-left"));
	teamOption($("#team-picker-right"));
	
	$('#datetime-match').datetimepicker({
	  format:'d.m.Y H:i',
	  inline:true,
	  lang:'pt'
	});
})