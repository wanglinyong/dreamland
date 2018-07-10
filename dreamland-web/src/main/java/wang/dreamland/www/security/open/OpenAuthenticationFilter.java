package wang.dreamland.www.security.open;

import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import wang.dreamland.www.common.Constants;
import wang.dreamland.www.common.ValidateUtils;
import wang.dreamland.www.controller.BaseController;
import wang.dreamland.www.entity.OpenUser;
import wang.dreamland.www.entity.RoleUser;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.OpenUserService;
import wang.dreamland.www.service.RoleUserService;
import wang.dreamland.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by wly on 2018/7/3.
 */
public class OpenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private Logger log = LoggerFactory.getLogger( OpenAuthenticationFilter.class );
	@Autowired
	private OpenUserService openUserService;
	@Autowired
	private RoleUserService roleUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseController baseController;


	protected OpenAuthenticationFilter( ) {
		super( new AntPathRequestMatcher(Constants.QQ_LOGIN_URL) );
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

		OpenUser openUser = getOpenUser( request, response );
		log.info("openId==="+openUser.getOpenId()+"==="+openUser.getAccessToken());
		if(openUser.getOpenId()==null){
			throw new OpenUserNotFoundException("第三方用户OpenId为空");
		}
		OpenUser openUserDB = openUserService.findByOpenId( openUser.getOpenId());
		openUser.setLastLoginTime(new Date());
		if(openUserDB==null){
			User user = null;
			if(baseController.getCurrentUser()!=null){
				user = baseController.getCurrentUser();
			}else {
				user =new User();
				user.setNickName( openUser.getNickName() );
				user.setEmail( openUser.getOpenId() );
				user.setImgUrl( openUser.getAvatar() );
				userService.regist( user );
				addRoleUser( user.getId() );
			}
			openUser.setuId( user.getId() );
			openUserService.add( openUser );
		}else {
			//是否过期
			boolean accountNonExpired = ValidateUtils.isAccountNonExpired( new Date(), openUserDB.getLastLoginTime(), openUserDB.getExpiredTime() );
			if(!accountNonExpired){
				//过期
				openUser.setId( openUserDB.getId() );
				openUser.setuId(openUser.getuId());
				openUserService.update( openUser );
				openUserDB = openUser;
			}
			if(baseController.getCurrentUser()!=null){
				//绑定 判断是否有创建临时用户
				User user = userService.findById(openUserDB.getuId());
				if(user!=null && user.getPassword()==null){
					//删除临时用户
					roleUserService.deleteByUid(user.getId());
					userService.deleteByEmail(user.getEmail());
				}
				//绑定
				openUserDB.setuId(baseController.getCurrentUser().getId());
				openUserService.update(openUserDB);
			}
		}
		OpenAuthenticationToken authRequest = new OpenAuthenticationToken(openUser.getOpenId());
		this.setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, OpenAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void addRoleUser(Long uid){
		RoleUser roleUser = new RoleUser();
		roleUser.setuId( uid );
		roleUser.setrId( Constants.ROLE_USER );
		roleUserService.add( roleUser );
	}

	public OpenUser getOpenUser(HttpServletRequest request,HttpServletResponse response){
		OpenUser openUser = new OpenUser();
		response.setContentType("text/html; charset=utf-8");
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken   = null;
			String openID   = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				//用户取消了授权
				log.info("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();//失效时间
				// 利用获取到的accessToken 去获取当前用的openid
				OpenID openIDObj =  new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);//根据用户openId和accessToken获取用户的qq信息
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				if (userInfoBean.getRet() == 0) {
					openUser.setOpenType( Constants.OPEN_TYPE_QQ );
					openUser.setNickName( userInfoBean.getNickname() );
					openUser.setAvatar( userInfoBean.getAvatar().getAvatarURL50() );
					openUser.setOpenId( openID );
					openUser.setAccessToken( accessToken );
					openUser.setExpiredTime( tokenExpireIn );
				} else {
					log.info("未能正确获取QQ用户信息，原因是： " + userInfoBean.getMsg());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException( "获取QQ用户信息失败",e );
		}

		return openUser;
	}

}
