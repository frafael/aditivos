(function(app) {
	app.utils = {
		boxDialog : {
			_$ : null,
			init : function() {
				this._$ = $("#dialog");
			},
			close : function() {
				this._$.dialog("close").dialog("destroy").html("");
			},
			show : function( data ) {
				this._$.html( data.message )
					.dialog({
					resizable 	: false,
					modal 		: (data.modal === undefined) || data.modal,
					message 	: data.message,
					title 		: data.title || "Informação do sistema",
					width 		: data.width || 350,
					height 		: data.height || 200,
					buttons 	: data.buttons || {
						Fechar	: function (){ app.utils.boxDialog.close(); }
					},
					close : data.close || function(event, ui) {},
					open : data.open ||
						function(event, ui) {
							$(".ui-button").focus();
						}
				});
			}
		},
		splitDateInDateAndHour : {
			formatAndGetDate : function( data ) {
				//Slice pegando apenas a data no formato americano e transformando pro brasileiro. (99/99/9999).
				return data.slice(0,10).replace(/^(\d{4})\-(\d{2})\-(\d{2})/, "$3/$2/$1");
			},
			getHoursOfDate : function( data ) {
				//Slice pegando apenas a hora (99:99).
				return data.slice(11,16);
			}
		},
		formatDateToEn : function ( data ){
			return data.replace(/^(\d{2})\/(\d{2})\/(\d{4})/, "$3/$2/$1");
		},
		scrollFormActions: function (){
			var topo = document.body.scrollTop, tamanho = document.body.clientHeight, tamanhoscroll = document.body.scrollHeight;
			
			tamanho_soma = eval(tamanho+topo);
			
			if( topo <=160 ) {
				var esp = 160-topo;
				$("#form-action").css("top",esp);
			} else if( topo > 160) {
				$("#form-action").css("top","0");
				
				/*scrollsidebar = document.getElementById('topoConteudo').scrollTop;
				
				scrolldifinition = topo+1;
				document.getElementById('topoConteudo').scrollTop = scrolldifinition;*/
			}
		},
		validateDate : function( data ) {
			var valid = false,
				regex = new RegExp("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)[0-9]{2})$"),
				matches = regex.exec(data);
			if( matches != null ) {
				var day = parseInt(matches[1], 10),
					month = (parseInt(matches[2], 10)) - 1,
					year = parseInt(matches[3], 10),
					date = new Date(year, month, day, 0, 0, 0);
				valid = ( date.getDate() == day ) && ( date.getMonth() == month ) && ( date.getFullYear() == year );
			}
			return valid;
		},
		validateTime : function( hora ) {
		    var valid = false,
		    	regex = new RegExp("^([0-9]{2}):([0-9]{2})$"),
		    	matches = regex.exec(hora);
		    if ( matches != null ) {
		        var hour = parseInt(matches[1], 10),
		        	minute = parseInt(matches[2], 10),
		        	date = new Date(0, 0, 0, hour, minute, 0);
		        valid = ( date.getHours() == hour ) && ( date.getMinutes() == minute );
		    }
		    return valid;
		},
		transformTextToLink : function( text ) {
			    replacePattern1 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
			    replacedText = text.replace(replacePattern1, '<a href="mailto:$1">$1</a>');
			    
			    replacePattern2 = /(.+\s)?((.+)\.net)/gim;
			    replacedText = replacedText.replace(replacePattern2, '$1 <a href="http://$2" target="_blank">$2</a>');
			    
			    replacePattern3 = /(.+\s)?((.+)\.(com.br|com))/gim;
			    replacedText = replacedText.replace(replacePattern3, '$1 <a href="http://$2" target="_blank">$2</a>');
			    
			    replacePattern4 = /(.+\s)?((.+)\.org)/gim;
			    replacedText = replacedText.replace(replacePattern4, '$1 <a href="http://$2" target="_blank">$2</a>');
			    
			    replacePattern5 = /(http:\/\/)(https?|ftp)/gim;
			    replacedText = replacedText.replace(replacePattern5, "$2");
			    
		    return replacedText;
		},
		stripTags : function(allowed, input) {
			allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join('');
			var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi;
			var commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(:php)?[\s\S]*?\?>/gi;
			return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
			    return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
		    });
		},
		accordion : {
			_$ : null,
			init : function (){
				this._$ = $(".accordion");
				this._$.accordion({autoHeight: false});
			}
		},
		maxlengthTextArea : {
			init : function() {
				$("textarea[maxlength]").on("keydown keyup blur", function(event) {
					var key = event.which,
						maxLength = $(this).attr("maxlength"),
						length = this.value.length,
						$contadorDeCaracteres = $(".contadorDeCaracteres");
					//pegando o backspace
					if( key == 8 ) {
						if( $contadorDeCaracteres.length ) {
							$contadorDeCaracteres.find(".length").html(maxLength - length);
						}
					}
					//todas as teclas incluindo enter
					if(key >= 33 || key == 13 || key == 32) {
						if( $contadorDeCaracteres.length ) {
							$contadorDeCaracteres.find(".length").html(maxLength - length);
						}
						if(length > maxLength) {
							$(this).val($(this).val().substr(0, maxLength));
					        $(".contadorDeCaracteres").find(".length").html("0");
						}
					}
				});
			}
		},
		paginacao : {
			_$ : null,
			init : function(qtdDeItens) {
				this._$ = $('#page_container');
				this._$.pajinate({
					items_per_page : qtdDeItens,
					num_page_links_to_display : 5,
					nav_label_first : '<<',
					nav_label_last : '>>',
					nav_label_prev : '<',
					nav_label_next : '>',
					abort_on_small_lists: true
				});
			}
		},
		impedirQueDataFuturaSejaMenorQueDataPrevia : function ( datas, id ) {
			var dates = $( datas ).datepicker({
				showOtherMonths: true, selectOtherMonths: true,
				onSelect : function( selectedDate ) {
					var option = this.id == id ? "minDate" : "maxDate",
						instance = $( this ).data( "datepicker" ),
						date = $.datepicker.parseDate(instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );
				}
			});
		},
		tempoPassado : function(dataCadastro) {
			var dataDeHoje = new Date();
			var dataDeCadastro = new Date ( app.utils.formatDateToEn(dataCadastro) );

			var diferencaEmSegundos = ((Date.parse(dataDeHoje))-(Date.parse(dataDeCadastro)))/1000;
			
		    var tempoDeCadastro;
		    if ( diferencaEmSegundos < 60 )
		    	if ( diferencaEmSegundos > 10 )
		    		tempoDeCadastro = diferencaEmSegundos + " segudos atrás"; //CALCULA-SE A DIFERENÇA EM SEGUNDOS
		    	else
		    		tempoDeCadastro = "Alguns segundos atrás";
		    if ( diferencaEmSegundos >= 60 && diferencaEmSegundos < 3600 )
		    	tempoDeCadastro = parseInt(diferencaEmSegundos/60) + " min atrás"; //CALCULA-SE A DIFERENÇA EM MINUTOS
		    else if ( diferencaEmSegundos >= 3600 && diferencaEmSegundos < 3600*24 )
		    	tempoDeCadastro = parseInt(diferencaEmSegundos/(60*60)) + " hora(s) atrás"; //CALCULA-SE A DIFERENÇA EM HORAS
		    else if ( diferencaEmSegundos >= 3600*24 && diferencaEmSegundos < 3600*24*30)
		    	tempoDeCadastro = parseInt(diferencaEmSegundos/(60*60*24)) + " dia(s) atrás"; //CALCULA-SE A DIFERENÇA EM DIAS
		    else if ( diferencaEmSegundos >= 3600*24*30 && diferencaEmSegundos < 3600*24*365)
		    	tempoDeCadastro = parseInt(diferencaEmSegundos/(60*60*24*30)) + " mes(es) atrás"; //CALCULA-SE A DIFERENÇA EM MESES
		    else if ( diferencaEmSegundos >= 3600*24*365)
		    	tempoDeCadastro = parseInt(diferencaEmSegundos/(60*60*24*365)) + " ano(s) atrás"; //CALCULA-SE A DIFERENÇA EM ANOS
		    return tempoDeCadastro;
		},
		logout : function(contexto) {
			$.cookie("nome", null, {path: "/"+contexto});
		},
		listar : function( ) {
			var id = $('#estabelecimentos').val();
			if ( id == "" ) {
				$("#requisitantes").html("");
				$("#requisitantes").append("<option value=''>Escolha um solicitante...</option>");
				$("#setores").html("");
				$("#setores").append("<option value=''>Escolha um departamento...</option>");
			} else {
				$.ajax({
					url : contextPath + "/compras/requisitantes/"+ id.split("|")[0],
					type : "GET",
					cache : false,
					success : function( retorno ) {
						var requisitantes = retorno.requisitantes;
						var options = "<option value=''>Escolha um solicitante...</option>";
						$(requisitantes).each(function(i, requisitante ) {
							options += "<option value='" + requisitante.id + "|" + requisitante.nome +  "'>"+ requisitante.nome +"</option>" 
						});
						$("#requisitantes").html("");
						$("#requisitantes").append(options);
						
						$.ajax({
							url : contextPath + "/compras/setores/"+ id.split("|")[0],
							type : "GET",
							cache : false,
							success : function( retorno ) {
								var setores = retorno.setores;
								var options = "<option value=''>Escolha um departamento...</option>";
								$(setores).each(function(i, setor ) {
									options += "<option value='" + setor.id + "|" + setor.nome + "'>"+ setor.nome +"</option>" 
								});
								$("#setores").html("");
								$("#setores").append(options);
							},
							error : function (){}
						});
					},
					error : function (){}
				});
			}
		}
	};
})(intranet);