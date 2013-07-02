package controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Perfil;
import model.Usuario;
import util.Util;
import br.com.bronx.accesscontrol.annotation.AccessDenied;
import br.com.bronx.accesscontrol.annotation.Roles;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import dao.PerfilDao;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Resource @Path("/{contexto}/usuarios") @AccessDenied(loginPage="/index/negado", accessDeniedPage="/index/negado")
public class UsuarioController {

	private Result result;
	private PerfilDao perfilDao;
	private UsuarioDao usuarioDao;
	private UsuarioSession usuarioSession;

	public UsuarioController( Result result, PerfilDao perfilDao,
			 UsuarioDao usuarioDao, UsuarioSession usuarioSession ) {
		this.result = result;
		this.perfilDao = perfilDao;
		this.usuarioDao = usuarioDao;
		this.usuarioSession = usuarioSession;
	}

	@Get("")
	public void usuarios(String contexto) {
		
	}
	
	@Get("/filtro/{filtro}/unidade/{unidade}/setor/{setor}/{search}")
	public void search(Long filtro, String search) {
//	    String s = Normalizer.normalize(search, Normalizer.Form.NFD);
//	    s.replaceAll("[^\\p{ASCII}]", "");
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		List<Usuario> usuarios = usuarioDao.search(filtro, search, empresaId);
		result.include("usuarios", usuarios);
	}
	
	@Get("/new") @Roles(roles="novo_usuario")
	public void formulario(String contexto) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		Perfil administrador = perfilDao.load(9L);
		List<Perfil> perfis = perfilDao.list(empresaId);
		if (!usuarioSession.getUsuario().getPerfil().getId().equals(administrador.getId()))
			perfis.remove(administrador);
		result.include("perfis", perfis);
	}

	@Get({"/{id}/foto", "/{id}/foto/{thumb}"})
	public Download foto( Long id, String thumb ) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		Usuario usuario = usuarioDao.load(id);
		String intranet_home = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		String fotoFileName = usuario.getFotoFileName();
		String path = intranet_home + separator + "imagens/photo_unregistered.png";
		
		if(fotoFileName != null && !fotoFileName.isEmpty()){
			String empresa = usuarioSession.getUsuario().getEmpresa().getId().toString();
			if( thumb == null )
				path = intranet_home + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + fotoFileName;
			else
				path = intranet_home + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + "thumb_" + fotoFileName;
		}
		File foto = new File(path);
		FileDownload file = new FileDownload(foto, "image/jpg", usuario.getId().toString() + ".jpg");

		return file;
	}

	@Get("/{id}/show")
	public void show( Long id ) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		result.include("usuario", usuarioDao.show(id, empresaId));
	}

	@Get("/{id}/edit")
	public void edit( String contexto, Long id ) {
		if( usuarioSession.getUsuario().getPerfil().getRoles().contains("novo_usuario") || usuarioSession.getUsuario().getId().equals(id) )
			result.include("usuario", usuarioDao.load(id))
				.forwardTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
		else result.redirectTo(IndexController.class).negado();
	}
	
	@Get("/editperfil")
	public void editPerfil() {
		result.include("usuario", usuarioDao.load(usuarioSession.getUsuario().getId())).forwardTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Post("") @Roles(roles="novo_usuario")
	public void save(Usuario usuario, UploadedFile foto) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		if( usuarioDao.isLoginExiste( usuario.getLogin(), empresaId ) ) {
			result.include("loginExiste", "Login já existe no banco de dados.").redirectTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
			return;
		} try {
			String senhaCriptografada = Util.toMD5(usuario.getSenha());
			usuario.setEmpresa(usuarioSession.getUsuario().getEmpresa());
			usuario.setSenha(senhaCriptografada);
			usuario.uploadFoto(usuarioSession.getUsuario().getEmpresa().getId().toString(), foto);
			if (usuarioDao.save(usuario)) {
				result.include("message", "Usuário cadastrado com sucesso!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.include("message", "Não foi possível cadastrar a foto do usuário.");
		} catch (Exception e) {
			e.printStackTrace();
			result.include("message", "Erro ao cadastrar usuário.");
			try {
				usuario.removeFotoAntiga(usuarioSession.getUsuario().getEmpresa().getId().toString(), usuario.getFotoFileName());
			} catch (Exception e2) {
				e2.printStackTrace();
				result.include("message", "Não foi possível remover a foto do usuário que foi cadastrada.");
			}
		}
		result.redirectTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Get("/verificardisponibilidade/{login}")
	public void verificarDisponibilidade( String login ) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		result.use(json()).from(usuarioDao.isLoginExiste(login, empresaId)).serialize();
	}

	@Put("")
	public void update( Usuario usuario, UploadedFile foto) {
		Usuario user = usuarioDao.load(usuario.getId());
		usuario.setSenha(user.getSenha());
		usuario.setLogin(user.getLogin());
		usuario.setEmpresa(user.getEmpresa());
		usuario.setQtdDeAcessos(user.getQtdDeAcessos());
		usuario.setUltimoLogin(usuarioSession.getUsuario().getUltimoLogin());

		if (usuario.getPerfil() == null) {
			Perfil perfil = perfilDao.load(user.getPerfil().getId());
			usuario.setPerfil(perfil);
		} else {
			Perfil perfil = perfilDao.load(usuario.getPerfil().getId());
			usuario.setPerfil(perfil);
		}
		
		if( foto == null ) {
			usuario.setFotoFileName(user.getFotoFileName());
		} else {
			try {
				String empresa = usuarioSession.getUsuario().getEmpresa().getId().toString();
				usuario.removeFotoAntiga(empresa, user.getFotoFileName());
				usuario.uploadFoto(empresa, foto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String message;
		if ( usuarioDao.update(usuario) ) 
			message = "Usuário editado com sucesso!";
		else
			message = "Ocorreu algum problema ao editar o usuário!";
		result.include("message", message);
		
		if( usuarioSession.getUsuario().getId().equals(user.getId()) )
			usuarioSession.setUsuario(usuario);
		result.redirectTo(this).edit(usuarioSession.getUsuario().getEmpresa().getContexto(), usuario.getId());
	}
	
	@Get("/editsenha")
	public void editSenha(String contexto){}

	@Put("/editsenha")
	public void editSenha( String senhaAtualCriptografada, String novaSenha ) {
		Usuario usuario = usuarioDao.load( usuarioSession.getUsuario().getId() );
		if ( usuario.getSenha().equals(senhaAtualCriptografada) ) {
			usuario.setSenha(Util.toMD5(novaSenha));
			usuarioDao.update(usuario);
			usuarioSession.setUsuario(usuario);
			result.include("message", "Senha atualizada com sucesso.");
		} else {
			result.include("message", "Senha atual não confere com a senha salva.");
		}
		result.redirectTo(this).editSenha(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
	
	@Get("/{id}/editsenha") @Roles(roles = "definir_senha")
	public void editSenhaAdmin(String contexto, Long id){
		Usuario usuario = usuarioDao.load( id );
		result.include("usuario", usuario);
	}

	@Put("{id}/editsenha") @Roles(roles = "definir_senha")
	public void editSenhaAdmin( Long id, String novaSenha ) {
		Usuario usuario = usuarioDao.load( id );
		usuario.setSenha(Util.toMD5(novaSenha));
		usuarioDao.update(usuario);
		result.include("message", "Senha atualizada com sucesso.");
		result.redirectTo(this).editSenhaAdmin(usuarioSession.getUsuario().getEmpresa().getContexto(), id);
	}
	
	@Get("/checkrole/{role}")
	public void checkRole(String role){
		boolean permiteExcluirRecado = usuarioSession.getUsuario().getPerfil().getRoles().contains(role);
		result.use(json()).from(permiteExcluirRecado).serialize();
	}

	@Delete("/{id}") @Roles(roles="deletar_usuario")
	public void delete( Long id ) {
		Usuario usuario = usuarioDao.load(id);
		usuario.setAtivo(false);
		usuarioDao.update(usuario);
		result.redirectTo(this).usuarios(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
}