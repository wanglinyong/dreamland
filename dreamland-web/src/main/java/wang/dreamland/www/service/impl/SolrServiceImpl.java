package wang.dreamland.www.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.dreamland.www.common.DateUtils;
import wang.dreamland.www.common.PageHelper;
import wang.dreamland.www.entity.UserContent;
import wang.dreamland.www.service.SolrService;

import java.io.IOException;
import wang.dreamland.www.common.PageHelper.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 12903 on 2018/6/23.
 */
@Service
public class SolrServiceImpl implements SolrService {
    @Autowired
    HttpSolrClient solrClient;
    @Override
    public Page<UserContent> findByKeyWords(String keyword, Integer pageNum, Integer pageSize) {
        SolrQuery solrQuery = new SolrQuery( );
        //设置查询条件
        solrQuery.setQuery( "title:"+keyword );
        //设置高亮
        solrQuery.setHighlight( true );
        solrQuery.addHighlightField( "title" );
        solrQuery.setHighlightSimplePre( "<span style='color:red'>" );
        solrQuery.setHighlightSimplePost( "</span>" );

        //分页
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 7;
        }
        solrQuery.setStart( (pageNum-1)*pageSize );
        solrQuery.setRows( pageSize );
        solrQuery.addSort("rpt_time", SolrQuery.ORDER.desc);
        //开始查询

        try {
            QueryResponse response = solrClient.query( solrQuery );
            //获得高亮数据集合
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //获得结果集
            SolrDocumentList resultList = response.getResults();
            //获得总数量
            long totalNum = resultList.getNumFound();
            List<UserContent> list = new ArrayList<UserContent>(  );
            for(SolrDocument solrDocument:resultList){
                //创建文章对象
                UserContent content = new UserContent();
                //文章id
                String id = (String) solrDocument.get( "id" );
                Object content1 = solrDocument.get( "content" );
                Object commentNum = solrDocument.get( "comment_num" );
                Object downvote = solrDocument.get( "downvote" );
                Object upvote = solrDocument.get( "upvote" );
                Object nickName = solrDocument.get( "nick_name" );
                Object imgUrl = solrDocument.get( "img_url" );
                Object uid = solrDocument.get( "u_id" );
                Object rpt_time = solrDocument.get( "rpt_time" );
                Object category = solrDocument.get( "category" );
                Object personal = solrDocument.get( "personal" );
                //取得高亮数据集合中的文章标题
                Map<String, List<String>> map = highlighting.get( id );
                String title = map.get( "title" ).get( 0 );

                content.setId( Long.parseLong( id ) );
                content.setCommentNum( Integer.parseInt( commentNum.toString() ) );
                content.setDownvote( Integer.parseInt( downvote.toString() ) );
                content.setUpvote( Integer.parseInt( upvote.toString() ) );
                content.setNickName( nickName.toString() );
                content.setImgUrl( imgUrl.toString() );
                content.setuId( Long.parseLong( uid.toString() ) );
                content.setTitle( title );
                content.setPersonal( personal.toString() );
                Date date = (Date)rpt_time;
                content.setRptTime(date);
                List<String> clist = (ArrayList)content1;
                content.setContent( clist.get(0).toString() );
                content.setCategory( category.toString() );
                list.add( content );
            }
            PageHelper.startPage(pageNum, pageSize);//开始分页
            PageHelper.Page page = PageHelper.endPage();//分页结束
            page.setResult(list);
            page.setTotal(totalNum);
            return page;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUserContent(UserContent cont) {
        if(cont!=null){
            addDocument(cont);
        }
    }

    @Override
    public void updateUserContent(UserContent cont) {
        if(cont!=null){
            addDocument(cont);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            solrClient.deleteById(id.toString());
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDocument(UserContent cont){
        try {
            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.addField( "comment_num", cont.getCommentNum() );
            inputDocument.addField( "downvote", cont.getDownvote() );
            inputDocument.addField( "upvote", cont.getUpvote() );
            inputDocument.addField( "nick_name", cont.getNickName());
            inputDocument.addField( "img_url", cont.getImgUrl() );
            inputDocument.addField( "rpt_time", cont.getRptTime() );
            inputDocument.addField( "content", cont.getContent() );
            inputDocument.addField( "category", cont.getCategory());
            inputDocument.addField( "title", cont.getTitle() );
            inputDocument.addField( "u_id", cont.getuId() );
            inputDocument.addField( "id", cont.getId());
            inputDocument.addField( "personal", cont.getPersonal());
            solrClient.add( inputDocument );
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
