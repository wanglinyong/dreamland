package wang.dreamland.www.security.open;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by wly on 2018/6/29.
 */
public class OpenAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		OpenAuthenticationToken authenticationToken = (OpenAuthenticationToken) authentication;
		UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

		if (userDetails == null) {

			throw new OpenUserNotFoundException("第三方OpenId不存在");

		} else if (!userDetails.isEnabled()) {

			throw new DisabledException("第三方用户已被禁用");

		} else if (!userDetails.isAccountNonExpired()) {

			throw new AccountExpiredException("第三方账号已过期");

		} else if (!userDetails.isAccountNonLocked()) {

			throw new LockedException("第三方账号已被锁定");

		} else if (!userDetails.isCredentialsNonExpired()) {

			throw new LockedException("第三方凭证已过期");
		}

		OpenAuthenticationToken result = new OpenAuthenticationToken(userDetails,
				userDetails.getAuthorities());

		result.setDetails(authenticationToken.getDetails());

		return result;
	}

	public boolean supports(Class<?> authentication) {
		return OpenAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
