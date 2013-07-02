<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<!doctype html>
<html class="no-js" lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Acesso negado</title>
	<meta name="description" content="">
	<link rel="shortcut icon" href="${contextPath}/imagens/icones/fortes-intranet.ico" type="image/x-icon" />
  	<meta name="viewport" content="width=device-width">
	<script src="${contextPath}/js/libs/modernizr-2.5.3.min.js"></script>
  	<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
</head>
<body>
	<div class="box-erro">
		<div class="box-body-erro">
			<h3 class="titulo">Acesso negado!</h3>
			<img src="<c:url value='/imagens/acesso_negado.png'/>" /><br>
			<div class="mensagem" >
				<span style="font-size: 20px; color: #595959;">Você não tem acesso a essa p&#225;gina.</span>
			</div>
		</div>
		<a class="btn" href="javascript: history.back()">Voltar</a>
		<div class="ass-sistema">
			<h4>Um sistema:</h4>
			<img src="${contextPath}/imagens/logo-ass-login.gif" height="46" width="35">
		</div>
	</div>
</body>
</html>