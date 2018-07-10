package wang.dreamland.www.service;

import wang.dreamland.www.entity.OpenUser;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */
public interface OpenUserService {
	/**
	 * 根据openId查找OpenUser
	 * @param openId
	 * @return
	 */
	OpenUser findByOpenId(String openId);

	/**
	 * 根据id查询OpenUser
	 * @param id
	 * @return
	 */
	OpenUser findById(Long id);

	/**
	 * 根据用户id和第三方类型查询OpenUser
	 * @param uid
	 * @param type
	 * @return
	 */
	OpenUser findByUidAndType(Long uid, String type);

	/**
	 * 根据用户id查询OpenUser集合
	 * @param uid
	 * @return
	 */
	List<OpenUser> findByUid(Long uid);

	/**
	 * 添加OpenUser
	 * @param openUser
	 */
	void add(OpenUser openUser);

	/**
	 * 修改OpenUser
	 * @param openUser
	 */
	void update(OpenUser openUser);

	/**
	 * 根据openId删除OpenUser
	 * @param openId
	 */
	void deleteByOpenId(String openId);

	/**
	 * 根据id删除OpenUser
	 * @param openId
	 */
	void deleteById(Long openId);

	/**
	 * 根据用户id和第三方类型删除OpenUser
	 * @param uid
	 * @param type
	 */
	void deleteByUidAndType(Long uid,String type);
}
