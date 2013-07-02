package util;

import br.com.bronx.accesscontrol.interfaces.Login;
import br.com.bronx.accesscontrol.interfaces.Profile;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped	
public class LoginControl implements Login{

	private boolean loggedIn;
	private Profile profile;

	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
