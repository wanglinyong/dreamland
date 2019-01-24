<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>写梦</title>

    <link href="${ctx}/css/zui/css/zui.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.css" rel="stylesheet"/>

    <script src="${ctx}/editormd/examples/js/jquery.min.js"></script>
    <script src="${ctx}/editormd/lib/editormd.js"></script>

    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>



    <link rel="stylesheet" href="${ctx}/editormd/examples/css/style.css" />
    <%--   <link rel="stylesheet" href="${ctx}/editormd/css/editormd.min.css" />--%>
    <link rel="stylesheet" href="${ctx}/editormd/css/editormd.css" /><%--自定义样式--%>

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
            min-height:850px;
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
<body class="xxzui xxzuitheme ">
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
<div class="writedream-context ">
    <div  style="margin-top: 20px;margin-left: 20px;position: absolute;">
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
            <ul class="dropdown-menu" id="dreamland-category" style="text-align: left">
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

    <div style="float: left;margin-top: 20px;margin-left: 110px;background-color: #EBEBEB;">
        <input type="text" id="txtTitle" name="txtT_itle" value="${cont.title}" class="input-file-title" maxlength="100" placeholder="&nbsp;&nbsp;&nbsp;&nbsp;输入文章标题"  style="height: 33px;width: 980px;background-color:#EBEBEB;border: 0px" >
    </div>
    <!--富文本编辑器-->
    <%--<div style="margin-top:20px ;float: left;margin-left: 20px">
        <textarea id="content" name="content" class="form-control kindeditor" style="height:600px;width: 1170px">${cont.content}</textarea>

    </div>--%>
    <!--editormd-->
    <div class="xxlib">
        <div class=" editormd" style="margin-top:20px ;float: left;margin-left: 20px" id="test-editormd">

            <textarea class="editormd-markdown-textarea" name="test-editormd-markdown-doc" id="editormd">${cont.editormd}</textarea>
            <!-- 第二个隐藏文本域，用来构造生成的HTML代码，方便表单POST提交，这里的name可以任意取，后台接受时以这个name键为准 -->
            <!-- html textarea 需要开启配置项 saveHTMLToTextarea == true -->
            <textarea class="editormd-html-textarea" name="editorhtml" id="editorhtml"></textarea>

        </div>

        <input type="hidden" name="editormd" id="editormdVal"/>
    </div>


    <br/>
    <div class="switch" style="float: left;margin-top: 720px;margin-left: 20px;position: absolute">
        <input type="checkbox" name="private_dream" id="private_dream" value="off">
        <label>私密梦</label>
        <span style="color: red">${error}</span>
    </div>
    <br/>
    <div  style="float: left;margin-top: 750px;margin-left:20px;position: absolute">
        <button class="btn btn-primary"  type="button" id="sub_dream">发表梦</button>
        <button class="btn btn-primary" id="go_back" type="button" >返回</button>
    </div>
</div>
</form>
</body>
<script>
    var testEditor;

    $(function() {
        testEditor = editormd("test-editormd", {
            placeholder : "请开始你的表演...",
            width   : "90%",
         /*   codeFold : true,*/
            height  : 640,
            syncScrolling : "single",
            path    : "${ctx}/editormd/lib/",
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "/fileUpload",
            saveHTMLToTextarea : true,
            emoji: true,
            taskList: true,
            tocm: true,         // Using [TOCM]
            tex: true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart: true,             // 开启流程图支持，默认关闭
            sequenceDiagram: true,// 开启时序/序列图支持，默认关闭
          /*  toolbarIcons : function() {
                // Or return editormd.toolbarModes[name]; // full, simple, mini
                // Using "||" set icons align right.
                return ["undo","redo","|","bold","italic","quote","uppercase","lowercase","|","h1","h2","h3","h4","|","list-ul","list-ol","hr","|","link","image","code","code-block","table","html-entities","|","watch","preview","fullscreen","clear","|","help"]
            },*/

            //下面这一行将使用dark主题
            previewTheme : "dark"

            //editor.md期望得到一个json格式的上传后的返回值，格式是这样的：
            /*
             {
             success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
             message : "提示的信息，上传成功或上传失败及错误信息等。",
             url     : "图片地址"        // 上传成功时才返回
             }
             */
        });

    }



    );



    //li标签的点击事件
    $("#dreamland-category li").click(function(){//jquery的click事件
        var val = $(this).text();
        $("#fen").html(val);
        $("#hidden_cat").val(val);
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

       var v1 = $("#editorhtml").val();
       if(v1 == null || v1.trim() == ""){
           new $.zui.Messager('文章内容为空！', {
               icon: 'bell', // 定义消息图标
               type:'danger'
           }).show()
           return;
       }

       var editormd2=$("#editormd").val();


       $("#editormdVal").val(editormd2)

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