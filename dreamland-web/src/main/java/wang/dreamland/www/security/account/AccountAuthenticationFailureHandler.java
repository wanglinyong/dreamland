package wang.dreamland.www.security.account;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wly on 2018/6/29.
 */
public class AccountAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private String defaultFailureUrl;

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if("User is disabled".equals(exception.getMessage())){
			request.setAttribute("error","active");
		}else {
			request.setAttribute("error", "fail");
		}
		String email = request.getParameter("username");
		request.setAttribute("email", email);
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

	@Override
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

}
