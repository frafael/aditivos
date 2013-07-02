<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/taglibs/JLib.tld" prefix="jLib"%>
<jsp:include page="../index/header.jsp" />
<script>
	$(function() {
		$.ajaxSetup({
       	 	cache: false
    	});
		intranet.views.conteudo.load(function(){
			$(this).mCustomScrollbar({
				mouseWheel: false,
				theme: "dark",
				scrollButtons:{
				    enable: true
				}
			});
						
			$(".hora").each(function() {
				var hora = $(this).text();
				hora = intranet.utils.tempoPassado(hora);
				$(this).html(hora);
			});
		});
		intranet.views.videos.load();
		intranet.views.blocos.index();
		intranet.views.mensagens.listar(filtroIdMensagens, "${usuarioLogado}");
		intranet.formulario.validacao.validate("#formNewMensagem");
		intranet.formulario.validacao.validate("#formNewRecado");
		

		$("#formNewRecado").wrapLabel();

		$(".box-cont").not(".box-feed .box-cont").each(function() {
			var elemento = $(this);
			$(this).mCustomScrollbar({
				mouseWheel: false,
				theme: "dark",
				scrollButtons:{
				    enable: true
				},
				advanced:{
				    updateOnContentResize: Boolean
				}
				
			});
		});
		
		intranet.views.aniversariantes.scroll();
		
	});
</script>
	<input type="hidden" id="usuarioLogado" value="${usuarioLogado}">
	<div class="col-1 col">
			<c:forEach items="${blocos}" var="bloco">
				<c:if test="${bloco.coluna == 1 && bloco.nome != 'Aniversários' && bloco.nome != 'Vídeos' && bloco.nome != 'feed'}">
					<div class="box"  id="bloco_${bloco.id}">
						<div class="box-title">
							<div>${bloco.nome}</div>
						</div>
						<div class="box-cont" blocoid="${bloco.id}">
							
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/conteudos/${bloco.id}'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<jLib:checkrole roles="bloco_${bloco.id}"><a href="<c:url value='/${contexto}/conteudos/new/${bloco.id}'/>" class="btn-novoconteudo"><i class="icon-plus"></i> Novo conteúdo</a></jLib:checkrole>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 1 && bloco.nome == 'Aniversários'}">
					<div id="aniversariantes" class="box">
						<div class="box-title">
							<div>Aniversários</div>
						</div>
						<div class="box-cont">
							<ul class="lista-aniversarios">
								<c:forEach items="${aniversariantes}" var="usuario">
										<li usuarioid="${usuario.id}" <c:if test="${usuario.aniversariante}">class="aniversariantedodia ativo"</c:if><c:if test="${!usuario.aniversariante && !usuario.aniversarianteDoMes}">class="inativo"</c:if><c:if test="${usuario.aniversarianteDoMes}">class="ativo"</c:if>>
											<span class="data-niver"><fmt:formatDate value="${usuario.dataDeNascimento}" pattern="dd/MM" /></span>
											<span class="usuario-nome link" title="Informações" onclick="intranet.views.usuario.show(${usuario.id});">${usuario.nome}</span>
											
											<span class='aniversario-actions'>
												<c:if test="${usuario.qtdRecados > 0}">
													<i class="icon-comments link" onclick="intranet.views.aniversariantes.verRecados(${usuario.id}, ${usuarioId});" id="totalComentarios" totalComentarios="${usuario.qtdRecados}" title="${usuario.qtdRecados} recados(s)"></i>
												</c:if>
												<jLib:checkrole roles="novo_recado">
													<c:if test="${usuario.aniversariante}">
														<i id="comentar" title="Deixar recado" class="icon-envelope-alt link" onclick="intranet.views.aniversariantes.comentar(${usuario.id}, ${usuarioId});"></i>
													</c:if>
												</jLib:checkrole>
											</span>
										</li>
								</c:forEach>
							</ul>
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/usuarios/aniversarios'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 1 && bloco.nome == 'Vídeos'}">
					<div id="videos" class="box">
						<div class="box-title">
							<div>Vídeos</div>
						</div>
						<div class="box-cont">
							<div class="videos bloco"></div>
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/videos'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<a href="<c:url value='/${contexto}/videos/new'/>" class="btn-novoconteudo">Novo vídeo</a>
						</div>
						
						<div id="videoShow">
							<form id="videoNewComentario" method="post" action="<c:url value='/${contexto}/comentarios' />">
								<input type="hidden" name="comentario.usuario.id" value="${usuarioId}">
								<input type="hidden" name="comentario.video.id" id="comentarioVideoId">
								<div class="coment-video" style="height: 100px;">
									<label>Comentário: </label>
									<textarea class="required textarea-dialog" id="videoNewComentario" name="comentario.descricao"></textarea>
									<button onclick="intranet.views.videos.comentarios.gravar(${usuarioId});" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only btn">
										<span class="ui-button-text">Gravar</span>
									</button>
								</div>
							</form>
							<div class="comentarios bloco pull-both max-width">
								<table id="comentariosVideos" class="table max-width"></table>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 1 && bloco.nome == 'feed'}">
					<div class="box box-feed">
						<div class="box-title">
							<div>Quero compartilhar com a empresa</div>
						</div>
						<div class="box-cont">
							<form id="formNewMensagem" class="frm-compartilhar" method="post" action="<c:url value='/${contexto}/mensagens'/>">
								<div class="feeds-textarea-panel">
									<textarea class="required" name="mensagem.conteudo" maxlength="140"></textarea>
									<div class="btn-enviar">
										<div class="contadorDeCaracteres">
											<div class="conta-letras length">
												140
											</div>
										</div>
										<div class="ir button" onclick="intranet.views.mensagens.gravar(${usuarioLogado});"><i class='icon-plus'></i></div>
									</div>
								</div>
							</form>
							<ul class="lista-compartilhados"></ul>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="col-2 col">
			<c:forEach items="${blocos}" var="bloco">
				<c:if test="${bloco.coluna == 2 && bloco.nome != 'Aniversários' && bloco.nome != 'Vídeos' && bloco.nome != 'feed'}">
					<div class="box"  id="bloco_${bloco.id}">
						<div class="box-title">
							<div>${bloco.nome}</div>
						</div>
						<div class="box-cont" blocoid="${bloco.id}">
							
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/conteudos/${bloco.id}'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<jLib:checkrole roles="bloco_${bloco.id}"><a href="<c:url value='/${contexto}/conteudos/new/${bloco.id}'/>" class="btn-novoconteudo"><i class="icon-plus"></i> Novo conteúdo</a></jLib:checkrole>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 2 && bloco.nome == 'Aniversários'}">
					<div id="aniversariantes" class="box">
						<div class="box-title">
							<div>Aniversários</div>
						</div>
						<div class="box-cont">
							<ul class="lista-aniversarios">
								<c:forEach items="${aniversariantes}" var="usuario">
										<li usuarioid="${usuario.id}" <c:if test="${usuario.aniversariante}">class="aniversariantedodia ativo"</c:if><c:if test="${!usuario.aniversariante && !usuario.aniversarianteDoMes}">class="inativo"</c:if><c:if test="${usuario.aniversarianteDoMes}">class="ativo"</c:if>>
											<span class="data-niver"><fmt:formatDate value="${usuario.dataDeNascimento}" pattern="dd/MM" /></span>
											<span class="usuario-nome link" title="Informações" onclick="intranet.views.usuario.show(${usuario.id});">${usuario.nome}</span>
											
											<span class='aniversario-actions'>
												<c:if test="${usuario.qtdRecados > 0}">
													<i class="icon-comments link" onclick="intranet.views.aniversariantes.verRecados(${usuario.id}, ${usuarioId});" id="totalComentarios" totalComentarios="${usuario.qtdRecados}" title="${usuario.qtdRecados} recados(s)"></i>
												</c:if>
												<jLib:checkrole roles="novo_recado">
													<c:if test="${usuario.aniversariante}">
														<i id="comentar" title="Deixar recado" class="icon-envelope-alt link" onclick="intranet.views.aniversariantes.comentar(${usuario.id}, ${usuarioId});"></i>
													</c:if>
												</jLib:checkrole>
											</span>
										</li>
								</c:forEach>
							</ul>
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/usuarios/aniversarios'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
						</div>
					</div>
				</c:if>
				
				<c:if test="${bloco.coluna == 2 && bloco.nome == 'Vídeos'}">
					<div id="videos" class="box">
						<div class="box-title">
							<div>Vídeos</div>
						</div>
						<div class="box-cont">
							
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/videos'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<a href="<c:url value='/${contexto}/videos/new'/>" class="btn-novoconteudo">Novo vídeo</a>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="col-3 col">
			<c:forEach items="${blocos}" var="bloco">
				<c:if test="${bloco.coluna == 3 && bloco.nome != 'Aniversários' && bloco.nome != 'Vídeos' && bloco.nome != 'feed'}">
					<div class="box"  id="bloco_${bloco.id}">
						<div class="box-title">
							<div>${bloco.nome}</div>
						</div>
						<div class="box-cont" blocoid="${bloco.id}">
							
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/conteudos/${bloco.id}'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<jLib:checkrole roles="bloco_${bloco.id}"><a href="<c:url value='/${contexto}/conteudos/new/${bloco.id}'/>" class="btn-novoconteudo"><i class="icon-plus"></i> Novo conteúdo</a></jLib:checkrole>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 3 && bloco.nome == 'Aniversários'}">
					<div id="aniversariantes" class="box">
						<div class="box-title">
							<div>Aniversários</div>
						</div>
						<div class="box-cont">
							<ul class="lista-aniversarios">
								<c:forEach items="${aniversariantes}" var="usuario">
										<li usuarioid="${usuario.id}" <c:if test="${usuario.aniversariante}">class="aniversariantedodia ativo"</c:if><c:if test="${!usuario.aniversariante && !usuario.aniversarianteDoMes}">class="inativo"</c:if><c:if test="${usuario.aniversarianteDoMes}">class="ativo"</c:if>>
											<span class="data-niver"><fmt:formatDate value="${usuario.dataDeNascimento}" pattern="dd/MM" /></span>
											<span class="usuario-nome link" title="Informações" onclick="intranet.views.usuario.show(${usuario.id});">${usuario.nome}</span>
											
											<span class='aniversario-actions'>
												<c:if test="${usuario.qtdRecados > 0}">
													<i class="icon-comments link" onclick="intranet.views.aniversariantes.verRecados(${usuario.id}, ${usuarioId});" id="totalComentarios" totalComentarios="${usuario.qtdRecados}" title="${usuario.qtdRecados} recados(s)"></i>
												</c:if>
												<jLib:checkrole roles="novo_recado">
													<c:if test="${usuario.aniversariante}">
														<i id="comentar" title="Deixar recado" class="icon-envelope-alt link" onclick="intranet.views.aniversariantes.comentar(${usuario.id}, ${usuarioId});"></i>
													</c:if>
												</jLib:checkrole>
											</span>
										</li>
								</c:forEach>
							</ul>
						</div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/usuarios/aniversarios'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 3 && bloco.nome == 'Vídeos'}">
					<div id="videos" class="box">
						<div class="box-title">
							<div>Vídeos</div>
						</div>
						<div class="box-cont"></div>
						<div class="bnts-funcoes">
							<a href="<c:url value='/${contexto}/videos'/>" class="btn-todos"><i class="icon-folder-close"></i> Ver todos</a>
							<a href="<c:url value='/${contexto}/videos/new'/>" class="btn-novoconteudo">Novo vídeo</a>
						</div>
					</div>
				</c:if>
				<c:if test="${bloco.coluna == 3 && bloco.nome == 'feed'}">
					<div class="box box-feed">
						<div class="box-title">
							<div>Quero compartilhar com a empresa</div>
						</div>
						<div class="box-cont">
							<form id="formNewMensagem" class="frm-compartilhar" method="post" action="<c:url value='/${contexto}/mensagens'/>">
								<div class="feeds-textarea-panel">
									<textarea class="required" name="mensagem.conteudo" maxlength="140"></textarea>
									<div class="btn-enviar">
										<div class="contadorDeCaracteres">
											<div class="conta-letras length">
												140
											</div>
										</div>
										<div class="ir button" onclick="intranet.views.mensagens.gravar(${usuarioLogado});"><i class='icon-plus'></i></div>
									</div>
								</div>
							</form>
							<ul class="lista-compartilhados"></ul>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>

<!-- DIALOGS -->
<div id="usuarioShow"></div>

<div style="display: none" id="newRecado" class="box">
	<form id="formNewRecado">
		<input type="hidden" name="recado.destinatario.id" id="recadoDestinatario" />
		<input type="hidden" name="recado.remetente.id" value="${usuarioId}" />
		<div style="height: 158px;">
			<label>Recado: </label>
			<textarea class="required textarea-dialog" name="recado.mensagem" maxlength="140"></textarea>
			<button onclick="intranet.views.aniversariantes.recados.gravar(${usuarioId});" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only btn">
				<span class="ui-button-text">Gravar</span>
			</button>
		</div>
		<div class="recados bloco pull-both">
			<table id="recadosAniversariantes" class="table max-width recados"></table>
		</div>
	</form>
</div>

<div style="display: none" id="verRecados" class="box">
	<div class="recados bloco pull-both">
		<table id="recados" class="table max-width"></table>
	</div>
</div>

<div id="videoShow" style="display:none">
	<form  id="videoNewComentario" method="post" action="<c:url value='/${contexto}/comentarios' />">
		<input type="hidden" name="comentario.usuario.id" value="${usuarioId}">
		<input type="hidden" name="comentario.video.id" id="comentarioVideoId">
		<div class="coment-video" style="height: 100px;">
			<label>Comentário: </label>
			<textarea class="required textarea-dialog" id="videoNewComentario" name="comentario.descricao"></textarea>
			
			<button onclick="intranet.views.videos.comentarios.gravar(${usuarioId});" type="button" class="btn">
				Gravar
			</button>
		</div>
	</form>
	<div class="comentarios">
		<table id="comentariosVideos" class="table table-bordered dialog"></table>
	</div>
</div>
<!-- DIALOGS -->

<script id="post-compartilhado-tpl" type="text/template">
	<li mensagemid="{{mensagem.id}}" id="{{mensagem.id}}">
		<div class="post-title">
			<div  class='foto-container link post-photo'>
				<img class="link" onclick="intranet.views.usuario.show({{mensagem.remetente.id}})" src="/${contexto}/usuarios/{{mensagem.remetente.id}}/foto/thumb">
			</div>
				
			
			<div class="post-usuario-info link" onclick="intranet.views.usuario.show({{mensagem.remetente.id}})">
				<div class="post-usuario">{{mensagem.remetente.nome}}</div>
				<div class="post-cargo">{{mensagem.remetente.cargo.nome}}</div>
			</div>
			<div class="post-info">
				<span class="link" onclick="intranet.views.mensagens.curtir({{mensagem.id}}, {{usuarioLogado}});">
					{{botaoValue}} <i class=" icon-thumbs-up"> {{totalCurtidas}}</i>
				</span><br/>
				<span class="post-time">{{tempoPassado}}</span>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="post-text">
			{{texto}}
		</div>
	</li>
</script>
<jsp:include page="../index/footer.jsp" />