package wang.dreamland.www.security.phone;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by wly on 2018/6/29.
 */
public class PhoneNotFoundException extends AuthenticationException {
	public PhoneNotFoundException(String msg, Throwable t) {
		super( msg, t );
	}

	public PhoneNotFoundException(String msg) {
		super( msg );
	}
}
