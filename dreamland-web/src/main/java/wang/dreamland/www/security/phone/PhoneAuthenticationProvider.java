package wang.dreamland.www.security.phone;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.*;

/**
 * Created by wly on 2018/6/29.
 */
public class PhoneAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		PhoneAuthenticationToken authenticationToken = (PhoneAuthenticationToken) authentication;
		UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

		if (userDetails == null) {

			throw new PhoneNotFoundException("手机号码不存在");

		} else if (!userDetails.isEnabled()) {

			throw new DisabledException("用户已被禁用");

		} else if (!userDetails.isAccountNonExpired()) {

			throw new AccountExpiredException("账号已过期");

		} else if (!userDetails.isAccountNonLocked()) {

			throw new LockedException("账号已被锁定");

		} else if (!userDetails.isCredentialsNonExpired()) {

			throw new LockedException("凭证已过期");
		}

		PhoneAuthenticationToken result = new PhoneAuthenticationToken(userDetails,
				userDetails.getAuthorities());

		result.setDetails(authenticationToken.getDetails());

		return result;
	}

	public boolean supports(Class<?> authentication) {
		return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
