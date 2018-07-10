package wang.dreamland.www.security.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import wang.dreamland.www.common.CodeValidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wly on 2018/6/29.
 */
public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public static final String phoneParameter = "telephone";
	public static final String codeParameter = "phone_code";
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	protected PhoneAuthenticationFilter( ) {
		super( new AntPathRequestMatcher("/phoneLogin") );
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
			String phone = this.obtainPhone(request);
			String phone_code = this.obtainValidateCode(request);
			if(phone == null) {
				phone = "";
			}
			if(phone_code == null) {
				phone_code = "";
			}

			phone = phone.trim();
			String cache_code = redisTemplate.opsForValue().get( phone );
			boolean flag = CodeValidate.validateCode(phone_code,cache_code);
			if(!flag){
				throw new PhoneNotFoundException( "手机验证码错误" );
			}
			PhoneAuthenticationToken authRequest = new PhoneAuthenticationToken(phone);
			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);

	}

	protected void setDetails(HttpServletRequest request, PhoneAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	protected String obtainPhone(HttpServletRequest request) {
		return request.getParameter(phoneParameter);
	}
	protected String obtainValidateCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}

}
