package wang.dreamland.www.service;

import wang.dreamland.www.entity.Comment;

import java.util.List;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface CommentService {


    /**
     * 添加评论
     * @param comment
     * @return
     */
   int add(Comment comment);


    /**
     * 更新评论
     * @param comment
     */
   void update(Comment comment);

    /**
     * 根据文章id查询所有评论
     * @return
     */
  List<Comment> findAll(Long content_id);

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
  Comment findById(Long id);

    /**
     * 根据文章id查询所有父评论
     */
    List<Comment> findAllFirstComment(Long content_id);

    /**
     * 根据文章id和子评论ids查询所有子评论
     */
    List<Comment> findAllChildrenComment(Long content_id, String children);


    /**
     * 根据id删除评论
     * @param id
     */
    void deleteById(Long id);

    /**
     * 批量删除子评论
     * @param children
     */
    void deleteChildrenComment(String children);
}
