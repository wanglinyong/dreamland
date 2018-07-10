package wang.dreamland.www.security.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import wang.dreamland.www.entity.Role;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.RoleService;
import wang.dreamland.www.service.UserService;

import java.util.List;

/**
 * Created by 12903 on 2018/6/30.
 */
public class AccountDetailsService implements UserDetailsService{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<Role> roles = roleService.findByUid(user.getId());
        user.setRoles(roles);

        return user;
    }

}
