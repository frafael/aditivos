package interceptor;

import static br.com.caelum.vraptor.view.Results.http;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import model.Empresa;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import controller.LoginController;
import dao.EmpresaDao;
import dao.UsuarioDao;
import dao.UsuarioSession;

@Intercepts
public class LoginInterceptor implements Interceptor {

	private Result result;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private HttpServletRequest request;
	private UsuarioSession usuarioSession;
	
	public LoginInterceptor( Result result, EmpresaDao empresaDao, UsuarioDao usuarioDao, HttpServletRequest request, UsuarioSession usuarioSession ) {
		this.result = result;
		this.empresaDao = empresaDao;
		this.usuarioDao = usuarioDao;
		this.request = request;
		this.usuarioSession = usuarioSession;
	}
	
	@SuppressWarnings("unchecked")
	public boolean accepts( ResourceMethod method ) {
		return !Arrays.asList(LoginController.class).contains(method.getMethod().getDeclaredAnnotations());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object object) throws InterceptionException {
		boolean isUsuarioLogado = ( usuarioSession.getUsuario() != null );
		boolean isQuerAcessarLogin = method.getResource().getType().equals(LoginController.class);
		boolean acessoNegado = method.getMethod().getName().equals("negado");
		boolean loadLogo = method.getMethod().getName().equals("logo");
		boolean imageAccess = method.getMethod().getName().equals("foto"); 
		boolean arquivo = method.getMethod().getName().equals("file") || method.getMethod().getName().equals("fileWithExtension"); 
			
		String url = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		String contexto = url.replaceAll("(/)([a-zA-Z0-9]+)(/(.+)?)?", "$2");
		Empresa empresa = empresaDao.loadByContexto(contexto);
		boolean userExist = false;
		if (isUsuarioLogado)
			userExist = usuarioDao.userExist(usuarioSession.getUsuario().getId());
		
		System.out.println(userExist);
		
		if (empresa != null || acessoNegado || loadLogo || imageAccess || arquivo) {
			if( !isUsuarioLogado && !isQuerAcessarLogin && !acessoNegado && !loadLogo && !imageAccess && !arquivo)
				result.redirectTo(LoginController.class).login(contexto);
			else {
				
					if (isUsuarioLogado){
						if (!contexto.equals(usuarioSession.getUsuario().getEmpresa().getContexto()) && !isQuerAcessarLogin && !acessoNegado && !loadLogo) {
							result.use(http()).sendError(401, "Erro sessão!");
						} else if ( !userExist ){
								usuarioSession.setUsuario(null);
								isUsuarioLogado = false;
						}
					}
					stack.next(method, object);
			}
		} else {
			result.use(http()).sendError(404, "Página não encontrada!");
		}
	}

}