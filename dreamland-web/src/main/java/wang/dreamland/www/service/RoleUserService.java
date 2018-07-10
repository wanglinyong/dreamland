package wang.dreamland.www.service;

import wang.dreamland.www.entity.RoleUser;
import wang.dreamland.www.entity.User;

import java.util.List;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface RoleUserService {
    /**
     * 根据用户查询角色用户集合
     * @param user
     * @return
     */
    List<RoleUser> findByUser(User user);

    /**
     * 添加用户角色中间对象
     * @param roleUser
     * @return id
     */
    int add(RoleUser roleUser);


    /**
     * 根据用户id删除
     * @param uid
     */
    void deleteByUid(Long uid);
}
