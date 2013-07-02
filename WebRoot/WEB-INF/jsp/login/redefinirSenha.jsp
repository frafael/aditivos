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
  	<!-- link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css"> -->
  	<link rel="stylesheet" type="text/css" href="${contextPath}/stylesheets/bootstrap.min.css">
  	<link rel="stylesheet" type="text/css" href="http://10.1.4.94:9292/stylesheets/style.css">
  	<script>
		$(function() {
			$("#usuarioLogin").focus();
			$("#formRedefinirSenha").find("input").keydown(function(event) {
				if( event.keyCode === 13 ) $("#redefinir").click(); 
			});
			$("#formRedefinirSenha").validate({
				rules : {
					"login" : {
						required : true
					}
				},
				messages : {
					"login" : {
						required : "Digite o login a ser verificado."
					}
				}
			});
		});
	</script>
</head>
<body>
	<div class='login-container'>
		<div class="box box-login">
			<div class="box-login-empresa">
				<img src="<c:url value='/${empresa.id}/logo'/>" width="58" height="67" />
				<div class='login-empresa-nome'>Intranet ${empresa.nome}</div>
			</div>
			<div class="box-login-title">Redefinir senha</div>
			<div class="box-login-cont">
				
				<form method="post" id="formRedefinirSenha" action="${contextPath}/${contexto}/redefinirsenha">
				
					<div id="mensagem-redefinir">${mensagem}</div>
					
					<label class="user">Digite seu login: </label>
					<input type="text" class="ipt-login-user" name="login" id="usuarioLogin">
					
					<button id="redefinir" onclick="" class="btn login-button">Redefinir</button>
				</form>
			</div>
			<div class="ass-sistema">
				<div>Um sistema:</div>
				<img src="${contextPath}/imagens/logo-ass-login.gif" height="46" width="35">
			</div>
		</div>
	
	</div>
</body>
</html>