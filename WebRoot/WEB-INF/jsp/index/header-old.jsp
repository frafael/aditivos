<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/JLib.tld" prefix="jLib"%>
<c:set var="contextPath">${pageContext.request.contextPath}</c:set>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>     <![endif]--><html class="no-js lt-ie9" lang="en">
<!--[if gt IE 8]> <html class="no-js" lang="en"><!-->  <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Intranet - Fortes Informática</title>
	<meta name="description" content="">
	
	<meta name="viewport" content="width=device-width">
	<link rel="shortcut icon" href="${contextPath}/imagens/icones/fortes-intranet.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/intranet.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.css?v=${version}">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/pagination.css?v=${version}">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery/jquery-ui-1.10.3.custom.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery.mCustomScrollbar.css" />
	
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css?v=${version}">
	<link rel="stylesheet" type="text/css" href="http://localhost:9292/stylesheets/style.css?v=${version}">
	
	<script src="${contextPath}/js/jquery/jquery-1.7.0.min.js?v=${version}"></script>
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
	<script src="${contextPath}/js/libs/modernizr-2.5.3.min.js?v=${version}"></script>
	<script src="${contextPath}/js/mustache.js?v=${version}"></script>
	<script src="${contextPath}/js/bootstrap.js?v=${version}"></script>
	<script src="${contextPath}/js/jquery.mCustomScrollbar.concat.min.js?v=${version}"></script>
	
	<script src="${contextPath}/js/intranet/reservas/acoes.js?v=${version}"></script>
	
	
	<link rel="stylesheet" href="${contextPath}/jqueryTreeview/jquery.treeview.css" />
	<script src="${contextPath}/jqueryTreeview/jquery.treeview.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${contextPath}/jqueryTreeview/jquery.treeview.css" />
	
	<script>
		$("")
		var contextPath = "${contextPath}/${contexto}";
		$(function() {
			$(".menu-name").find(".menu-icon-close").show();
			$(".menu-name").find(".menu-icon-open").hide();
			$(".menu-name").click(function(){
				$(".menu-selected").attr("class", "itens menu-unselect");
				$(this).parent().find(".itens").attr("class", "itens menu-selected");
				$(".menu-selected").parent().find(".itens").toggle();
				$(".menu-selected").parent().find(".menu-icon").toggle();
				
				$(".menu-unselect").hide();
				$(".menu-unselect").parent().find(".menu-icon-close").show();
				$(".menu-unselect").parent().find(".menu-icon-open").hide();
			});
		});
		function selectMenu(menusel){
			$(".itens").hide().attr("class","itens menu-unselect");
			$(".menu-name").find(".menu-icon-close").show();
			$(".menu-name").find(".menu-icon-open").hide();
			$(".menu li[menuname="+menusel+"]").find(".itens").attr("class", "itens menu-selected").show();
			$(".menu li[menuname="+menusel+"]").find(".menu-icon-close").hide();
			$(".menu li[menuname="+menusel+"]").find(".menu-icon-open").show();
		}
	</script>
</head>
<body onLoad="intranet.formulario.focusAndReset();">
<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
	<div class="header">
		<div class="container">
			<h1><a href="<c:url value='/${contexto}'/>"><img src="<c:url value='/${usuarioEmpresaId}/logo'/>" width="56" height="65" /></a></h1>
			<div class="painel-topo">
				<!--
				<c:if test="${recadosNaoLidos > 0}">
					<div class="mensagens">
						<span class="ico"></span>
						<c:if test="${recadosNaoLidos == 1}">
							<strong>Existe uma mensagem para você</strong>
						</c:if>
						<c:if test="${recadosNaoLidos > 1}">
							<strong>Existem ${recadosNaoLidos} mensagens para você</strong>
						</c:if>
						<a href="<c:url value='/${contexto}/usuarios/${usuarioId}/mensagens'/>">Clique aqui para ler suas mensagens</a>
					</div>
				</c:if> -->
				<c:if test="${comentariosNaoAutorizados > 0}">
					<div class="mensagens">
						<span class="ico"></span>
						<strong>Existem comentários aguardando liberação</strong>
						<a href="<c:url value='/${contexto}/conteudos/autorizarcomentarios'/>">Clique aqui para ver em quais conteúdos existem comentários</a>
					</div>
				</c:if> 
				<jLib:checkrole roles="autorizar_conteudo">
					<c:if test="${conteudosNaoAutorizados > 0}">
						<div class="mensagens">
							<span class="ico"></span>
							<c:if test="${conteudosNaoAutorizados == 1}">
								<strong>Existe um conteúdo aguardando liberação</strong>
							</c:if>
							<c:if test="${conteudosNaoAutorizados > 1}">
								<strong>Existem ${conteudosNaoAutorizados} conteúdos aguardando liberação</strong>
							</c:if>
							<a href="<c:url value='/${contexto}/conteudos/autorizar'/>">Clique aqui para ver os conteúdos aguardando liberação</a>
						</div>
					</c:if> 
				</jLib:checkrole>
				<div class="meu-perfil">
					<div class="foto-perfil">
						<c:if test="${usuarioFotoFileName != null && usuarioFotoFileName != ''}">
							<img src="<c:url value='/${contexto}/usuarios/${usuarioId}/foto/thumb'/>" width="65" height="64" />
						</c:if>
						<c:if test="${usuarioFotoFileName == null || usuarioFotoFileName == ''}">
							<img src="<c:url value='/imagens/photo_unregistered.png'/>" width="65" height="64" />
						</c:if>
					</div>
					<div class="dados-perfil">
						<h4>${usuarioNome}</h4>
						<span class="cargo">${usuarioCargo}</span>
						<span class="empresa">${usuarioEmpresaNome}</span>
					</div>
				</div>
				<div class="editar-perfil">
					<jLib:checkrole roles="meus_dados"><a href="<c:url value='/${contexto}/usuarios/${usuarioId}/edit'/>" class="btn-edit-perfil">Meus dados</a></jLib:checkrole>
					<a onclick="intranet.utils.logout('${contexto}');" href="<c:url value='/${contexto}/logout'/>" class="btn-sair-perfil">Sair</a>
				</div>
			</div>
		</div>
	</div>
	<div role="main" class="meio">
	
	<div class="menu">
		<ul>
			<li><a href="<c:url value='/${contexto}'/>" class="home-item"><span class="menu-icon"></span>Pagina Principal</a></li>
			<c:forEach items="${papeis}" var="papel" varStatus="status">
				<c:if test="${papel.papel == null && papel.menu}">
					<jLib:checkrole roles="${papel.codigo}">
						<c:if test="${status.count > 1}">
									</ul>
								</div>
							</li>
						</c:if>
						<li menuname="${papel.nome}">
							<a class="menu-name"><span class="menu-icon menu-icon-close"></span><span class="menu-icon menu-icon-open" style="display: none;"></span>${papel.nome}</a>
							<div class="itens menu-selected">
								<span class="bullet-submenu"></span>
								<ul <c:if test="${usuarioEmpresaId == 17}">class="background-logo"</c:if>>
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
					</div>
				</li>
			</c:if>
			<li><a href='<c:url value="/${contexto}/localizacoes/new"/>' class="localiza-item"><span class="menu-icon"></span>Localiza</a></li>			
			<c:forEach items="${menus}" var="menu">
				<li menuname="${menu.id}">
					<a class="menu-name menu-cadastrado"><span class="menu-icon menu-icon-close"></span><span class="menu-icon menu-icon-open" style="display: none;"></span>${menu.nome}
						<span class="menu-cadastrado-icon"></span>
					</a>
					<div class="itens menu-unselect">
						<span class="bullet-submenu"></span>
						<ul <c:if test="${usuarioEmpresaId == 17}">class="background-logo"</c:if>>
							<c:forEach items="${linksmenu}" var="linkmenu">
								<c:if test="${linkmenu.menu.id == menu.id}">
									<li><a href='${linkmenu.url}' <c:if test="${linkmenu.novaAba}">target="_blank"</c:if> >${linkmenu.nome}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</li>
			</c:forEach>
			<c:if test="${intranetAntiga}">
				<li><a href="http://intranet.fortesinformatica.com.br/${contexto}" class="intranet-item" target="_blank"><span class="menu-icon"></span>Intranet Antiga</a></li>
			</c:if>
		</ul>
	</div>
	<div class="conteudo">