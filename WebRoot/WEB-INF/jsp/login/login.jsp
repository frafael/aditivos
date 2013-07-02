<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--><html class="no-js" lang="en"><!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Intranet - Fortes Informática</title>
	<meta name="description" content="">
	<link rel="shortcut icon" href="${contextPath}/imagens/icones/fortes-intranet.ico" type="image/x-icon" />
  	<meta name="viewport" content="width=device-width">
	<script src="${contextPath}/js/jquery/jquery-1.7.0.min.js"></script>
	<script src="${contextPath}/js/jquery/jquery.cookie.js"></script>
	<script src="${contextPath}/js/login.js"></script>
	<script src="${contextPath}/js/jquery/jquery.validate.min.js"></script>
	<script src="${contextPath}/js/md5.js"></script>
	
  	<!-- link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css"> -->
  	<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.min.css">
  	<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
  	<script>
		$(function() {
			$("#usuarioLogin").focus();
			$("#formLogin").find("input").keydown(function(event) {
				if( event.keyCode === 13 ) $("#logar").click(); 
			});
		});
	</script>
</head>
<body>
	<div class='login'>
		<div class="box box-login">
			<div class="box-empresa">
				<!-- img src="<c:url value='/${empresa.id}/logo'/>" width="58" height="67" /-->
				<div class='login-empresa-nome'>${empresa.nome}</div>
			</div>
			<div class="box-login-cont">
				
				<form class="form" method="post" id="formLogin" action="${contextPath}/${contexto}/login" autocomplete="off">
					<input type="hidden" name="idCli" value="${empresa.id}">
					<input type="hidden" id="senhaCriptografada" name="senhaCriptografada" />
					<div id="erro">${message}</div>
					
					<label class="user">Usuário:</label>
					<input type="text" class="ipt-login-user" name="nome" id="usuarioLogin">
					
					
					<label class="pass">Senha:</label>
					<input type="password" class="ipt-login-user" name="senha" id="usuarioPassword">
					
					
					<label for="lembreSeDeMim" class="checkbox">
						<input type="checkbox" id="lembreSeDeMim" class="check-ipt">
						Lembre-se de mim
					</label>
					
					<input class="btn login-button" type="button" id="logar" onclick="login.logar('${contexto}');" class="btn-acessar" value='Acessar'>
						
					<a class="redefinir-senha link" href="<c:url value='/${contexto}/redefinirsenha'/>">Esqueci minha senha</a>
				</form>
			</div>
			<!-- div class="ass-sistema">
				<div>Um sistema:</div>
				<img src="${contextPath}/imagens/logo-ass-login.gif" height="46" width="35">
			</div -->
		</div>
	
	</div>
	<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
</body>
</html>