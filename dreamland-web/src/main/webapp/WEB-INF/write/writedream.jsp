<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>写梦</title>
    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
    <script src="${ctx}/css/zui/lib/kindeditor/kindeditor.min.js"></script>
    <style>
        body,html{
            background-color: #EBEBEB;
            padding: 0;
            margin: 0;
            height:100%;
        }
        .writedream-context{
            background-color: white;
            margin-top: 30px;
            margin-left: 30px;
            margin-right: 30px;
            min-height:800px;
        }
        .collapse img{
            width: 25px;
            height: 25px;
            border-radius: 25px;
            padding: 0;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid" style="background-color: #2f2f2f">
        <!-- 导航头部 -->
        <div class="navbar-header">
            <!-- 移动设备上的导航切换按钮 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- 品牌名称或logo -->
            <a class="navbar-brand" href="${ctx}/list?id=${user.id}">个人空间</a>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li><a href="#">我的梦</a></li>
                <li><a href="${ctx}/index_list">首页</a></li>
                ...
                <!-- 导航中的下拉菜单 -->
                <li class="dropdown">
                    <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown">设置 <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="your/nice/url">任务</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="your/nice/url">消息</a></li>
            </ul>

            <ul class="nav navbar-nav">
                <li style="background-color: black"><a href="javascript:void(0);">写梦</a></li>
            </ul>

            <ul class="nav navbar-nav" style="margin-left: 680px">
                <li>
                    <a style="float: left" href="${ctx}/list">
                        <img src="${user.imgUrl}"  style="width: 25px;width: 25px"/>${user.nickName}
                    </a>
                </li>
            </ul>
        </div><!-- END .navbar-collapse -->
    </div>

</nav>

<!--中间内容-->
<form id="write_form" name="w_form" role="w_form" class="writedream-form" action="doWritedream?cid=${cont.id}" method="post">
<div class="writedream-context">
    <div style="margin-top: 20px;margin-left: 20px;position: absolute;">
        <div class="dropdown dropdown-hover">
            <button class="btn" type="button" data-toggle="dropdown" id="dream-diff" style="background-color:#EBEBEB"><span id="fen" >
                <c:if test="${cont.category != null}">
                    ${cont.category}
                </c:if>
                <c:if test="${cont.category == '' || cont.category == null}">
                    梦分类
                </c:if>

            </span> <span class="caret"></span></button>
            <input id="hidden_cat" hidden="hidden" name="category" value="${cont.category}"/>
            <ul class="dropdown-menu" id="dreamland-category">
                <li><a>惊悚梦</a></li>
                <li><a>爱情梦</a></li>
                <li><a>武侠梦</a></li>
                <li><a>美食梦</a></li>
                <li><a>工作梦</a></li>
                <li><a>动物梦</a></li>
                <li><a>其他梦</a></li>
            </ul>
        </div>
    </div>

    <div style="float: left;margin-top: 20px;margin-left: 110px;background-color: #EBEBEB">
        <input type="text" id="txtTitle" name="txtT_itle" value="${cont.title}" class="input-file-title" maxlength="100" placeholder="&nbsp;&nbsp;&nbsp;&nbsp;输入文章标题"  style="height: 33px;width: 1080px;background-color:#EBEBEB;border: 0px" >
    </div>
    <!--富文本编辑器-->
    <div style="margin-top:20px ;float: left;margin-left: 20px">
        <textarea id="content" name="content" class="form-control kindeditor" style="height:600px;width: 1170px">${cont.content}</textarea>

    </div>

    <br/>
    <div class="switch" style="float: left;margin-top: 670px;margin-left: 20px;position: absolute">
        <input type="checkbox" name="private_dream" id="private_dream" value="off">
        <label>私密梦</label>
        <span style="color: red">${error}</span>
    </div>
    <br/>
    <div style="float: left;margin-top: 700px;margin-left:20px;position: absolute">
        <button class="btn btn-primary"  type="button" id="sub_dream">发表梦</button>
        <button class="btn btn-primary" id="go_back" type="button" >返回</button>
    </div>
</div>
</form>
</body>
<script>
    //li标签的点击事件
    $("#dreamland-category li").click(function(){//jquery的click事件
        var val = $(this).text();
        $("#fen").html(val);
        $("#hidden_cat").val(val);
    });


   $(function () {
       editor =  KindEditor.create('#content', {
           basePath: 'css/zui/lib/kindeditor/',
           uploadJson : '${ctx}/fileUpload',
           fileManagerJson : '${ctx}/fileManager',
           allowFileManager : true,
           bodyClass : 'article-content',
           items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                                 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                                 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                                 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                                 'anchor', 'link', 'unlink']

       });
       KindEditor.sync();

   });

//私密梦开关点击事件
$(".switch").click(function () {
    var pd = document.getElementById('private_dream');
    if(pd.checked == true){
        $("#private_dream").val("on");
    }else{
        $("#private_dream").val("off");
    }
});

    //返回
   $("#go_back").click(function () {
       location.href ="javascript:history.go(-1);"
   });

   //发表梦
   $("#sub_dream").click (function(){
      var val =  $("#fen").html();
       if(val.trim()=='梦分类'){
           new $.zui.Messager('请选择梦分类！', {
               icon: 'bell', // 定义消息图标
               type:'danger'
           }).show()
           return;
       }

       var tit = $("#txtTitle").val();
       if(tit == null || tit.trim() == ""){
           new $.zui.Messager('请输入文章标题！', {
               icon: 'bell', // 定义消息图标
               type:'danger'
           }).show()
           return;
       }
       editor.sync();
       var v1 = $("#content").val();
       if(v1 == null || v1.trim() == ""){
           new $.zui.Messager('文章内容为空！', {
               icon: 'bell', // 定义消息图标
               type:'danger'
           }).show()
           return;
       }
           $("#write_form").submit();

   });

   //私密开关回显
   $(function () {
       var v = '${cont.personal}';
       if(v == "1"){
           var pd = document.getElementById('private_dream');
           pd.checked = true;
       }
   });
</script>
</html>