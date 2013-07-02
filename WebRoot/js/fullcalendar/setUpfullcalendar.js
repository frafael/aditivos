if(typeof intranet === "undefined") {
  var intranet = {};
}

intranet.calendar = function(){
	var _defaultOptions = function(){
		
		return {
			buttonText: { today: 'hoje', month: 'mês', week:  'semana', day:   'dia' },
			dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabado'],
			dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
			              'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'agendaWeek,agendaDay'
			},
			titleFormat: {
			    month: 'MMMM yyyy',                             // Setembro 2013
			    week: "d MMM[ yyyy]{ '&#8212;' d MMM yyyy}", 	// Set 7 - 13 2013
			    day: 'dddd, MMM d, yyyy'                  		// Terça, Set 8, 2013
			},
			timeFormat: 'H:mm{ - H:mm}',
			columnFormat: {
				 month: 'ddd',    	// Mon
				 week: 'ddd d/MM', 	// Mon 9/7
				 day: 'dddd d/MM'  	// Monday 9/7
			},
			firstHour: 8,
			defaultView: 'agendaWeek',
			axisFormat:'H:mm',
			allDaySlot: false,
			firstDay: 1,
			minTime: 7,
			maxTime: 21,
			contentHeight: 610,
		}
		
	}
	
	var _closePopover = function(){
		$('.fc-event').popover('hide');
	};
	
	var _init = function(){
		var self = this;
		
		self.options = {
			events: function(start, end, callback){
				self.loadEvents(self.recursoId, start, end, callback);
			},
			
			eventRender: function(event, element){
				if(!event.editable) return;
				
				var contentTpl = $("#reservaTpl").html();
				var dados = {
					data: moment(event.start).format("ddd, D MMMM, HH:mm – ") + moment(event.end).format("HH:mm")
				}
				var contentHtml = Mustache.render(contentTpl, dados);
				
				element.popover({
					title: event.title + "<button type='button' id='close' onClick='intranet.calendar.closePopover()' class='close'>&times;</button>",
					html: true,
					content: contentHtml,
					placement: 'top',
					container: 'body',
				});
			},
			eventDrop: _moveEvent,
			eventResize: _resizeEvent,
			
			dayClick: function(date, allDay, jsEvent, view) {
				$('.fc-event').popover('hide');
				
				if($("#reservaRecursoId").val() == 0){
					intranet.utils.boxDialog.show({
						title : "Aviso",
						message : "Selecione um recurso acima antes efetuar a reserva",
						width : 300,
						height : 150
					});
					return;
				}
				
				if(self.getCurrentEvent() != undefined && self.getCurrentEvent().dirty) return;
				
				var event = {
					id: "tmp",
		        	title: 'Novo evento',
		        	start: date,
		        	end: moment(date).add("minutes", 30).toDate(),
		        	allDay: false,
		        	editable: true,
		        	dirty: true
		        };
				
				self.$calendar.fullCalendar('renderEvent', event);
				self.currentEvent = event;
				
		    },
		    eventAfterRender: function(event, element, view ){
		    	if(typeof(event.dirty) !== "undefined"){
		    		
		    		_setValoresDefaultReservaForm("#formNewReserva", event);
		    		
		    		$("#formNewReserva").dialog("open");
		    		$("#obs").focus();
		    	}
		    	
		    },
		    
			eventClick: function(event){
				if(event.dirty){
					$('.fc-event').popover('hide');
					return;
				}
				
				self.currentEvent = event;
				$(".popover").data("event", event);
			},
			
			eventAfterAllRender: function(){
				$('.fc-event').click(function(){
				    $('.fc-event').not(this).popover('hide'); //all but this
				});
			}
		}
		
		self.$calendar = $('#fullcalendar');
		
		self.$calendar.fullCalendar($.extend(self.options, self.defaultOptions()));
		
		 $("#reservaRecursoId").change(function(){
			 self.recursoId = $(this).val();
			 self.$calendar.fullCalendar( 'refetchEvents' );
		 });

		 $(document).on("click", "#excluir-reserva" ,function(){
			 _showDeleteConfirmation(self.currentEvent);
			$('.fc-event').popover('hide');
		 });
		 
		 this.initAddReservaDialog();
		 this.initEditReservaDialog();
		 
		 
	}
	
	var _setValoresDefaultReservaForm = function(formSelector,event){
		$form = $(formSelector);
		$form.find("#recursoId").val($("#reservaRecursoId").val());
		$form.find("#dataReserva").val(moment(event.start).format("DD/MM/YYYY"));
		
		$form.find("#horaReservaInicio").val(moment(event.start).format("HH:mm"));
		$form.find("#horaReservaFim").val(moment(event.end).format("HH:mm"));
		
		$form.find("#horaReservaInicial").val(moment(event.start).format("HH:mm"));
		$form.find("#horaReservaFinal").val(moment(event.end).format("HH:mm"));
		
		$form.find("#loteId").val(event.loteId);
		
		if(!event.dirty){
			var obs = event.title.split("-");
			obs.pop();
			$form.find("#obs").val($.trim(obs.join("-")));
			
			$form.find("#reservaId").val(event.id);
		}
		
	}
	
	
	var _editReserva = function(){
		$('.fc-event').popover('hide');
		if( this.currentEvent.loteId != null){
			$("#edicao-lote-options").show();
		}
		
		$("#formEditReserva").dialog("open");
		_setValoresDefaultReservaForm("#formEditReserva",this.currentEvent);
	}
	
	var _initEditReservaDialog = function(){
		var self = this;
		
		$("#formEditReserva").dialog({
			 autoOpen: false,
			 minHeight: 276,
			 minWidth: 500,
			 modal: true,
			 title: 'Edição Reserva',
			 buttons: {
				 "Salvar": function(){
					 var successCallback = function(){  
						 intranet.calendar.$calendar.fullCalendar("refetchEvents"); 
						 self.currentEvent = undefined; 
					 }
					 
					 var failCallback = function(mensagem){
						 $( "#formEditReserva" ).dialog( "close" );
						 
						 intranet.utils.boxDialog.show({
								title : "Aviso",
								message : mensagem,
								width : 300,
								height : 150
						 });
						 
						 if(self.currentEvent && self.currentEvent.dirty){
							 self.$calendar.fullCalendar('removeEvents', self.currentEvent.id);
							 self.currentEvent = undefined;
						 }
					 }
					 
					 if(self.currentEvent.dirty){
						 intranet.views.reserva.gravar("#formEditReserva", successCallback, failCallback);
					 }else{
						 intranet.views.reserva.atualizar("#formEditReserva", successCallback, failCallback); 
					 }
					 $("#edicao-lote-options").hide();
				 },
				 "Cancelar": function(){
					 
					 if(self.currentEvent && self.currentEvent.dirty){
						 self.$calendar.fullCalendar('removeEvents', self.currentEvent.id);
					 }
					 self.currentEvent = null;
					 $( this ).dialog( "close" );
					 $("#edicao-lote-options").hide();
				 }
			 },
			 close: function(event, ui){
				 $(this)[0].reset();
			 },
			 open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
		 });
	}
	
	var _initAddReservaDialog = function(){
		var self = this;
		
		$("#formNewReserva").dialog({
			 autoOpen: false,
			 minHeight: 276,
			 minWidth: 500,
			 modal: true,
			 title: 'Nova Reserva',
			 buttons: {
				 "Salvar": function(){
					 var successCallback = function(){
						 intranet.calendar.$calendar.fullCalendar("refetchEvents");
						 self.currentEvent = undefined; 
					}
					 
					 var failCallback = function(mensagem){
						 $( "#formNewReserva" ).dialog( "close" );
						 
						 intranet.utils.boxDialog.show({
								title : "Aviso",
								message : mensagem,
								width : 300,
								height : 150
						 });
						 
						 if(self.currentEvent && self.currentEvent.dirty){
							 self.$calendar.fullCalendar('removeEvents', self.currentEvent.id);
							 self.currentEvent = undefined;
						 }
					 }
					 
					 if(self.currentEvent.dirty){
						 intranet.views.reserva.gravar("#formNewReserva", successCallback, failCallback);
					 }else{
						 intranet.views.reserva.atualizar("#formNewReserva", successCallback, failCallback); 
					 }
					 
				 },
				 "Cancelar": function(){
					 
					 if(self.currentEvent && self.currentEvent.dirty){
						 self.$calendar.fullCalendar('removeEvents', self.currentEvent.id);
					 }
					 self.currentEvent = null;
					 $( this ).dialog( "close" );
				 }
			 },
			 close: function(event, ui){
				 $(this)[0].reset();
			 },
			 open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
		 });
	}
	
	var _loadEvents = function(recursoId, start, end, callback) {
		$.ajax({
			url : contextPath + '/reservas/calendar',
			dataType : 'json',
			data : {
				start : Math.round(start.getTime() / 1000),
				end : Math.round(end.getTime() / 1000),
				recursoId : recursoId
			},
			success : function(eventsRaw) {
				var events = [];
				$(eventsRaw).each(
						function(idx, event) {
							var options = {
								id : event.id,
								title : event.observacao + " - "
										+ event.usuario.nome,
								start : moment(event.dataInicio,
										"DD/MM/YYYY HH:mm:ss").format(
										"YYYY-MM-DD HH:mm:ss"),
								end : moment(event.dataFim,
										"DD/MM/YYYY HH:mm:ss").format(
										"YYYY-MM-DD HH:mm:ss"),
								allDay : false,
								editable : event.editavel,
								loteId: event.loteId
							}

							if (!event.editavel) {
								$.extend(options, {
									backgroundColor : "#ccc",
									borderColor : "#ccc",
									textColor : "#000"
								});
							}
							;

							events.push(options);
						});

				callback(events);
			}
		});
	}
	
	var _moveEvent = function(event, dayDelta, minuteDelta, allDay, revertFunc) {
		if(event.dirty) return;
		var data = {
			id : event.id,
			dayDelta : dayDelta,
			minuteDelta : minuteDelta,
			allDay : allDay
		}
		var url = contextPath + '/reservas/calendar/move'
		_postMudancaEvento(url, data, revertFunc);
	}
	
	var _resizeEvent = function(event,dayDelta,minuteDelta,revertFunc){
		if(event.dirty) return;
		var data = {
				id: event.id,
				dayDelta: dayDelta,
				minuteDelta: minuteDelta
			}
			var url = contextPath + '/reservas/calendar/resize'
			_postMudancaEvento(url, data, revertFunc);
	}
	
	var _postMudancaEvento = function(url, data, revertFunc){
		$.post(url, data)
		 .success(function(eventsRaw){})
		 .fail(function(data){
			 if(data.status == 403){
				 intranet.utils.boxDialog.show({
						title : "Aviso",
						message : "Você não tem permissão para essa ação.",
						width : 300,
						height : 150
				 });
			 };
			 
			 if(data.status == 422){
				 intranet.utils.boxDialog.show({
						title : "Aviso",
						message : data.responseText,
						width : 300,
						height : 150
				 });
			 };
			 
			 revertFunc();
		 });
	}
	
	var _showDeleteConfirmation = function(event){
		var excluirTpl = $("#excluirReservaTpl").html();

		intranet.utils.boxDialog.show({
			title : "Aviso",
			message : event.loteId == undefined ? "Tem certeza que deseja excluir esta reserva?" : excluirTpl,
			width : 300,
			height : 150,
			buttons: {
				"Excluir": function(){
					_deleteReserva(event);
				},
				"Cancelar": function(){
					intranet.utils.boxDialog.close();
				}
			}
		});
		
	}
	
	var _deleteReserva = function(event){
		var self = this;
		var form = $("#excluirReservaOptions").serialize();
		$.ajax({
			url : contextPath + '/reservas/destroy/' + event.id,
			dataType : 'json',
			data: form,
			type: 'post',
			success : function(eventsRaw) {
				intranet.calendar.$calendar.fullCalendar( 'refetchEvents' );
				intranet.utils.boxDialog.close();
			}
		});
	}
	
	var _getCurrentEvent = function(){
		return this.currentEvent;
	}
	
	return {
		init: _init,
		loadEvents: _loadEvents,
		defaultOptions: _defaultOptions,
		initAddReservaDialog: _initAddReservaDialog,
		initEditReservaDialog: _initEditReservaDialog,
		closePopover: _closePopover,
		editReserva: _editReserva,
		getCurrentEvent: _getCurrentEvent
	}
	
}();

$(function(){
	moment.lang('pt-br');
	intranet.calendar.init();
});

