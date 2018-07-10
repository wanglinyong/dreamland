package wang.dreamland.www.security.account;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 12903 on 2018/6/30.
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private String errorPage;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (isAjax) {
            String jsonObject = "{\"message\":\"Access is denied！\",\"access-denied\":true}";
            String contentType = "application/json";
            response.setContentType(contentType);
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
            return;
        } else {
            if (!response.isCommitted()) {
                if (this.errorPage != null) {
                    request.setAttribute("SPRING_SECURITY_403_EXCEPTION", e);
                    response.setStatus(403);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPage);
                    dispatcher.forward(request, response);

                } else {
                    response.sendError(403, e.getMessage());
                }
            }
        }
    }

    public void setErrorPage(String errorPage) {
        if(errorPage != null && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        } else {
            this.errorPage = errorPage;
        }
    }
}
