package wang.dreamland.www.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.dreamland.www.common.Constants;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.entity.UserContent;
import wang.dreamland.www.service.SolrService;
import wang.dreamland.www.service.UserContentService;

import java.util.Date;

/**
 * Created by 12903 on 2018/5/20.
 */
@Controller
public class WriteController extends BaseController {
    private final static Logger log = Logger.getLogger(WriteController.class);
    @Autowired
    private UserContentService userContentService;

    @Autowired
    private SolrService solrService;

    /**
     * 进入writedream
     * @param model
     * @return
     */
    @RequestMapping("/writedream")
    public String writedream(Model model,@RequestParam(value = "cid",required = false) Long cid,
                             @RequestParam(value = "style",required = false) String style) {
        User user = getCurrentUser();
        if(cid!=null){
            UserContent content = userContentService.findById(cid);
            model.addAttribute("cont",content);
        }

        model.addAttribute("user", user);
        if(StringUtils.isNotBlank( style ) && Constants.WRITE_STYLE_MARKDOWN.equals( style )){
            return "write/writedream";
        }else {
            return "write/markdown";
        }
    }



    /**
     * 写文章
     * @param model
     * @param id
     * @param category
     * @param txtT_itle
     * @param content
     * @param private_dream
     * @return
     */
    @RequestMapping("/doWritedream")
    public String doWritedream(Model model, @RequestParam(value = "id",required = false) String id,
                               @RequestParam(value = "cid",required = false) Long cid,
                               @RequestParam(value = "category",required = false) String category,
                               @RequestParam(value = "txtT_itle",required = false) String txtT_itle,
                               @RequestParam(value = "editorhtml",required = false) String content,
                               @RequestParam(value = "editormd",required = false) String editormd,
                               @RequestParam(value = "private_dream",required = false) String private_dream) {
        log.info( "进入写梦Controller" );
        User user = getCurrentUser();
        UserContent userContent = new UserContent();
        if(cid!=null){
            userContent = userContentService.findById(cid);
        }
        userContent.setCategory( category );
        userContent.setContent( content );
        userContent.setEditormd( editormd );
        userContent.setRptTime( new Date(  ) );
        String imgUrl = user.getImgUrl();
        if(StringUtils.isBlank( imgUrl )){
            userContent.setImgUrl( "/images/icon_m.jpg" );
        }else {
            userContent.setImgUrl( imgUrl );
        }
        if("on".equals( private_dream )){
            userContent.setPersonal( "1" );
        }else{
            userContent.setPersonal( "0" );
        }
        userContent.setTitle( txtT_itle );
        userContent.setuId( user.getId() );
        userContent.setNickName( user.getNickName() );

        if(cid ==null){
            userContent.setUpvote( 0 );
            userContent.setDownvote( 0 );
            userContent.setCommentNum( 0 );
            userContentService.addContent( userContent );
            solrService.addUserContent(userContent);

        }else {
            userContentService.updateById(userContent);
            solrService.updateUserContent(userContent);
        }
        model.addAttribute("content",userContent);
        return "write/writesuccess";
    }

    /**
     * 根据文章id查看文章
     * @param model
     * @param cid
     * @return
     */
    @RequestMapping("/watch")
    public String watchContent(Model model, @RequestParam(value = "cid",required = false) Long cid){
        User user = getCurrentUser();
        UserContent userContent = userContentService.findById(cid);
        model.addAttribute("cont",userContent);
        model.addAttribute("user",user);
        return "personal/watch";
    }
}
