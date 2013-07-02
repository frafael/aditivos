package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Empresa;
import model.Papel;
import model.Perfil;
import model.Usuario;
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
import dao.EmpresaDao;
import dao.PerfilDao;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Resource @Path("/{contexto}/empresas") @Roles(roles="empresas") @AccessDenied(loginPage="/index/negado", accessDeniedPage="/index/negado")
public class EmpresaController {
	
	private Result result;
	private PerfilDao perfilDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private UsuarioSession usuarioSession;
	
	public EmpresaController( Result result, EmpresaDao empresaDao, PerfilDao perfilDao, UsuarioDao usuarioDao,
			UsuarioSession usuarioSession ) {
		this.result = result;
		this.perfilDao = perfilDao;
		this.empresaDao = empresaDao;
		this.usuarioDao = usuarioDao;
		this.usuarioSession = usuarioSession;
	}
	
	@Get("")
	public void empresas(String contexto) {
		result.include("empresas", empresaDao.list());
	}
	
	@Get("/new") @Roles(roles="nova_empresa")
	public void formulario(String contexto) {}
	
	@Get("/{id}/edit") @Roles(roles = "editar_cargo")
	public void edit(Long id) {
		result.include("empresa", empresaDao.load(id)).forwardTo(this).formulario(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
	
	@Post("")
	public void save( Empresa empresa, UploadedFile logo){
		String message;
		if ( empresaDao.save(empresa) ) {
			empresa.newDir();
			configureSystem(empresa);
			try {
				empresa.uploadLogo(logo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			message = "Empresa cadastrada com sucesso!";
		} else
			message = "Ocorreu algum problema ao cadastrar a empresa!";
		result.include("message", message).redirectTo(this).empresas(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
	
	public void configureSystem( Empresa empresa ){

		Perfil perfilAdminOrigin = perfilDao.load(1L);
		Perfil perfilAdmin = new Perfil();
			perfilAdmin.setNome("Moderador");
			List<Papel> papeisAdmin = perfilAdminOrigin.getPapeis();
			List<Papel> papeisA = new ArrayList<Papel>();
				for (Papel papel : papeisAdmin) {
					papeisA.add(papel);
				}
			perfilAdmin.setPapeis(papeisA);
			perfilAdmin.setEmpresa(empresa);
		perfilDao.save(perfilAdmin);
		
		Perfil perfilComumOrigin = perfilDao.load(2L);
		Perfil perfilComum = new Perfil();
			perfilComum.setNome("Usuaŕio Comum");
			List<Papel> papeisComum = perfilComumOrigin.getPapeis();
			List<Papel> papeisC = new ArrayList<Papel>();
				for (Papel papel : papeisComum) {
					papeisC.add(papel);
				}
			perfilComum.setPapeis(papeisC);
			perfilComum.setEmpresa(empresa);
		perfilDao.save(perfilComum);
		
		Usuario admin = new Usuario();
			admin.setNome("Administrador");
			admin.setLogin("admin");
			admin.setAtivo(true);
			admin.setDataDeNascimento(new Date());
			admin.setQtdDeAcessos(0L);
			admin.setSenha("200820e3227815ed1756a6b531e7e0d2");
			admin.setPerfil(perfilAdmin);
			admin.setEmpresa(empresa);
		usuarioDao.save(admin);
	}
	
	@Get("/{id}/logo")
	public Download logo( Long id ) {
		Empresa empresaObj = empresaDao.load(id);
		String intranet_home = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		String logoFileName = empresaObj.getLogoFileName();
		String path;
		String empresa = id.toString();

		path = intranet_home + separator + empresa + separator + "imagens" + separator + "logo" + separator + logoFileName;
		File logo = new File(path);
		FileDownload file = new FileDownload(logo, "image/jpg", empresa + ".jpg");

		return file;
	}
	
	@Put("")
	public void update( Empresa empresa, UploadedFile logo ) {
		String message;
		Empresa emp = empresaDao.load(empresa.getId());
		
			if( logo == null && emp.getLogoFileName()!=null) {
				empresa.setLogoFileName(emp.getLogoFileName());
			} else if (logo != null) {
				try {
					if( emp.getLogoFileName()!=null)
						empresa.removeLogoAntiga(empresa.getId().toString(), emp.getLogoFileName());
					empresa.uploadLogo(logo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		if ( empresaDao.update(empresa) )
			message = "Empresa editada com sucesso!";
		else
			message = "Ocorreu algum problema ao editar a empresa!";
		result.include("message", message).redirectTo(this).empresas(usuarioSession.getUsuario().getEmpresa().getContexto());
	}

	@Delete("/{id}") @Roles(roles = "deletar_empresa")
	public void delete( Long id ) {
		Empresa empresa = empresaDao.load(id);
		empresaDao.delete(empresa);
		result.redirectTo(this).empresas(usuarioSession.getUsuario().getEmpresa().getContexto());
	}
}