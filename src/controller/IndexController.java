package controller;

import static br.com.caelum.vraptor.view.Results.http;

import java.io.File;
import java.util.List;

import model.Empresa;
import model.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import dao.EmpresaDao;
import dao.UsuarioSession;

@Resource 
public class IndexController {

	private Result result;
	private EmpresaDao empresaDao;
	private UsuarioSession usuarioSession;

	public IndexController( Result result, EmpresaDao empresaDao, UsuarioSession usuarioSession ) {
		this.result = result;
		this.empresaDao = empresaDao;
		this.usuarioSession = usuarioSession;
	}

	@Get({"/{contexto}", "/{contexto}/"})
	public void index(String contexto) {
		if( usuarioSession.getUsuario() == null )
			result.redirectTo(LoginController.class).login(contexto);
		else
			result.include("usuarioLogado", usuarioSession.getUsuario().getId());
	}
	
	public void negado() {
		result.use(http()).sendError(403, "Acesso negado!");
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
}