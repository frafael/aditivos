<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/JLib.tld" prefix="jLib"%>
<script>
	$(function() {
		intranet.formulario.validacao.validate("#videoNewComentario");
	});
</script>
<form id="formDeleteVideo" method="post" action="<c:url value='/${contexto}/videos/?'/>">
	<input type="hidden" name="_method" value="DELETE" />
</form>
<form id="formDelete" method="post" action="<c:url value='/${contexto}/comentarios/?'/>">
	<input type="hidden" name="_method" value="DELETE" />
</form>
<form id="formStatusComentario" method="post" action="<c:url value='/${contexto}/comentarios/{id}/status/{status}'/>">
	<input type="hidden" name="_method" value="PUT" />
</form>
<ul class="lista-videos">
	<c:forEach items="${videos}" var="video">
		<li videoId="${video.id}">
			<div class="video-moldura link" 
				<c:if test="${video.url != ''}">onclick="intranet.views.videos.show(${usuarioId}, ${video.id}, '${video.url}');"</c:if>
				<c:if test="${video.url == ''}">onclick="intranet.views.videos.show(${usuarioId}, ${video.id});"</c:if>>
				<i class="icon-play-circle"></i>
				<c:if test="${video.url != ''}">
					<img class='video-preview' width="98" src="http://i.ytimg.com/vi/${video.url}/0.jpg" alt="imagem" />
				</c:if>
				<c:if test="${video.url == ''}">
					<c:if test="${video.videoPreview != null }">
						<img class='video-preview' width="98" src="<c:url value='/${contexto}/videos/${video.id}/videopreview' />" alt="imagem" />
					</c:if>
					<c:if test="${video.videoPreview == null}">
						<img class='video-preview' width="98" src="<c:url value='/imagens/preview_undefined.png' />" alt="imagem" />
					</c:if>
				</c:if>
			</div>
			<div class='video-detalhes'>
				<a class='video-descricao' href="<c:url value='/${contexto}/videos/${video.id}/show' />">${video.descricao}</a>
				<div class='video-usuario'>Postado por: ${video.usuario.nome}</div>
				<div class="video-actions">
					<jLib:checkrole roles="editar_conteudo" conditionOr="${usuarioId == video.usuario.id}">
						<a href="<c:url value='/${contexto}/videos/${video.id}/edit'/>"><i class="icon-edit"></i> Editar</a>
						<span class="separador"></span>
					</jLib:checkrole>
					<jLib:checkrole roles="deletar_conteudo" conditionOr="${usuarioId == video.usuario.id}">
						<a class="link btn-delete" onclick="intranet.formulario.formDelete(${video.id}, 'Video');"><i class="icon-trash"></i> Deletar</a>
					</jLib:checkrole>
				</div>
			</div>
			<div class='clearfix'></div>
		</li>
	</c:forEach>
</ul>