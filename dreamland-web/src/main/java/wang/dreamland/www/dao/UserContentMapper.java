package wang.dreamland.www.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import wang.dreamland.www.entity.Comment;
import wang.dreamland.www.entity.UserContent;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */
public interface UserContentMapper extends Mapper<UserContent> {
    /**
     * 根据用户id查询出梦分类
     * @param uid
     * @return
     */
    List<UserContent> findCategoryByUid(@Param("uid")long uid);

    /**
     *  插入文章并返回主键id 返回类型只是影响行数  id在UserContent对象中
     * @param userContent
     * @return
     */
    int inserContent(UserContent userContent);

    /**
     * user_content与user连接查询
     * @return
     */
    List<UserContent> findByJoin(UserContent userContent);
}
