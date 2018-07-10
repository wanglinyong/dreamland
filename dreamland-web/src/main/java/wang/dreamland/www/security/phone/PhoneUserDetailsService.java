package wang.dreamland.www.security.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import wang.dreamland.www.entity.Role;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.RoleService;
import wang.dreamland.www.service.UserService;

import java.util.List;

/**
 * Created by wly on 2018/6/29.
 */
public class PhoneUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public UserDetails loadUserByUsername(String phone) throws PhoneNotFoundException {
		User user = userService.findByPhone(phone);
		if(user == null){
			throw new PhoneNotFoundException("手机号码错误");
		}
		List<Role> roles = roleService.findByUid(user.getId());
		user.setRoles(roles);
		return user;
	}
}
