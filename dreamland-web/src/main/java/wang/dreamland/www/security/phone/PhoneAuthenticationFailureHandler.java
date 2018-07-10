package wang.dreamland.www.security.phone;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wly on 2018/6/29.
 */
public class PhoneAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private String defaultFailureUrl;

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String phone = request.getParameter("telephone");
		request.setAttribute("phoneError", "phone");
		request.setAttribute("phoneNum", phone);
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
