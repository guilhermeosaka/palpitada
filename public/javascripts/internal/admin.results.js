$(document).ready(function() {
	var unfinishedMatches = getFinishedMatches(false);
	var finishedMatches = getFinishedMatches(true);
	var x = 1;
	var viewUnfinishedMatches = [];
	if( Object.prototype.toString.call( matches ) === '[object Array]' ) {
		for (var i in unfinishedMatches) {
			var match = new Match(matches[i]);
			viewUnfinishedMatches.push(match);
		}
	}
	var viewFinishedMatches = [];
	if( Object.prototype.toString.call( matches ) === '[object Array]' ) {
		for (var i in finishedMatches) {
			var match = new Match(matches[i]);
			viewFinishedMatches.push(match);
		}
	}
	
	
	function ViewModel() {
		var self = this;
		
		self.unfinishedMatches = ko.observableArray(viewUnfinishedMatches);
		self.finishedMatches = ko.observableArray(viewFinishedMatches);
	}
	
	var viewModel = new ViewModel();
	ko.applyBindings(viewModel);
	
	$("#no-unfinished-matches").hide();
	$("#no-finished-matches").hide();
	refresh(unfinishedMatches.length, finishedMatches.length);
});

function Match(match) {
	this.id = match.id;
	this.stage = trim(match.stage + " " + match.group);
	this.stadium = match.stadium;
	if (match.matchTeamA.goals == null) match.matchTeamA.goals = "";
	if (match.matchTeamB.goals == null) match.matchTeamB.goals = "";
	this.matchTeamA = match.matchTeamA;
	this.matchTeamB = match.matchTeamB;
	this.finished = match.finished;
	
	var datetimeMoment = moment(match.datetime, "DD/MM/YYYY HH:mm").subtract("m", moment().zone()).format("DD/MM/YYYY HH:mm");
	var datetime = Date.parseString(datetimeMoment, "dd/MM/yyyy HH:mm");
	this.day = datetime.getUTCDate();
	this.month = monthsEN[datetime.getUTCMonth()];
	this.year = datetime.getUTCFullYear();
	var hours = datetime.getHours();
	if (hours < 10)
		hours = "0" + hours;
	var minutes = datetime.getMinutes();
	if (minutes < 10)
		minutes = "0" + minutes;
	this.time = hours + "h" + minutes;
}

function refresh(numUnfinishedMatches, numFinishedMatches) {
	$('.tooltip-team').tooltip();
	
	var height = 0;
	if (numUnfinishedMatches > numFinishedMatches)
		height = numUnfinishedMatches;
	else
		height = numFinishedMatches;
	$(".inner-separator").css("height", height * 250 + "px");
	
	if (numUnfinishedMatches == 0) {
		$("#no-unfinished-matches").slideDown();
	} else {
		$("#no-unfinished-matches").slideUp();
	}
	
	if (numFinishedMatches == 0) {
		$("#no-finished-matches").slideDown();
	} else {
		$("#no-finished-matches").slideUp();
	}
}