<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/JLib.tld" prefix="jLib"%>
<form id="formDeleteConteudos" method="post" action="<c:url value='/${contexto}/conteudos/?'/>">
	<input type="hidden" name="_method" value="DELETE" />
</form>
<ul>
	<c:forEach items="${conteudos}" var="conteudo">
		<li id="conteudo-${conteudo.id}">
			<a class='conteudo-title' href="<c:url value='/${contexto}/conteudos/${blocoId}/show/${conteudo.id}'/>">${conteudo.titulo}</a>
			<div class="metadados">
				<c:if test="${bloco.nome != 'Classificados'}">
					<span class="por">Postado por: <a class="link link-usuario" onclick="intranet.views.usuario.show(${conteudo.usuario.id});">${conteudo.usuario.nome}</a></span>
					<div class='data'>
						<span class="hora"><fmt:formatDate value="${conteudo.cadastradoEm}" pattern="dd/MM/yyyy HH:mm:ss" /></span>
						<span class="dia">&nbsp;- <fmt:formatDate value="${conteudo.cadastradoEm}" pattern="dd/MM/yyyy" /></span>
					</div>
				</c:if>
				<c:if test="${bloco.nome == 'Classificados'}">
					<span class="por">Quem está vendendo: <a class="link link-usuario" onclick="intranet.views.usuario.show(${conteudo.usuario.id});">${conteudo.usuario.nome}</a></span>
				</c:if>
			</div>
			<div class="post">${conteudo.resumo}</div>
			<div class="post-actions">
				<c:if test="${!conteudo.ativo}">
					<a id="aguardandoAutorizacao"><i class="icon-time"></i> Aguardando autorização</a><br/>
					<jLib:checkrole roles="autorizar_conteudo">
						<span id="autorizarConteudo">
							<a class="link" onclick="intranet.views.conteudo.ativar(${conteudo.id})"><i class="icon-ok"></i> Autorizar</a>
							<div class="separador"></div>
						</span>
					</jLib:checkrole>
				</c:if>
				<jLib:checkrole roles="editar_conteudo" conditionOr="${usuarioId == conteudo.usuario.id}">
					<a href="<c:url value='/${contexto}/conteudos/${blocoId}/edit/${conteudo.id}'/>" class="btn-editar"><i class="icon-edit"></i> Editar</a>
					<div class="separador"></div>
				</jLib:checkrole>
				<jLib:checkrole roles="deletar_conteudo" conditionOr="${usuarioId == conteudo.usuario.id}">
					<a class="link btn-delete" onclick="intranet.formulario.formDelete(${conteudo.id}, 'Conteudos');" class="btn-delete"><i class="icon-trash"></i></i> Deletar</a>
				</jLib:checkrole>
				<c:if test="${conteudo.totalComentarios > 0}">
					<div class="separador"></div>
					<i class="icon-comments verticalAlign" title="${conteudo.totalComentarios} comentário(s)" style="margin-top: -1px;"></i>
				</c:if>

			</div>
		</li>
	</c:forEach>
</ul>