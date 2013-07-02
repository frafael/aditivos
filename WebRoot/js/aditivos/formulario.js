(function(app) {
	app.formulario = {
		formDelete : function( id, objeto ) {
			var $form = $("#formDelete");
			if( objeto ) $form = $("#formDelete" + objeto);
			var novaActionDelete = $form.attr("action").replace("/?", "/"+ id);
			$form.attr("action", novaActionDelete);
			app.utils.boxDialog.show({
				title : "Confirmação",
				message : "Tem certeza que deseja excluir?",
				width : 250,
				height : 150,
				buttons : {
					"Sim" : function (){
						if (objeto == "Usuario"){
							var url = contextPath + "/usuarios/"+ id + "/reservas";
							$.get(url)
							.success(function(retorno) {
								console.log(retorno);
								if(retorno.boolean == true) app.views.usuario.confirmacaoTransferirReservas(id);
								else $form.submit();
							});
						} else {
							$form.submit();
						}
					},
					"Não" : function (){ app.utils.boxDialog.close(); }
				},
				open : function(event, ui) {
					$(".ui-dialog .ui-button:eq(1)").focus();
				}
			});
		},
		statusComentario : function( id, status ) {
			var $form = $("#formStatusComentario"),
				novaActionComentario = $form.attr("action").replace("{id}", id).replace("{status}", status);
			$form.attr("action", novaActionComentario);
			$form.submit();
		},
		focusAndReset : function( usuarioId ) {
			$("form :input:visible:not(:button):first").focus();
			if( $("#formNewUsuario").length ) {
				$("#formNewUsuario #usuarioLogin").val("");
				$("#formNewUsuario input[type=password]").val("");
			}
		},
		validacao : {
			mascaras : {
				init : function() {
					_$datas = $(".dateForm");
					_$telefone = $(".telefone");
					_$placa = $(".placa");
					_$hora = $(".horaForm");
					_$cep = $(".cep");
					_$datas.mask("99/99/9999");
					_$telefone.mask("(99) 9999-9999");
					_$placa.mask("aaa-9999");
					_$hora.mask("99:99");
					_$cep.mask("99.999-999");
				}
			},
			validate : function(form) {
				var $form = $(form), rules = {}, messages = {}, $elements, 
					itensObrigatoriosList = [], itensEmailList = [];
				$elements = $form.find("input[type != hidden], select, textarea");
				$elements.each(function() {
					var thisClass = $(this).attr("class"); 
					if( thisClass && thisClass.match("required") ) {
						itensObrigatoriosList.push( $(this).attr("name") );
						$(this).closest(".clearfix").find("label").append("<strong>*</strong>");
					} else if( thisClass && thisClass.match("email") ) {
						itensEmailList.push( $(this).attr("name") );
					}
				});
				for( var i = 0; i < itensObrigatoriosList.length; i++ ) {
					rules[itensObrigatoriosList[i]] = { required : true };
					messages[itensObrigatoriosList[i]] = { required : "" };
				}
				for( var i = 0; i < itensEmailList.length; i++ ) {
					rules[itensEmailList[i]] = { email : true };
					messages[itensEmailList[i]] = { email : "Digite um e-mail válido." };
				}
				$form.validate({
					rules : rules,
					messages : messages,
					highlight : function( element ) {
						$( element ).closest(".clearfix").addClass("error");
					},
					unhighlight: function( element ) {
						$( element ).closest(".clearfix").removeClass("error");
					}
				});
			},
			validateGeneric : function(form) {
				var $form = $(form), rules = {}, messages = {}, $elements, 
					itensObrigatoriosList = [], itensEmailList = [];
				$elements = $form.find("input[type != hidden], select, textarea");
				$elements.each(function() {
					var thisClass = $(this).attr("class");
					if( thisClass && thisClass.match("required") ) {
						itensObrigatoriosList.push( $(this).attr("name") );
					}
				});
				for( var i = 0; i < itensObrigatoriosList.length; i++ ) {
					rules[itensObrigatoriosList[i]] = { required : true };
					messages[itensObrigatoriosList[i]] = { required : "" };
				}
				$form.validate({
					rules : rules,
					messages : messages,
					highlight : function( element ) {
						$( element ).closest(".clearfix").addClass("error");
					},
					unhighlight: function( element ) {
						$( element ).closest(".clearfix").removeClass("error");
					}
				});
			}
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