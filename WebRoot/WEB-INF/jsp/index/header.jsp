<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/JLib.tld" prefix="jLib"%>
<!DOCTYPE HTML>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="stylesheet" type="text/css" href="${contextPath}/stylesheets/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/stylesheets/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery/jquery-ui-1.10.3.custom.min.css" />
		
        <link rel="stylesheet" type="text/css" href="http://10.1.2.46:9292/stylesheets/style.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery.mCustomScrollbar.css">
        
        <script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.7.0.min.js"></script>
        <script type="text/javascript" src="http://10.1.2.46:9292/js/jquery/jquery.fortesMenu.js"></script>
        <script type="text/javascript" src="http://10.1.2.46:9292/js/jquery/jquery.fortesPhotoInput.js"></script>
        <script type="text/javascript" src="http://10.1.2.46:9292/js/jquery/jquery.fortesPhotoPreview.js"></script>
		
		<script src="${contextPath}/js/jquery/jquery-ui-1.8.13.custom.min.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.ui.datepicker-pt-BR.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.validate.min.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.pajinate.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.ui.swappable.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.maskedinput-1.3.js?v=${version}"/></script>
		<script src="${contextPath}/js/jquery/jquery.cookie.js?v=${version}"></script>
		<script src="${contextPath}/js/libs/ba-linkify.min.js?v=${version}"></script>
		<script src="${contextPath}/js/moment/moment.min.js?v=${version}"></script>
		<script src="${contextPath}/js/moment/moment-require-fix.js?v=${version}"></script>
		<script src="${contextPath}/js/moment/lang/pt-br.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/utils.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/formulario.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/blocos.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/usuario.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/mensagem.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/reserva.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/aniversariantes.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/conteudo.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/video.js?v=${version}"></script>
		<script src="${contextPath}/js/intranet/arquivo.js?v=${version}"></script>
		<script src="${contextPath}/js/md5.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery.wrap_label.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery.mousewheel.js?v=${version}"></script>
		<script src="${contextPath}/js/keep-scrolling.js?v=${version}"></script>
		
		<script src="${contextPath}/js/mustache.js?v=${version}"></script>
		<script src="${contextPath}/js/bootstrap.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery/jquery.sticky.js?v=${version}"></script>
		<script src="${contextPath}/js/jquery.mCustomScrollbar.concat.min.js?v=${version}"></script>
		<script src="${contextPath}/js/imgLiquid-min.js?v=${version}"></script>
		
	
        <script type="text/javascript">
        	var contextPath = "${contextPath}/${contexto}";
        	$(function(){
        	
        		if ( $.browser.msie ) {
				  if ( $.browser.version < 10 )
				  	alert("Versão inferior a 10.0");
				}
        		
        		$(".menu").fortesMenu();
        		
        		$("#form-action").sticky({
        			getWidthFrom: ".box-cont",
        			topSpacing:10
        		});	
        		$(".header .foto-container").imgLiquid({
        			fill: true
        		});
        	});

        </script>
        <title>Intranet - ${usuarioEmpresaNome}</title>
    </head>
 
    <body>
 		<div class="header row-fluid">
 			<div id="logo-panel">
 				
 				<a href="http://intranet.grupofortes.com.br/grupofortes">
 					<img id="logo" src="http://intranet.grupofortes.com.br/17/logo" width="56" height="65">
 				</a>
 			</div>
 			<div id="info-panel">
 				<div class="notifications" id="notifications" >
 					<span class="ico"><i class="icon-exclamation-sign"></i></span>
					<span class="mensagem">Existem 9 mensagens para você</span><br/>
					<a href="/grupofortes/usuarios/4003/mensagens">Clique aqui para ler suas mensagens</a>
 				</div>
 				<div id="perfil">
	 				<div class="details">
	 					<div class='foto-moldura pull-left' style='width: 60px; height: 60px;'>
							<div style='width: 60px; height: 60px;' class='foto-container link'>
							   <img class="foto" src="<c:url value='/${contexto}/usuarios/${usuarioId}/foto/thumb'/>" />
							</div>
						</div>
		  				
		  				<div class="dados pull-left">
		  					<div class="name">${usuarioNome}</div>
		  					<div class="cargo">${usuarioCargo}</div>
		  					<div class="empresa"><i class="icon-group"></i> ${usuarioEmpresaNome}</div>
		  				</div>
		  				<div class="options pull-left">
							<a href="/grupofortes/usuarios/${usuarioId}/edit"> <i class="icon-cog"></i>Meus dados</a>
							<a onclick="intranet.utils.logout('grupofortes');" href="/grupofortes/logout"><i class="icon-remove"></i> Sair</a>
						</div>
					</div>
 				</div>
 			</div>
 			
 		</div>

	 		<div class="content-body">
		 		
		 		<div class='menu'>
		 			<ul>
		 			
		 				<li class='no-submenu'>
		 					<div class='menu-title'><a href="<c:url value='/${contexto}'/>"><i class="icon-home"></i>Pagina Principal</a></div>
		 				</li>
		 				
		 				<c:forEach items="${papeis}" var="papel" varStatus="status">
							<c:if test="${papel.papel == null && papel.menu}">
								<jLib:checkrole roles="${papel.codigo}">
									<c:if test="${status.count > 1}">
											</ul>
										</li>
									</c:if>
									<li menuname="${papel.nome}">
										<div class='menu-title'><a><i class="icon-plus"></i>${papel.nome}</a></div>
											<ul class="submenu <c:if test="${usuarioEmpresaId == 17}">background-logo</c:if>">
								</jLib:checkrole>
		 					</c:if>
		 					<c:if test="${papel.papel != null && papel.menu}">
								<jLib:checkrole roles="${papel.codigo}">
									<li><a href='<c:url value="/${contexto}${papel.link}"/>'>${papel.nome}</a></li>
								</jLib:checkrole>
							</c:if>
						</c:forEach>
						
						<c:if test="${papeisSize > 0}">
								</ul>
							</li>
						</c:if>
						
						<li class="no-submenu"><div class='menu-title'><a href='<c:url value="/${contexto}/localizacoes/new"/>'><i class="icon-search"></i>Localiza</a></div></li>
						
						<c:forEach items="${menus}" var="menu">
							<li>
								<div class='menu-title'><a><i class="icon-plus"></i>${menu.nome}</a></div>
									<ul class='submenu'>
									<c:forEach items="${linksmenu}" var="linkmenu">
										<c:if test="${linkmenu.menu.id == menu.id}">
											<li><a href='${linkmenu.url}' <c:if test="${linkmenu.novaAba}">target="_blank"</c:if> >${linkmenu.nome}</a></li>
										</c:if>
									</c:forEach>
			 					</ul>
							</li>
						</c:forEach>
						
		 				<c:if test="${intranetAntiga}">
							<li class="no-submenu"><a href="http://intranet.fortesinformatica.com.br/${contexto}" class="intranet-item" target="_blank"><span class="menu-icon"></span>Intranet Antiga</a></li>
						</c:if>
						
		 				<li>
		 					<div class='menu-title'><a><i class="icon-plus"></i>Videos</a></div>
		 					<ul class='submenu'>
		 						<li><a>Usuarios</a></li>
		 						<li><a>Blocos</a></li>
		 						<li><a>Videos</a></li>
		 						<li><a>Mensagens</a></li>
		 					</ul>
		 				</li>
		 				<li class='no-submenu'><div class='menu-title'><a><i class="icon-plus"></i>Usuarios</a></div></li>
		 				<li class='no-submenu'><div class='menu-title'><a><i class="icon-plus"></i>Administração</a></div></li>
		 				
		 			</ul>
		 		</div>
		 		
		 		<div id="content">
