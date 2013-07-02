package interceptor;

import java.util.List;

import model.Usuario;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Intercepts
public class IndexInterceptor implements Interceptor {

	private Result result;
	private UsuarioDao usuarioDao;
	private UsuarioSession usuarioSession;
	
	public IndexInterceptor( Result result, UsuarioDao usuarioDao, UsuarioSession usuarioSession ) {
		this.result = result;
		this.usuarioDao = usuarioDao;
		this.usuarioSession = usuarioSession;
	}

	public boolean accepts(ResourceMethod method) {
		return method.getMethod().getName().equals("index");
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object object) throws InterceptionException {
		if( usuarioSession.getUsuario() != null ) {
			boolean blocosAll = false;
			Long empresaId = usuarioSession.getUsuario().getEmpresa().getId();
			List<Usuario> usuarios = usuarioDao.aniversariantes(empresaId);
			result.include("aniversariantes", usuarios);
		}
		stack.next(method, object);
	}
}