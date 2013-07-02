(function(app) {
	var _ = {
		verificaLogin : function(login, functionSuccess){
			$.get(contextPath + "/usuarios/verificardisponibilidade/"+ login)
				.success( functionSuccess )
				.error(function() {
					app.utils.boxDialog.show({ message : "Erro ao verificar a disponibilidade do login." });
				});
		},
		validFormEdit : function() {
			$("#formUpdateSenha").validate({
				rules : {
					'senhaAtual' : {
						required : true,
						minlength : 3
					},
					'novaSenha' : {
						required : true,
						minlength : 3
					},
					'confirmar_senha' : {
						required : true,
						minlength : 3,
						equalTo : "#novaSenha"
					}
				},
				messages : {
					'senhaAtual' : {
						required : "Digite a senha atual.",
						minlength : "Tamanho mínimo da senha é 3."
					},
					'novaSenha' : {
						required : "Digite a nova senha.",
						minlength : "Tamanho mínimo da senha é 3."
					},
					'confirmar_senha' : {
						required : "Digite a confirmação da senha.",
						minlength : "Tamanho mínimo da senha é 3",
						equalTo : "Senhas não conferem."
					}
				}
			});
		}
	};
	app.views.usuario = {
		confirmacaoTransferirReservas : function( id ) {
			$("#transferirReservas").load(contextPath + "/usuarios/transferir");
			$("#transferirReservas").dialog({
				title : "Reservas do usuário",
				resizable : false,
				modal : true,
				width : 480,
				buttons : {
					"Ok" : function (){
						app.views.usuario.transferirReservas(id, $("#users").val());
					}
				},
				close : function() {
					if( url ) $("#videoShow").find("iframe").remove();
					else $("#videoShow").find("embed").remove();
					$("#comentariosVideos tr").remove();
				}
			});
		},
		transferirReservas : function(id, newUsuarioId) {
			$form = $("#formDeleteUsuario");
			var novaActionDelete = $form.attr("action").replace("/?", "/"+ id);
			$form.attr("action", novaActionDelete);

			var url = contextPath + "/usuarios/"+ id + "/reservas/" + newUsuarioId;
			$.get(url)
			.success(function() { 
				$form.submit();
			});
		},
		pesquisar : function() {
			var $usuarioSearch = $("#usuarioSearch"),
				unidade = $usuarioSearch.find("#usuarioUnidadeId").val(),
				setor = $usuarioSearch.find("#usuarioSetorId").val(),
				filtro = $usuarioSearch.find(":radio:checked").val(),
				search = $usuarioSearch.find(":text").val().replace(" ", "%20") || "todos",
				url = contextPath + "/usuarios/filtro/"+ filtro +"/unidade/"+ unidade +"/setor/"+ setor +"/"+ search;
			$("#listUsuarios").load(url);
		},
		show : function( id ) {
			$("#usuarioShow").html("");
			var url = contextPath + "/usuarios/"+ id +"/show";
				sHtml = $("#usuarioShow").load(url);
			app.utils.boxDialog.show({	
				title 	: "Informações do usuário",
				message : sHtml,
				width 	: 680,
				minHeight: 300,
				buttons : { Fechar : function() { $("#dialog").dialog("close"); } }
			});
		},
		verficarDisponibilidade : function() {
			var $usuarioLogin = $("#usuarioLogin");
			$('.alert-message').toggle(false);
			
			if( $usuarioLogin.val() ) {
				_.verificaLogin($usuarioLogin.val(), function( isLoginExiste ) {
					$('.success').toggle( !isLoginExiste.boolean );
					$('.error').toggle( isLoginExiste.boolean );
					if ( !isLoginExiste.boolean ) $("#usuarioLogin").attr('class', 'required inputSuccess');
					else $("#usuarioLogin").attr('class', 'required inputError');
				});
			} else {
				$('.info').toggle(true);
				$("#usuarioLogin").attr('class', 'required inputError');
			}
			$("#usuarioShow").parent().parent().css("height", "auto");
		},
		gravar : function(id) {
			var $formNewUsuario = $("#formNewUsuario"), $usuarioLogin = $("#usuarioLogin");
			$('.alert-message').toggle(false);
			
			if( $formNewUsuario.valid() ) {
				_.verificaLogin($usuarioLogin.val(), function( isLoginExiste ) {
					if( isLoginExiste.boolean ) $('.error').toggle();
					else $formNewUsuario.submit();
				});
			}
		}
	},
	app.validacao.usuario = {
		editToPassword : {
			init : function() {
				_.validFormEdit();
			}
		}
	};
})(intranet);