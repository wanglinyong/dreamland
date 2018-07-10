package wang.dreamland.www.security.open;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by wly on 2018/6/29.
 */
public class OpenUserNotFoundException extends AuthenticationException {
	public OpenUserNotFoundException(String msg, Throwable t) {
		super( msg, t );
	}

	public OpenUserNotFoundException(String msg) {
		super( msg );
	}
}
