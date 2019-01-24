package wang.dreamland.www.interceptor;
import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import tk.mybatis.mapper.entity.Example;
import wang.dreamland.www.common.PageHelper;
import wang.dreamland.www.controller.BaseController;
import wang.dreamland.www.dao.UserContentMapper;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.entity.UserContent;
import javax.servlet.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by wly on 2018/4/26.
 */
public class IndexJspFilter extends BaseController implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = getCurrentUser();
        request.setAttribute("user",user);
        System.out.println("===========自定义过滤器==========");
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        PageHelper.startPage(null, null);//开始分页
        List<UserContent> list = userContentMapper.findByJoin(null);
        PageHelper.Page endPage = PageHelper.endPage();//分页结束
        request.setAttribute("page", endPage );
        request.setAttribute("list", JSON.toJSONString( endPage.getResult() )  );
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
