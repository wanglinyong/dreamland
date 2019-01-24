package wang.dreamland.www.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.dreamland.www.common.Constants;
import wang.dreamland.www.common.MD5Util;
import wang.dreamland.www.common.RandStringUtils;
import wang.dreamland.www.entity.OpenUser;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.OpenUserService;
import wang.dreamland.www.service.UserInfoService;
import wang.dreamland.www.service.UserService;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wly on 2018/4/21.
 */
@Controller
public class LoginController extends BaseController {
    private final static Logger log = Logger.getLogger( LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired// redis数据库操作模板
    private RedisTemplate<String, String> redisTemplate;// jdbcTemplate HibernateTemplate

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;// mq消息模板.

    @Autowired
    private OpenUserService openUserService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 点击qq登陆
     * @param model
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin(Model model) {
        HttpServletRequest request = getRequest();
        String url = "";
        try {
            url = new Oauth().getAuthorizeURL(request);
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return "redirect:"+url;
    }


    @RequestMapping("/login")
    public String login(Model model) {
        User user = getCurrentUser();
        if(user!=null){

            return  "redirect:/list";
        }
        return "../login";
    }

    // 匹对验证码的正确性
    public int checkValidateCode(String code) {
        Object vercode = getRequest().getSession().getAttribute("VERCODE_KEY");
        if (null == vercode) {
            return -1;
        }
        if (!code.equalsIgnoreCase(vercode.toString())) {
            return 0;
        }
        return 1;
    }

    /**
     * 发送手机验证码
     * @param model
     * @param telephone
     * @return
     */
    @RequestMapping("/sendSms")
    @ResponseBody
    public Map<String,Object> index(Model model, @RequestParam(value = "telephone",required = false) final String telephone ) {
        Map map = new HashMap<String,Object>(  );
        try { //  发送验证码操作
            final String code = RandStringUtils.getCode();
            redisTemplate.opsForValue().set(telephone, code, 60, TimeUnit.SECONDS);// 60秒 有效 redis保存验证码
            log.debug("--------短信验证码为："+code);
            // 调用ActiveMQ jmsTemplate，发送一条消息给MQ
            jmsTemplate.send("login_msg", new MessageCreator() {
                public Message createMessage(javax.jms.Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone",telephone);
                    mapMessage.setString("code", code);
                    return mapMessage;
                }
            });
        } catch (Exception e) {
            map.put( "msg",false );
        }
        map.put( "msg",true );
        return map;

    }

    /**
     * 退出登录
     * @param model
     * @return
     */
    @RequestMapping("/loginout")
    public String exit(Model model) {
        log.info( "退出登录" );
        getSession().removeAttribute( "user" );
        getSession().invalidate();
        return "../login";
    }


}
