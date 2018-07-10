package wang.dreamland.www.security.account;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wang.dreamland.www.common.CodeValidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 12903 on 2018/6/30.
 */
public class AccountAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String codeParameter = "code";
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        String code = this.obtainCode(request);
        String caChecode = (String)request.getSession().getAttribute("VERCODE_KEY");
        boolean flag = CodeValidate.validateCode(code,caChecode);
        if(!flag){
            throw new UsernameNotFoundException("验证码错误");
        }
        if(username == null) {
            username = "";
        }

        if(password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }
}
