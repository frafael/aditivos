<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Intranet - Fortes Informática</title>
	<meta name="description" content="">
	<link rel="shortcut icon" href="${contextPath}/imagens/icones/fortes-intranet.ico" type="image/x-icon" />
  	<meta name="viewport" content="width=device-width">
	<script src="${contextPath}/js/jquery/jquery-1.7.0.min.js"></script>
	<script src="${contextPath}/js/jquery/jquery.validate.min.js"></script>
	<script src="${contextPath}/js/libs/modernizr-2.5.3.min.js"></script>
  	<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
  	<script>
		$(function() {
			$("#usuarioLogin").focus();
			$("#formRedefinirSenha").find("input").keydown(function(event) {
				if( event.keyCode === 13 ) $("#refefinir").click(); 
			});
			$("input").val("");
			$("#formRedefinirSenha").validate({
				rules : {
					"novasenha" : {
						required : true,
						minlength : 3
					},
					"confirmasenha" : {
						required : true,
						minlength : 3,
						equalTo : "#usuarioSenha"
					}
				},
				messages : {
					"novasenha" : {
						required : "Digite a nova senha.",
						minlength : "Tamanho mínimo da senha é 3."
					},
					"confirmasenha" : {
						required : "Digite a confirmação da senha.",
						minlength : "Tamanho mínimo da senha é 3",
						equalTo : "Senhas não conferem."
					}
				}
			});
		});
	</script>
</head>
<body>
	<div class="box-login">
		<div class="titulo-login">
			<img src="<c:url value='/${empresa.id}/logo'/>" width="58" height="67" />
			<h3>Intranet ${empresa.nome}</h3>
		</div>
		<div class="box-campos-login">
			<h3 class="titulo">Redefinir senha</h3>
			<form method="post" id="formRedefinirSenha" action="${contextPath}/${contexto}/recuperarsenha/${hash.usuario.id}/token/${hash.hash}">
				<div id="mensagem-redefinir">${mensagem}</div>
				<div class="campo">
					<label class="user">Digite sua nova senha: </label>
					<input type="password" class="ipt-login-user" name="novasenha" id="usuarioSenha">
				</div>
				<div class="campo">
					<label class="user">Confirme sua nova senha: </label>
					<input type="password" class="ipt-login-user" name="confirmasenha" id="usuarioSenhaConfirmacao">
				</div>
				<div class="campo campo-btn">
					<button id="refefinir" class="btn-style">Redefinir</button>
					<button id="voltar" onclick="window.location = '/${contexto}/login';" class="btn-style">Voltar</button>
				</div>
			</form>
		</div>
		<div class="ass-sistema">
			<h4>Um sistema:</h4>
			<img src="${contextPath}/imagens/logo-ass-login.gif" height="46" width="35">
		</div>
	</div>
</body>
</html>