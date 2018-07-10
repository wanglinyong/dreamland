package wang.dreamland.www.mail;

import javax.mail.PasswordAuthentication;

/**
 * Created by wly on 2018/3/7.
 */

public class MyAuthenticator extends javax.mail.Authenticator {
    private String strUser;
    private String strPwd;
    public MyAuthenticator(String user, String password) {
        this.strUser = user;
        this.strPwd = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUser, strPwd);
    }
}
