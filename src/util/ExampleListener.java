package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Usuario;

import org.hibernate.envers.RevisionListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ExampleListener implements RevisionListener {
	
	public void newRevision(Object revisionEntity) {
			
		Usuario usuario = (Usuario) getSession().getAttribute("userSession");
		
        CustomRevisionEntity exampleRevEntity = (CustomRevisionEntity) revisionEntity;
        exampleRevEntity.setUsuario(usuario);
        
	}
	
	private HttpServletRequest getRequest() {  
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        return requestAttributes.getRequest();  
    }  
  
    private HttpSession getSession() {  
        return getRequest().getSession();  
    }
 
}
