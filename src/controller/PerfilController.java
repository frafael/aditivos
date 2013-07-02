package controller;

import java.util.List;

import model.Perfil;
import model.Usuario;

import org.hibernate.exception.ConstraintViolationException;

import br.com.bronx.accesscontrol.annotation.AccessDenied;
import br.com.bronx.accesscontrol.annotation.Roles;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import dao.PapelDao;
import dao.PerfilDao;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Resource @Path("/{contexto}/perfis") @AccessDenied(loginPage="/index/negado", accessDeniedPage="/index/negado")
public class PerfilController {

	private Result result;
	private PapelDao papelDao;
	private PerfilDao perfilDao;
	private UsuarioDao usuarioDao;
	private UsuarioSession usuarioSession;

	public PerfilController( Result result, PapelDao papelDao, UsuarioDao usuarioDao, PerfilDao perfilDao, UsuarioSession usuarioSession ) {
		this.result = result;
		this.papelDao = papelDao;
		this.perfilDao = perfilDao;
		this.usuarioDao = usuarioDao;
		this.usuarioSession = usuarioSession;
	}

	@Get("") 
	public void perfis(String contexto) {
		Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
		
		Perfil administrador = perfilDao.load(9L);
		List<Perfil> perfis = perfilDao.list(empresaId);
		if (!usuarioSession.getUsuario().getPerfil().getId().equals(administrador.getId()))
			perfis.remove(administrador);
		
		result.include("perfis", perfis);
	}

	@Get("/new") 
	public void formulario(String contexto) {
		result.include("papeis", papelDao.list());
	}

	@Get("/{id}/edit") 
	public void edit( Long id ) {
		result.include("perfil", perfilDao.load(id)).forwardTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Post("")
	public void save( Perfil perfil ) {
		perfil.setEmpresa(usuarioSession.getUsuario().getEmpresa());
		String message;
		if ( perfilDao.save(perfil) )
			message = "Perfil cadastrado com sucesso!";
		else
			message = "Ocorreu algum problema ao cadastrar o perfil!";
		result.include("message", message).redirectTo(this).perfis(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Put("")
	public void update( Perfil perfil ) {
		String message;
		if ( perfilDao.update(perfil) ) {
			if (usuarioSession.getUsuario().getPerfil().getId().equals(perfil.getId())) {
				Usuario usuario = usuarioDao.load(usuarioSession.getUsuario().getId());
				usuarioSession.setUsuario(usuario);
			}
			message = "Perfil editado com sucesso!";
		} else
			message = "Ocorreu algum problema ao editar o perfil!";
		result.include("message", message).redirectTo(this).perfis(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Delete("/{id}") @Roles(roles="deletar_perfil")
	public void delete( Long id ) {
		try {
			Perfil perfil = perfilDao.load(id);
			perfilDao.delete(perfil);
		} catch (ConstraintViolationException e) {
			result.include("message", "Existe um usuário cadastrado com esse perfil.");
		}
		result.redirectTo(this).perfis(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
}