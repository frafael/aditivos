package interceptor;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Papel;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import dao.PapelDao;
import dao.UsuarioSession;

@Intercepts
public class UsuarioSessionInterceptor implements Interceptor {

	private Result result;
	private PapelDao papelDao;
	private HttpServletRequest request;
	private UsuarioSession usuarioSession;

	public UsuarioSessionInterceptor( Result result, PapelDao papelDao, HttpServletRequest request, UsuarioSession usuarioSession ) {
		this.result = result;
		this.request = request;
		this.papelDao = papelDao;
		this.usuarioSession = usuarioSession;
	}

	public boolean accepts(ResourceMethod method) {
		return true;
	}
	public void intercept(InterceptorStack stack, ResourceMethod method, Object object) throws InterceptionException {
		if( usuarioSession.getUsuario() != null ) {
			Long id = usuarioSession.getUsuario().getId();
			
			List<Papel> papeis = usuarioSession.getUsuario().getPerfil().getPapeis();
			Collections.sort(papeis);
			
			result.include("usuarioNome", usuarioSession.getUsuario().getNome())
				.include("usuarioFotoFileName", usuarioSession.getUsuario().getFotoFileName())
				.include("usuarioId", usuarioSession.getUsuario().getId())
				.include("usuarioEmpresaId", usuarioSession.getUsuario().getEmpresa().getId())
				.include("usuarioEmpresaNome", usuarioSession.getUsuario().getEmpresa().getNome())
				.include("contexto", usuarioSession.getUsuario().getEmpresa().getContexto())
				.include("usuarioRamal", usuarioSession.getUsuario().getRamal())
				.include("usuarioPlaca", usuarioSession.getUsuario().getPlaca())
				.include("usuarioCelular", usuarioSession.getUsuario().getCelular())
				.include("papeis", papeis)
				.include("isAdmin", usuarioSession.getUsuario().getPerfil().getNome().equals("Administrador"))
				.include("papeisSize", papeis.size())
				.include("version", "2.2.1");
		}
		System.out.println("Interceptando: " + request.getRequestURI());
		stack.next(method, object);
	}
}