package controller;

import static br.com.caelum.vraptor.view.Results.http;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import model.Empresa;
import model.Hash;
import model.Usuario;

import org.hibernate.NonUniqueResultException;

import sun.misc.BASE64Encoder;
import util.Email;
import util.LoginSvc;
import util.Tripa;
import util.Util;
import br.com.bronx.accesscontrol.interfaces.Login;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import dao.EmpresaDao;
import dao.HashDao;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Resource @Path("/{contexto}") 
public class LoginController {

	private Result result;
	private Login login;
	private HashDao hashDao;
	private HttpServletRequest request;
	private UsuarioDao usuarioDao;
	private EmpresaDao empresaDao;
	private UsuarioSession usuarioSession;

	public LoginController(Result result, Login login, HashDao hashDao, HttpServletRequest request, UsuarioDao usuarioDao, UsuarioSession usuarioSession, EmpresaDao empresaDao ) {
		this.result = result;
		this.login = login;
		this.hashDao = hashDao;
		this.request = request;
		this.usuarioDao = usuarioDao;
		this.empresaDao = empresaDao;
		this.usuarioSession = usuarioSession;
	}
	
	@Get("/loginsvc/{token}")
	public void loginsvc(String token) throws IOException {
		String retorno; 
		
		String aux = "fulano|senha|2012100514";
		
		token = LoginSvc.encryptBlowfish(aux, "1N7R4N37");///
		
		Tripa tripa = LoginSvc.DestrinchaToken(token);
		if (tripa != null) {
			Usuario usuario = usuarioDao.loginByEmail(tripa.usuario, tripa.senha, 17L);
			if ( usuario != null ) {
				retorno = LoginSvc.EntrinchaResp(tripa.timestamp, "OK");
			} else {
				retorno = LoginSvc.EntrinchaResp(tripa.timestamp, "NOTFOUND");
			}
		} else
			retorno = "ERROR";
		result.include("token", retorno);
	}
	
	@Get("/redefinirsenha")
	public void redefinirSenha(String contexto) {
		Empresa empresa = empresaDao.loadByContexto(contexto);
		result.include("contexto", contexto).include("empresa", empresa);
	}
	
	@Get("/verificalogin")
	public void verificaLoginParaSol(String contexto) {
		if (usuarioSession.getUsuario() != null) {
			Usuario usuario = usuarioSession.getUsuario();
			
			String str =  usuario.getNome()+"|"+usuario.getId();
			BASE64Encoder encoder = new BASE64Encoder();
			String encript = encoder.encodeBuffer(str.getBytes()).trim();
			
			result.redirectTo("http://" + request.getHeader("Host") + "/sol/session/"+encript);
		} else 
			result.redirectTo(this).login(contexto);
	}

	@Post("/redefinirsenha")
	public void redefinirSenha(String login, String contexto) {
		Date date = new Date();
		Long dateTime = date.getTime();
		String hash = Util.toMD5(dateTime.toString());
		Long empresaId = empresaDao.loadByContexto(contexto).getId();
		Usuario usuario = usuarioDao.load(login, empresaId);
		
		if( usuario != null  ) {
			if ( usuario.getEmail() != null || !usuario.getEmail().equals("") ) {
				String link = "<a href='http://intranet.grupofortes.com.br/"+ usuario.getEmpresa().getContexto() +"/recuperarsenha/"+ usuario.getId() +"/token/"+ hash +"'>aqui</a>";
				StringBuilder message = new StringBuilder();
					message.append("Caro(a) "+ usuario.getNome() + ",<br><br>Para redefinir a senha da intranet, clique " + link +".");
	
				String[] email = { usuario.getEmail() };
				
				try {
					Hash hashAntigo = hashDao.load(usuario.getId());
					if ( hashAntigo != null )
						hashDao.delete(hashAntigo);
					Hash hashObj = new Hash();
					hashObj.setUsuario(usuario);
					hashObj.setHash(hash);
					
					Email emailSender = new Email(usuario);
					emailSender.send(email, "Mudança de senha.", message.toString());
					hashDao.save(hashObj);
	
					result.include("mensagem", "Um email de mudança de senha foi enviado para o email: " + usuario.getEmail());
				} catch (Exception e) {
					result.include("mensagem", "Erro ao enviar e-mail.");
				}
			}
		}
		else {
			result.include("mensagem", "Este login não existe ou você não possui e-mail cadastrado, contate o administrador da sua unidade!");
		}
		result.redirectTo(this).redefinirSenha(contexto);
	}

	@Get("/recuperarsenha/{id}/token/{hash}")
	public void recuperarSenha(Long id, String hash, String contexto) {
		Empresa empresa = empresaDao.loadByContexto(contexto);
		try {
			Hash hashObj = hashDao.load(id);
			if (hashObj.getHash().equals(hash))
				result.include("hash", hashObj).include("contexto", contexto).include("empresa", empresa);
			else 
				result.use(http()).sendError(404, "Acesso negado!");
		} catch (Exception e) {
			result.use(http()).sendError(404, "Acesso negado!");
		}
	}
	
	@Post("/recuperarsenha/{id}/token/{hash}")
	public void recuperarSenha(Long id, String hash, String novasenha, String confirmasenha, String contexto) {
		Hash hashObj = hashDao.load(id);
		Usuario usuario = usuarioDao.load(id);
		if (hashObj.getHash().equals(hash)) {
			usuario.setSenha(Util.toMD5(novasenha));
			usuarioDao.update(usuario);
			hashDao.delete(hashObj);
			result.include("message", "Sua senha foi atualizada com sucesso.");
		} else {
			result.redirectTo(IndexController.class).negado();
		}
		result.redirectTo(this).login(contexto);
	}
	
	@Get({"/login", "/login/"})
	public void login(String contexto) {
		Empresa empresa = empresaDao.loadByContexto(contexto);
		if( usuarioSession.getUsuario() != null ) 
			result.redirectTo(IndexController.class).index(contexto);
		else 
			result.include("idCli", empresa.getId()).include("contexto", contexto).include("empresa", empresa);
	}

	@Get("/logout")
	public void logout() {
		String contexto = usuarioSession.getUsuario().getEmpresa().getContexto();
		usuarioSession.setUsuario(null);
		login.setLoggedIn(false);
		login.setProfile(null);
		result.redirectTo(this).login(contexto);
	}

	@Post("/login")
	public void login(String nome, String senhaCriptografada, Long idCli, String contexto) {
		try {
			Usuario usuario = usuarioDao.login(nome, senhaCriptografada, idCli);
			if( usuario == null ) {
				result.include("message", "Login ou senha inválidos.").redirectTo(this).login(contexto);
			} else {
				Long qdtDeAcessos = usuario.getQtdDeAcessos();
				usuarioSession.setUsuario(usuario.clone());
				usuario.setUltimoLogin(new Date());
				usuario.setQtdDeAcessos(++qdtDeAcessos);
				usuarioDao.update(usuario);
				login.setLoggedIn(true);//se usuário logou com sucesso 
				login.setProfile(usuario.getPerfil());//idem 
				result.redirectTo(IndexController.class).index(contexto);
			}
		} catch(NonUniqueResultException e) {
			result.include("message", "Usuário duplicado no sistema.").redirectTo(this).login(contexto);
		}
	}
}
