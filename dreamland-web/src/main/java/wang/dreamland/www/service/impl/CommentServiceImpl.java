package wang.dreamland.www.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import wang.dreamland.www.dao.CommentMapper;
import wang.dreamland.www.entity.Comment;
import wang.dreamland.www.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wly on 2017/12/15.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    public int add(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    public void update(Comment comment) {
        commentMapper.updateByPrimaryKey( comment );
    }

    public List<Comment> findAll(Long cid) {
        return commentMapper.selectAll(cid);
    }

    public Comment findById(Long id) {
        Comment comment = new Comment();
        comment.setId( id );
        return commentMapper.selectOne( comment );
    }

    public List<Comment> findAllFirstComment(Long cid)
    {
        return commentMapper.findAllFirstComment(cid);
    }

    public List<Comment> findAllChildrenComment(Long cid, String children) {
        return commentMapper.findAllChildrenComment(cid,children);
    }

    public void deleteById(Long id) {
        Comment c = new Comment();
        c.setId( id );
        commentMapper.deleteByPrimaryKey( c );

    }

    public void deleteChildrenComment(String children) {
        Example example = new Example( Comment.class );
        Example.Criteria criteria = example.createCriteria();
        List<Object> list = new ArrayList<Object>(  );
        String[] split = children.split( "," );
        for(int i = 0;i<split.length;i++){
            list.add( split[i] );
        }
        criteria.andIn( "id",list );
        commentMapper.deleteByExample(example);
    }

    @Override
    public void deleteByContentId(Long cid) {
        Comment comment = new Comment();
        comment.setConId(cid);
        commentMapper.delete(comment);
    }
}
