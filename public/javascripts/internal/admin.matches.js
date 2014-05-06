//Global variables
var teams = {};
var stages = {};
var groups = {};
var stadiums = {};
var matches = {};

$(document).ready(function() {
	//New
	var teams = getTeams();
	var stages = getStages();
	var groups = getGroups();
	var stadiums = getStadiums();
	var matches = getMatches();

	var viewMatches = [];
	if( Object.prototype.toString.call( matches ) === '[object Array]' ) {
		for (var i in matches) {
			var match = new Match(matches[i]);
			viewMatches.push(match);
		}
	}
	
	function ViewModel() {
		var self = this;
		
		self.stages = ko.observableArray(stages);
		self.groups = ko.observableArray(groups);
		self.stadiums = ko.observableArray(stadiums);
		self.teams = ko.observableArray(teams);
		
		self.stage = ko.observable();
		self.group = ko.observable();
		self.stadium = ko.observable();
		self.teamA = function() {
			var ddData = $("#team-a").data("ddslick");
			return ddData.selectedData.value;
		};
		self.teamB = function() {
			var ddData = $("#team-b").data("ddslick");
			return ddData.selectedData.value;
		};
		self.datetime = ko.observable();
		
		self.matches = ko.observableArray(viewMatches);
		self.addMatch = function() {
			if (self.teamA() == self.teamB()) {
				messageDialog("error", "duplicated team");
				return;
			}
			//persists the match and returns it
			var stageid = self.stage().id;
			var groupid = self.group().id;
			if (stageid != 1) 
				groupid = 0;
			var stadiumid = self.group().id;
			var date = self.datetime();
			var dateMoment = moment(date, "DD/MM/YYYY HH:mm").add("m", moment().zone()).format("DD/MM/YYYY HH:mm");
			
			ajax.controllers.Admin.addMatch(self.stage().id, groupid, self.stadium().id, self.teamA(), self.teamB(), dateMoment).ajax({
				success : function(data) {
					messageDialog("success", data.message);
					self.matches.push(new Match(data.match));
					refresh(self.matches().length);
				},
				error: function(data) {
				messageDialog("error", "Erro inesperado");
				}
			});
		};
		
		self.removeMatch = function(match) {
			vex.dialog.confirm({
				message: 'Você deseja excluir esta partida?',
				callback: function(value) {
					if (value) {
						ajax.controllers.Admin.removeMatch(match.id).ajax({
							success: function(data) {
								self.matches.remove(match);
								refresh(self.matches().length);
							},
							error: function(data) {
								messageDialog("error", "Erro inesperado");
							} 
						});
					}
				}
			});
		}
	}
	
	var viewModel = new ViewModel();
	ko.applyBindings(viewModel);
	
	$("#no-matches").hide();
	refresh(matches.length);
});

$(function(){
	$('.stages').selectric();
	$('.groups').selectric();
	$('.stadiums').selectric();
	$('#team-a').ddslick({
		width: 100,
		imagePosition: "left"
	});
	$('#team-b').ddslick({
		width: 100,
		imagePosition: "left"
	});
	$('#add-match').find(".datetime").datetimepicker({
		format: 'd/m/Y H:i',
		inline:true,
		lang:'en'
	});
});

function Match(match) {
	this.id = match.id;
	this.stage = trim(match.stage + " " + match.group);
	this.stadium = match.stadium;
	this.matchTeamA = match.matchTeamA;
	this.matchTeamB = match.matchTeamB;
	
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

function refresh(numMatches) {
	$('.tooltip-team').tooltip();
	$(".inner-separator").css("height", numMatches * 250 + "px");
	
	if (numMatches == 0) {
		$("#no-matches").slideDown();
	} else {
		$("#no-matches").slideUp();
	}
}

