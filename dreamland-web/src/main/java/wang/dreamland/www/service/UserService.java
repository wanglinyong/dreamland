package wang.dreamland.www.service;

import wang.dreamland.www.entity.User;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    int regist(User user);

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    User login(String email,String password);

    /**
     * 根据用户邮箱查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据用户手机号查询用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据用户邮箱删除用户
     * @param email
     */
    void deleteByEmail(String email);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);
}
