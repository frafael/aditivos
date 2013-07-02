package interceptor;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.HtmlUtils;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.ExecuteMethodInterceptor;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts(before=ExecuteMethodInterceptor.class)
public class XSSInterceptor implements Interceptor {

	private HttpServletRequest request;

	public XSSInterceptor( HttpServletRequest request ) {
		this.request = request;
	}

	public boolean accepts(ResourceMethod method) {
		String methodName = method.getMethod().getName();
		boolean notAcceptMethod = true;

		return notAcceptMethod;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object object) throws InterceptionException {
		Class<?>[] types = method.getMethod().getParameterTypes();
		if( types.length > 0 ) {
			for( Field field : types[0].getDeclaredFields() ) {
				if( field.getType().equals(String.class) ) {
					String getNameParameter = types[0].getSimpleName().toLowerCase() + "." + field.getName();
					String getNameScape = (String) HtmlUtils.htmlEscape(request.getParameter(getNameParameter));
					if(request.getParameterValues(getNameParameter) != null)
						request.getParameterValues(getNameParameter)[0] = getNameScape;
				}
			}
		}
		stack.next(method, object);
	}
}