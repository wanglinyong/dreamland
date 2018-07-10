package wang.dreamland.www.service;

import wang.dreamland.www.entity.RoleResource;

import java.util.List;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface RoleResourceService {
    /**
     * 添加roleRource
     * @param roleResource
     */
    void add(RoleResource roleResource);

    /**
     * 根据id查询RoleResource
     * @param id
     * @return
     */
    RoleResource findById(Long id);

    /**
     * 根据角色id查询角色资源集合
     * @param rid
     * @return
     */
    List<RoleResource> findByRoleId(Long rid);

    /**
     * 根据id删除RoleResource
     * @param id
     */
    void deleteById(Long id);


}
