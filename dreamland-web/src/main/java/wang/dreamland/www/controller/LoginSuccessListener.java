package wang.dreamland.www.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import wang.dreamland.www.entity.LoginLog;
import wang.dreamland.www.entity.OpenUser;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.LoginLogService;

import java.util.Date;
/**
 * Created by wly on 2018/2/3.
 */
@Component
public class LoginSuccessListener implements ApplicationListener {
	
	private final static Logger log = Logger.getLogger(LoginSuccessListener.class);

	@Autowired
	private LoginLogService loginLogService;

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
            WebAuthenticationDetails webDetail = (WebAuthenticationDetails) authEvent.getAuthentication().getDetails();
            Object principal = authEvent.getAuthentication().getPrincipal();
            User user = null;
            if(principal instanceof OpenUser){
                user = ((OpenUser) principal).getUser();
            }else {
                user = (User)principal;
            }
            LoginLog loginLog = new LoginLog();
            loginLog.setIp(webDetail.getRemoteAddress());
            loginLog.setCreateTime(new Date());
            loginLog.setuId(user.getId());
            loginLogService.add(loginLog);
            log.info(user.getNickName()+",IP:"+webDetail.getRemoteAddress()+" 登录成功");
        }
	}

}
