<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>梦境网</title>



    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>

    <link href="${ctx}/css/zui/css/zui2.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.css" rel="stylesheet"/>




    <link rel="stylesheet" href="${ctx}/css/reply/css/style.css">
    <link rel="stylesheet" href="${ctx}/css/reply/css/comment2.css">

    <link rel="stylesheet" href="${ctx}/editormd/css/editormd.preview2.css" />
    <link rel="stylesheet" href="${ctx}/editormd/css/editormd3.css" />



    <style>
        body,html{
            background-color: #EBEBEB;
            padding: 0;
            margin: 0;
            height:100%;
        }

        .xxzui .kwd, .xxzui .prettyprint .tag{
            color: #ff9900;
        }
        .stats a{
            text-decoration:none;
        }
        .stats-buttons  a{
            text-decoration:none;
        }
        .commentAll a{
            text-decoration:none;
        }

        .comment-show a{
            text-decoration:none;
        }
        a {
            color: inherit;
            outline: 0;
        }
        a:-webkit-any-link {
            cursor: pointer;

        }
        .container{
            min-height:100%;
            position: relative;

        }
        #content{
            min-height:100%;
            position: relative;
        }
        .col-md-9{
            padding-bottom: 80px;
        }
        .content-text{
            padding: 20px;

        }
        .single-share{
            float: right;
        }
        .stats-buttons {
            float: left;
        }
        .foot-nav-col li{
            list-style: none;
            margin-left: 100px;
        }
        .foot-nav-col h3{
            margin-left:120px;
        }
        .foot-nav-col a{
            text-decoration:none;
            color:grey;

        }
        .foot-nav-col a:link,a:visited { color:grey;}
        .foot-nav-col a:hover,a:active { color: #6318ff;}

        .foot-nav-col{
            margin-top: 20px;
            float: left;
        }

        .author img {
            width: 35px;
            height: 35px;
            border-radius: 35px;
            padding: 0;
            margin-right: 10px;
        }
        fieldset, img {
            border: 0;
        }
        .author a, .author span {
            float: left;
            font-size: 14px;
            font-weight: 700;
            line-height: 35px;
            color: #9b8878;
            text-decoration: none;
        }

        .author-h2 {
            display: block;
            font-size: 1.5em;
            -webkit-margin-before: 0.83em;
            -webkit-margin-after: 0.83em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
            font-weight: bold;
            font-size: 100%;
            font-weight: 400;
        }

        .commentAll{
            margin-left: 5px;
        }

        .header-user-img img{
            width: 25px;
            height: 25px;
            border-radius: 25px;
            padding: 0;
            margin-right: 10px;
        }
        .navbar navbar-inverse{
            height: 50px;
        }


    </style>
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?802ba774223c589fedeef7af66b41964";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<div class="container xxzui xxzuitheme">
    <div>
        <h1>Dreamland-梦境网</h1>
    </div>
    <div class="header-img" style="position: absolute;margin-left: 920px;margin-top: -40px;">
        <c:if test="${empty user}">
            <a href="to_login"><img src="${ctx}/images/Connect_logo_7.png"></a>
            &nbsp;&nbsp;
            <a name="tj_login" class="lb" href="/login" style="color: black">[登录]</a>
            &nbsp;&nbsp;
            <a name="tj_login" class="lb" href="register" style="color: black">[注册]</a>
        </c:if>
        <c:if test="${not empty user}">
            <div class="header-user-img">
                <a name="tj_loginp"   class="lb" onclick="personal('${user.id}');" style="color: black"><img style="width: 25px;width: 25px" src="${user.imgUrl}" /> <font color="#9370db">${user.nickName}, 欢迎您！</font></a>
                &nbsp;&nbsp;
                <a name="tj_login" class="lb" href="${ctx}/loginout" style="color: black">[退出]</a>
            </div>

        </c:if>

    </div>



    <nav class="navbar navbar-inverse">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/index_list">首页</a>
        </div>
        <div id="navbar-menu" class="collapse navbar-collapse">
            <ul class="nav navbar-nav" ><%--#ddd--%>
                <li class="active"><a href="#">最新梦</a></li>
                <li><a style="color: white" href="#">最热梦</a></li>
                <li><a style="color: white" href="#">梦诗词</a></li>
                <li><a style="color: white" href="#">梦问答</a></li>
                <li><a style="color: white" href="#">我的梦</a></li>
                <li><a style="color: white" href="${ctx}/list?id=${user.id}">个人空间</a></li>
            </ul>
        </div>

        <form method="post" action="${ctx}/index_list"  id="indexSearchForm"  class="navbar-form navbar-right" role="search" style="margin-top: -35px;margin-right: 10px">
            <div class="form-group">
                <input type="text" id="keyword" name="keyword" value="${keyword}" class="form-control" placeholder="搜索">
            </div>
            &nbsp; &nbsp;<i onclick="searchForm();" class="icon icon-search" style="color: white"></i>
        </form>

    </nav>

    <div id="content" class="row-fluid">
        <div class="col-md-9"  style="background-color: white;">
            <div id="content_col" class="content-main xxlib">

                <c:forEach var="cont" items="${page.result}" varStatus="i">
                    <!-- 正文开始 -->

                    <div class="content-text">

                        <div class="author clearfix">
                            <div>
                                <a href="#" target="_blank" rel="nofollow" style="height: 35px">
                                     <img src="${cont.imgUrl}">
                                </a>
                            </div>

                                <div class="author-h2">
                                    <div style="float: left;font-size: 15px;color: #9b8878">
                                        ${cont.nickName}
                                    </div>
                                    <div style="float: left;margin-left: 10px;color: grey;margin-top: 2px;font-size: 12px">
                                         ${cont.formatDate}
                                    </div>
                                </div>

                        </div>


                        <h2>${cont.title}</h2>
                        <div style="padding: 0px; float: none;margin-bottom: 10px;" class="xxlib content editormd-preview-theme-dark" id="content-text_${cont.id}">${cont.content}</div>
                        <div style="height: 5px"></div>
                        <div class="stats">
                            <!-- 笑脸、评论数等 -->
                            <span class="stats-vote"><i id="${cont.id}" class="number">${cont.upvote}</i> 赞</span>
                            <span class="stats-comments">
                    <span class="dash"> · </span>
                         <a  onclick="reply(${cont.id},${cont.uId});">
                              <i class="number" id="comment_num_${cont.id}">${cont.commentNum}</i> 评论
                          </a>
                    </span>
                        </div>
                        <div style="height: 5px"></div>
                        <div class="stats-buttons bar clearfix">
                            <a style="cursor: pointer;" onclick="upvote_click(${cont.id},1);">
                                <i class="icon icon-thumbs-o-up icon-2x"></i>
                                <span class="number hidden" id="up_${cont.id}">${cont.upvote}</span>
                            </a>
                            &nbsp;
                            <a style="cursor: pointer;" onclick="upvote_click(${cont.id},-1);">
                                <i class="icon icon-thumbs-o-down icon-2x"></i>
                                <span class="number hidden" id="down_${cont.id}">${cont.downvote}</span>
                            </a>
                            &nbsp;
                            <a style="cursor: pointer;" onclick="reply(${cont.id},${cont.uId});" title="点击打开或关闭">
                                <i class="icon icon-comment-alt icon-2x"></i>
                            </a>
                        </div>
                        <div class="single-share">
                            <a class="share-wechat" data-type="wechat" title="分享到微信" rel="nofollow" style="margin-left:18px;color: grey;cursor: pointer; text-decoration:none;">
                                <i class="icon icon-wechat icon-2x"></i>
                            </a>
                            <a class="share-qq" data-type="qq" title="分享到QQ" rel="nofollow" style="margin-left:18px;color: grey;cursor: pointer; text-decoration:none;">
                                <i class="icon icon-qq icon-2x"></i>
                            </a>
                            <a  class="share-weibo" data-type="weibo" title="分享到微博" rel="nofollow" style="margin-left:18px;color: grey;cursor: pointer; text-decoration:none;">
                                <i class="icon icon-weibo icon-2x"></i>
                            </a>
                        </div>
                        <br/>
                        &nbsp;
                        <div class="commentAll" style="display:none" id="comment_reply_${cont.id}">
                            <!--评论区域 begin-->
                            <div class="reviewArea clearfix">
                                <textarea style="padding: 10px 15px 10px 15px;" id="comment_input_${cont.id}"  class="content comment-input" placeholder="输入内容&hellip;" onkeyup="keyUP(this)"></textarea>
                                <a class="plBtn" id="comment_${cont.id}" onclick="_comment(${cont.id},${user.id==null?0:user.id},${cont.uId})" style="background: #339b53;color: white;cursor: pointer;">评论</a>
                            </div>
                            <!--评论区域 end-->
                            <div class="comment-show-first" id="comment-show-${cont.id}">

                            </div>

                        </div>

                        <div class="single-clear">

                        </div>
                    </div>
                    <!-- 正文结束 -->
                    <div style="position: absolute;width:900px;background-color: #EBEBEB;height: 10px;left: 0px">
                    </div>
                </c:forEach>

            </div>





            <div id="page-info" style="position: absolute;width:900px;background-color: #EBEBEB;height: 80px;left: 0px;">
                <ul class="pager pager-loose">
                    <c:if test="${page.pageNum <= 1}">
                        <li><a href="javascript:void(0);">« 上一页</a></li>
                    </c:if>
                    <c:if test="${page.pageNum > 1}">
                        <li class="previous"><a href="${ctx}/index_list?pageNum=${page.pageNum-1}&&keyword=${keyword}">« 上一页</a></li>
                    </c:if>
                    <c:forEach begin="${page.startPage}" end="${page.endPage}" var="pn">
                        <c:if test="${page.pageNum==pn}">
                            <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                        </c:if>
                        <c:if test="${page.pageNum!=pn}">
                            <li ><a href="${ctx}/index_list?pageNum=${pn}&&keyword=${keyword}">${pn}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${page.pageNum>=page.pages}">
                        <li><a href="javascript:void(0);">下一页 »</a></li>
                    </c:if>
                    <c:if test="${page.pageNum<page.pages}">
                        <li><a href="${ctx}/index_list?pageNum=${page.pageNum+1}&&keyword=${keyword}">下一页 »</a></li>
                    </c:if>

                </ul>
            </div>



            <div class="foot" style="position: absolute;left: 0px;float: left;margin-top: 60px;">
                <div class="foot-nav clearfix">
                    <div class="foot-nav-col">
                        <h3>
                            关于
                        </h3>
                        <ul>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    关于梦境
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    加入我们
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    联系方式
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="foot-nav-col">
                        <h3>
                            帮助
                        </h3>
                        <ul>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    在线反馈
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    用户协议
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="nofollow">
                                    隐私政策
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="foot-nav-col">
                        <h3>
                            下载
                        </h3>
                        <ul>
                            <li>
                                <a href="#"
                                   target="_blank" rel="external nofollow">
                                    Android 客户端
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="external nofollow">
                                    iPhone 客户端
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="foot-nav-col">
                        <h3>
                            关注
                        </h3>
                        <ul>
                            <li>
                                <a href="http://www.dreamland.wang" onMouseOut="hideImg()"  onmouseover="showImg()">
                                    微信
                                    <div id="wxImg" style="display:none;height:50px;back-ground:#f00;position:absolute;">
                                        <img src="images/dreamland.png"/><br/>
                                        手机扫描二维码关注
                                    </div>
                                    <!-- <div class="foot-wechat-tips">
                                        &lt;!&ndash; <span class="foot-wechat-icon"></span>&ndash;&gt;
                                         <span class=" icon icon-wechat icon-2x"></span>
                                         手机扫描二维码关注
                                     </div>-->
                                </a>
                            </li>
                            <li>
                                <a href="#" target="_blank" rel="external nofollow">
                                    新浪微博
                                </a>
                            </li>
                            <li>
                                <a href="http://www.dreamland.wang" target="_blank" rel="external nofollow">
                                    QQ空间
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
               <!-- rgba(60,63,65,0.31)-->
                <hr style="position: absolute;background-color: rgba(161,171,175,0.31);width: 1000px;height: 1px;left: 0px"/>
                <hr style="position: absolute;background-color: rgba(161,171,175,0.31);width: 1000px;height: 1px;left: 0px"/>
                <div class="foot-nav clearfix" style="position: absolute;left: 0px;margin-top: 40px;text-align: center">
                    <div class="foot-copyrights" style="margin-left: 200px">
                        <p>
                            互联网ICP备案：皖ICP备18007469号
                        </p>
                        <p>
                            <span>违法和不良信息举报电话：010-xxxxxxx</span>
                            <span>邮箱：dreamland_wang@163.com</span>
                        </p>
                        <p style="margin-top: 8px">&copy;www.dreamland.wang 梦境网版权所有</p>
                    </div>
                </div>
            </div>
            <!--col-md-9结束 -->
        </div>


        <div class="col-md-3" style="position:absolute;top:0px;left: 880px;width: 268px;">
            <div style="background-color: white;width: 250px;height: 440px">
                <iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=82" width="250" height="440" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </div>

            <div style="margin-top: 20px;" onclick="goBook();">
                <img src="images/drk.jpg">
            </div>

            <div style="margin-top: 20px;background-image: url('${ctx}/images/java.jpg');cursor:pointer;" onclick="goBook();">
                <div class="ssm" style="width: 40px;height: 20px;border:1px solid #000;text-align: center">广告</div>
                <div style="padding: 1px">
                    <h3>SSM 博客系统开发实战</h3>
                    <ul style="list-style-type: none">
                        <li>
                            SSM 博客系统开发实战
                        </li>
                        <li> 王林永·Java 高级工程师，CSDN博客专栏.</li>
                        <li> ¥29.99 | 20 课</li>
                        <div class="ssm" style="float: left;width: 40px;height: 20px;border:1px solid #000;text-align: center;color: red">最新</div>
                        <div class="ssm" style="float: left;width: 40px;height: 20px;border:1px solid #000;margin-left: 5px;text-align: center;color: red">架构</div>
                        <div class="ssm" style="float: left;width: 40px;height: 20px;border:1px solid #000;margin-left: 5px;text-align: center;color: red">SSM</div>
                        <div class="ssm" style="float: left;width: 40px;height: 20px;border:1px solid #000;margin-left: 5px;text-align: center;color: red">Java</div>
                    </ul>

                </div>
                <br/>
                <div style="font-size: 10px;padding: 2px;margin-top: 2px">
                    <ul style="list-style-type: none">
                        <li>导读：为什么选择 SSM 框架开发项目</li>
                        <li>第01课：基础环境安装及Maven创建父子工程</li>
                        <li>第02课：SSM 框架的搭建</li>
                        <li>第03课：MySQL表设计及反向生成实体类</li>
                        <li>第04课：接口设计及通用 Mapper</li>
                        <li>第05课：注册（邮件激活、Ajax 异步获取）</li>
                        <li>第06课：登录之账号登录（验证码）</li>
                        <li>第07课：登录之手机快捷登录</li>
                        <li>第08课：首页展示及分页（PageHelper）</li>
                        <li>第09课：评论、回复及点赞模块</li>
                        <li>第10课：个人主页模块</li>
                        <li>第11课：博客书写页面——KindEditor </li>
                        <li>第12课：个人资料修改页面</li>
                        <li>第13课：第三方 QQ 登录及账号绑定与解除</li>
                        <li>第14课：首页搜索功能（Solr）</li>
                        <li>第15课：Spring-Security 源码解读及认证授权</li>
                        <li>第16课：Spring-Security 之手机登录认证授权</li>
                        <li>第17课：Spring-Security 之QQ登录认证授权</li>
                        <li>第18课：Linux 系统部署发布</li>
                        <li>第19课：项目总结</li>

                    </ul>
                </div>

            </div>
        </div>

    </div>


</div>
<%--<script src="${ctx}/editormd/examples/js/jquery.min.js"></script>--%>


<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>

<script type="text/javascript" src="${ctx}/css/reply/js/jquery.flexText.js"></script>

<script src="${ctx}/editormd/lib/marked.min.js"></script>
<script src="${ctx}/editormd/lib/prettify.min.js"></script>
<script src="${ctx}/editormd/lib/editormd.min.js"></script>
</body>
<script language=javascript>
    $(function () {
        var clist = ${list};
        $(clist).each(function () {
            var id = this.id
            editormd.markdownToHTML("content-text_"+id);
        })
       /* if(clist!=''){
            var second = (new Function('return '+ clist +';'))();
            console.log(second);
            for(var p in second){
                editormd.markdownToHTML("content-text"+p.id);
            }


        }*/

    });
    function goBook() {
        window.open( "https://gitbook.cn/gitchat/column/5afa86a515da5a21f341cd7f");
    }
    function  showImg(){
        document.getElementById("wxImg").style.display='block';
    }
    function hideImg(){
        document.getElementById("wxImg").style.display='none';
    }
    function personal(uId) {
        this.location =  "${ctx}/list?id="+uId;
    }

    /**
     * 日期函数
     * @param strTime
     * @returns {string}
     * @constructor
     */
    function FormatDate (strTime) {
        var date = new Date(strTime);
        var h=date.getHours();       //获取当前小时数(0-23)
        var m=date.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=date.getSeconds();
        if(s<10) s = '0' + s;
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+h+':'+m+":"+s;
    }
    //点赞或踩
    function upvote_click(id,cont) {
        var uid = "${user.id}";
        if(uid==''||uid==null){
            window.location.href = "/login.jsp";
            return
        }
        $.ajax({
            type:'post',
            url:'/upvote',
            data: {"id":id,"uid":'${user.id}',"upvote":cont},
            dataType:'json',
            success:function(data){
                var result = data["access-denied"]
                if(result){
                    var noAccess = data["access-denied"]
                    if(noAccess){
                        new $.zui.Messager('无访问权限！', {
                            icon: 'bell', // 定义消息图标
                            type:'danger'
                        }).show()
                        return
                    }
                }
                var up =  data["data"];
                /*alert(up);*/
                if(up == "success"){
                    if (cont == -1){
                        var down = document.getElementById("down_"+id);
                        var num = down.innerHTML;
                        var value = parseInt(num) + cont;
                        down.innerHTML = value;
                    } else {
                        var num = document.getElementById(id).innerHTML;
                        var value = parseInt(num) + cont;
                        document.getElementById(id).innerHTML = value;
                        document.getElementById("up_"+id).innerHTML = value;
                    }
                }else if(up == "done"){
                        new $.zui.Messager('今日已赞！', {
                            icon: 'bell' // 定义消息图标
                        }).show()
                }else if(up == "down"){
                    new $.zui.Messager('今日已踩！', {
                        icon: 'bell' // 定义消息图标
                    }).show()
                } else {
                    window.location.href = "/login.jsp";
                }
            }
        });
    }

    //点击评论或者回复图标
    function reply(id,uid) {
        $("div").remove("#comment_reply_"+id+" .comment-show");
        $("div").remove("#comment_reply_"+id+" .comment-show-con");
        if(showdiv_display = document.getElementById('comment_reply_'+id).style.display=='none'){//如果show是隐藏的

            document.getElementById('comment_reply_'+id).style.display='block';//show的display属性设置为block（显示）
            $.ajax({
                type:'post',
                url:'/reply',
                data: {"content_id":id},
                dataType:'json',
                success:function(data){
                    var list =  data["list"];

                    var okHtml;
                    if(list!=null && list!=""){
                        $(list).each(function () {
                            var chtml = "<div class='comment-show'>"+
                                "<div class='comment-show-con clearfix'>"+
                                "<div class='comment-show-con-img pull-left'><img src='"+this.user.imgUrl+"' alt=''></div>"+
                                "<div class='comment-show-con-list pull-left clearfix'>"+
                                "<div class='pl-text clearfix'>"+
                                "<a  class='comment-size-name'>"+this.user.nickName+" :</a>"+
                                "<span class='my-pl-con'>&nbsp;"+this.comContent+"</span>"+
                                "</div> <div class='date-dz'><span class='date-dz-left pull-left comment-time'>"+FormatDate(this.commTime)+"</span>"+
                                "<div class='date-dz-right pull-right comment-pl-block'>"+
                                "<a onclick='deleteComment("+id+","+uid+","+this.id+",null)' id='comment_dl_"+this.id+"' style='cursor:pointer' class='removeBlock'>删除</a>"+
                                "<a style='cursor:pointer' onclick='comment_hf("+id+","+this.id+","+null+","+this.user.id+","+uid+")' id='comment_hf_"+this.id+"' class='date-dz-pl pl-hf hf-con-block pull-left'>回复</a>"+
                                "<span class='pull-left date-dz-line'>|</span>"+
                                "<a onclick='reply_up("+this.id+")' style='cursor:pointer' class='date-dz-z pull-left' id='change_color_"+this.id+"'><i class='date-dz-z-click-red'></i>赞 (<i class='z-num' id='comment_upvote_"+this.id+"'>"+this.upvote+"</i>)</a>"+
                                "</div> </div> <div class='hf-list-con' id='hf-list-con-"+this.id+"'>";


                            var ehtml =   "</div> </div> </div></div>";
                            var parentComm_id = this.id;

                            okHtml = chtml;
                            //alert(this.children)
                            if(this.children != null && this.children != ''){
                                var commentList = this.comList;
                                $(commentList).each(function () {
                                    // alert(this.id);
                                    var oHtml = "<div class='all-pl-con'><div class='pl-text hfpl-text clearfix'>"+
                                        "<a class='comment-size-name'>"+this.user.nickName+"<a class='atName'>@"+this.byUser.nickName+" :</a> </a>"+
                                        "<span class='my-pl-con'>"+this.comContent+"</span>"+
                                        "</div><div class='date-dz'> <span class='date-dz-left pull-left comment-time'>"+FormatDate(this.commTime)+"</span>"+
                                        "<div class='date-dz-right pull-right comment-pl-block'>"+
                                        "<a style='cursor:pointer' onclick='deleteComment("+id+","+uid+","+this.id+","+parentComm_id+")' id='comment_dl_"+this.id+"' class='removeBlock'>删除</a>"+
                                        "<a onclick='comment_hf("+id+","+this.id+","+parentComm_id+","+this.user.id+","+uid+")' id='comment_hf_"+this.id+"' style='cursor:pointer' class='date-dz-pl pl-hf hf-con-block pull-left'>回复</a> <span class='pull-left date-dz-line'>|</span>"+
                                        "<a onclick='reply_up("+this.id+")' id='change_color_"+this.id+"' style='cursor:pointer' class='date-dz-z pull-left'><i class='date-dz-z-click-red'></i>赞 (<i class='z-num' id='comment_upvote_"+this.id+"'>"+this.upvote+"</i>)</a>"+
                                        "</div></div> </div>";

                                    okHtml = okHtml + oHtml;
                                });


                            }

                            okHtml = okHtml+ehtml;
                            $("#comment-show-" + id).append(okHtml);

                        });
                    }

                }
            });


        }else{//如果show是显示的

            document.getElementById('comment_reply_'+id).style.display='none';//show的display属性设置为none（隐藏）

        }
    }

    //限制字数
    function keyUP(t){
        var len = $(t).val().length;
        if(len > 139){
            $(t).val($(t).val().substring(0,140));
        }
    }

    //点击评论按钮
    function _comment(content_id,uid,cuid) {
        if(uid==0||uid==null){
            new $.zui.Messager('请登录后评论！', {
                icon: 'bell', // 定义消息图标
            }).show()
            return
        }
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        var oSize = $("#comment_input_"+content_id).val();
        console.log(oSize);
        //动态创建评论模块

        if(oSize.replace(/(^\s*)|(\s*$)/g, "") != ''){


            $.ajax({
                type:'post',
                url:'/comment',
                data: {"content_id":content_id,"uid":uid,"oSize":oSize,"comment_time":now},
                dataType:'json',
                success:function(data){
                    var comm_data =  data["data"];
                    //alert(comm_data);
                    if(comm_data=="fail"){
                        window.location.href = "/login.jsp";
                    }else {
                        var id = comm_data.id;
                        //alert(id)
                        oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="${user.imgUrl}" alt=""></div> <div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a  class="comment-size-name">${user.nickName} : </a> <span class="my-pl-con">&nbsp;'+ oSize +'</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"><a style="cursor:pointer"  onclick="deleteComment('+content_id+','+cuid+','+id+','+null+')" id="comment_dl_'+id+'"  class="removeBlock">删除</a> <a style="cursor:pointer" onclick="comment_hf('+content_id+','+id+','+null+','+comm_data.user.id+','+cuid+')" id="comment_hf_'+id+'" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a onclick="reply_up('+id+')" id="change_color_'+id+'" style="cursor:pointer"  class="date-dz-z pull-left" ><i class="date-dz-z-click-red"></i>赞 (<i class="z-num" id="comment_upvote_'+id+'">0</i>)</a> </div> </div><div class="hf-list-con"></div></div> </div>';
                        $("#comment_"+content_id).parents('.reviewArea ').siblings('.comment-show-first').prepend(oHtml);
                        $("#comment_"+content_id).siblings('.flex-text-wrap').find('.comment-input').prop('value','').siblings('pre').find('span').text('');

                        $("#comment_input_"+content_id).val('');

                        var num = document.getElementById("comment_num_"+content_id).innerHTML;
                        document.getElementById("comment_num_"+content_id).innerHTML = (parseInt(num) + 1)+"";
                    }
                }
            });
        }

    }

    //删除评论块
    function deleteComment(con_id,uid,id,fid) {
        // alert(uid)
        if('${user.id}'==uid){

            if (!confirm("确认要删除？")) {
                window.event.returnValue = false;
            }else{

                //发送ajax请求
                $.ajax({
                    type:'post',
                    url:'/deleteComment',
                    data: {"id":id,"uid":uid,"con_id":con_id,"fid":fid},
                    dataType:'json',
                    success:function(data){
                        var comm_data =  data["data"];
                        //alert(comm_data);
                        if(comm_data=="fail"){
                            window.location.href = "/login.jsp";
                        }else if(comm_data=="no-access"){
                            //alert("没有权限！");
                        }else {
                            //alert(comm_data)
                            var oThis = $("#comment_dl_"+id);
                            var oT = oThis.parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
                            if(oT.siblings('.all-pl-con').length >= 1){
                                oT.remove();
                            }else {
                                oThis.parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display','none')
                                oT.remove();
                            }
                            oThis.parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();


                            //评论数comment_num_con_id
                            document.getElementById("comment_num_"+con_id).innerHTML = parseInt(comm_data)+"";

                        }
                    }
                });
            }
        }
    }

    //一级评论  点击回复创建回复块
    function comment_hf(content_id,comment_id,fid,by_id,cuid) {
        // alert(cuid)
        //获取回复人的名字
        var oThis = $("#comment_hf_"+comment_id);
        var fhName = oThis.parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        //回复@
        var fhN = '回复@' + fhName;
        var fhHtml = '<div class="hf-con pull-left"> <textarea id="plcaceholder_'+comment_id+'"  class="content comment-input " placeholder="'+fhN+'" onkeyup="keyUP(this)"></textarea> <a id="comment_pl_'+comment_id+'" onclick="comment_pl('+content_id+','+comment_id+','+fid+','+by_id+','+cuid+')" class="hf-pl" style="color: white;cursor:pointer">评论</a></div>';
        //显示回复
        if (oThis.is('.hf-con-block')) {
            oThis.parents('.date-dz-right').parents('.date-dz').append(fhHtml);
            oThis.removeClass('hf-con-block');
            $('.content').flexText();
            oThis.parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding', '6px 15px');

            //input框自动聚焦
            oThis.parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus().val(fhN);
        } else {
            oThis.addClass('hf-con-block');
            oThis.parents('.date-dz-right').siblings('.hf-con').remove();
        }
    }

    //点赞
    function reply_up(id) {
        var num = document.getElementById("comment_upvote_"+id).innerHTML;
        if($("#change_color_"+id).is('.date-dz-z-click')){
            num--;
            $("#change_color_"+id).removeClass('date-dz-z-click red');
            $("#change_color_"+id).find('.z-num').html(num);
            $("#change_color_"+id).find('.date-dz-z-click-red').removeClass('red');

        }else {
            num++;
            $("#change_color_"+id).addClass('date-dz-z-click');
            $("#change_color_"+id).find('.z-num').html(num);
            $("#change_color_"+id).find('.date-dz-z-click-red').addClass('red');
        }

        $.ajax({
            type:'post',
            url:'/comment',
            data: {"id":id,"upvote":num},
            dataType:'json',
            success:function(data){
                var comm_data =  data["data"];
                if(comm_data=="fail"){
                    window.location.href = "/login.jsp";
                }
            }
        });
    }

    //点击一级评论块的评论按钮
    function comment_pl(content_id,comment_id,fid,by_id,cuid) {
        var uid = "${user.id}";
        if(uid==''||uid==null){
            new $.zui.Messager('请登录后评论！', {
                icon: 'bell', // 定义消息图标
            }).show()
            return
        }

        if(fid==null){
            fid = comment_id
        }
        var oThis = $("#comment_pl_"+comment_id);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        var oHfVal = oThis.siblings('.flex-text-wrap').find('.comment-input').val();
        console.log(oHfVal)
        var oHfName = oThis.parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        //alert(oHfName)
        var oAllVal = '回复@'+oHfName;

        if(oHfVal.replace(/^ +| +$/g,'') == '' || oHfVal == oAllVal){

        }else {
            $.ajax({
                type:'post',
                url:'/comment_child',
                data: {"content_id":content_id,"uid":'${user.id}',"oSize":oHfVal,"comment_time":now,"by_id":by_id,"id":fid},
                dataType:'json',
                success:function(data){
                    var comm_data =  data["data"];
                    //alert(comm_data);
                    if(comm_data=="fail"){
                        window.location.href = "/login.jsp";
                    }else {
                        var id = comm_data.id;
                        //alert(id)
                        var oAt = '回复<a class="atName">@'+oHfName+'</a>  '+oHfVal;
                        var oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a class="comment-size-name">${user.nickName} : </a><span class="my-pl-con">'+oAt+'</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"> <a style="cursor:pointer" onclick="deleteComment('+content_id+','+cuid+','+id+','+fid+')" id="comment_dl_'+id+'" class="removeBlock">删除</a> <a onclick="comment_hf('+content_id+','+id+','+fid+','+comm_data.user.id+','+cuid+')" id="comment_hf_'+id+'" style="cursor:pointer" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a onclick="reply_up('+id+')" id="change_color_'+id+'" style="cursor:pointer" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num" id="comment_upvote_'+id+'">0</i>)</a> </div> </div></div>';
                        $("#comment_pl_"+comment_id).parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display','block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();

                        var num = document.getElementById("comment_num_"+content_id).innerHTML;
                        document.getElementById("comment_num_"+content_id).innerHTML = (parseInt(num) + 1)+"";
                    }
                }
            });
        }
    }
    //搜索
    function searchForm(){
        var keyword =  $("#keyword").val();
        if(keyword!=null && keyword.trim()!=""){
            $("#indexSearchForm").submit();
        }
    }
</script>



</html>