//Global variables
var teams = [];

$(document).ready(function() {
	
	if ($("div").hasClass("team-picker")) {
		teams = getTeams();
		
		function ViewModel() {
			var self = this;
			
			self.team = ko.observable();
			
			self.teams = ko.observableArray(teams);
			
			self.vote = function() {
				var ddData = $("#team-picker").data("ddslick");
				ajax.controllers.Application.voteFavorite(ddData.selectedData.value).ajax({
					success : function(data) {
						//window.location.replace("/");
						$(".team-picker").fadeOut();
						location.reload();
					},
					error : function(data) {
						alert("Erro ao salvar favorito");
					}
				});
			}
		}
		
		ko.applyBindings(new ViewModel());

		$("#team-picker").ddslick();
	}
});