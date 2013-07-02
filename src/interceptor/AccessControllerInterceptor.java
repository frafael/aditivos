package interceptor;

import java.util.List;

import util.LoginControl;

import groovy.swing.factory.ComponentFactory;
import br.com.bronx.accesscontrol.exception.RestrictionAnnotationException;
import br.com.bronx.accesscontrol.interfaces.Login;
import br.com.bronx.accesscontrol.restriction.RestrictionChecker;
import br.com.bronx.accesscontrol.restriction.RestrictionResult;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;

import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.Results;

/**
 * This interceptor controls the access to the resources, based on their
 * restrictions annotations.<br>
 * The restrictions might be method restrictions or resource restrictions,
 * according to where the annotations had been placed.
 * 
 * @author Diego Maia da Silva a.k.a. Bronx
 */
 @Intercepts
public class AccessControllerInterceptor implements Interceptor {

	private RestrictionChecker restrictionChecker = new RestrictionChecker();
	private Result result;
	private Login login;
	
	public AccessControllerInterceptor(LoginControl login,  Result result){
		this.login = login;
		this.result = result;
	}
	
	public boolean accepts(ResourceMethod method) {
		return this.restrictionChecker.hasRestriction(method.getMethod());
	}
	
	public void intercept(InterceptorStack stack, ResourceMethod resourceMethod,
			Object resourceInstance) throws InterceptionException {
		RestrictionResult restrictionResult;
		try {
			restrictionResult = restrictionChecker.checkRestrictions(resourceMethod.getMethod(), login);
		} catch (RestrictionAnnotationException e) {
			throw new InterceptionException(e);
		}
		if (restrictionResult.isRestricted()){
			result.use(Results.page()).redirect(restrictionResult.getDestination());
		} else {
			stack.next(resourceMethod, resourceInstance);
		}
	}
}
