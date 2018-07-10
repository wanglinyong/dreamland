<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_OK); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500页面</title>
    <style type="text/css">
        a { color:#555}
        html { height: 100%; width: 100%; background: url(${ctx}/images/bj.png) repeat; }
        body {margin: 0px auto;}
        .cw404 { width:755px; margin: 0px auto; }
        .cw404_nr { height: 284px; width: 750px; background: #e5e5e5; border: 1px solid #aaaaaa; margin: 0px auto; }
        .cw404_top { height: 200px; width: 750px; margin: 0px auto; }
        .top_left { float: left; height: 150px; width: 150px; }
        .top_right { font-size: 100px; line-height: 150px; text-align: center; float: left; height: 150px; width: 600px; color: #3f8bc7; font-family: "微软雅黑"; font-weight: bold; }
        .nr_top { font-size: 25px; line-height: 40px; height: 40px; margin-right: 5px; margin-left: 5px; border-bottom: 1px solid #aaaaaa; text-align: center; font-weight: bold; color: #555555; }
        .nr_min { height: 200px; border-top: 1px solid #FFFFFF; margin-right: 5px; margin-left: 5px; border-bottom: 1px solid #aaaaaa; }
        .nr_foot { font-size: 12px; line-height: 40px; background: #eaeaea; height: 40px; border-top: 1px solid #FFFFFF; color: #555; margin-right: 5px; margin-left: 5px; }
        .min_top { font-size: 14px; line-height: 30px; height: 30px; }
        .min_choose { height: 30px; width: 100px; border: 1px solid #aaa; font-size: 12px; line-height: 30px; text-align: center; background: #eaeaea; }
        .else { height: 30px;  }
        .else li{ width:100px; float:left; margin-left:10px; font-size:12px; line-height:30px; display:inline}
        a:link { text-decoration: none; }
        a:visited { text-decoration: none; }
        a:hover { text-decoration: none; }
        a:active { text-decoration: none; }
    </style>
</head>

<body>
<div class="cw404">
    <div class="cw404_top">
        <div class="top_left"><img src="${ctx}/images/500.png"></div>

        <div class="top_right">500</div>
    </div>
    <div class="cw404_nr">
        <div class="nr_top">系统出错啦！</div>

        <div class="nr_min">
            <div class="min_top" style="margin-left: 180px;margin-top: 50px">您访问的系统出错啦！您可以选择返回首页，或者链接到其它地址</div>

            <div class="min_choose" style="margin-left: 260px;margin-top: 60px;float: left" ><a href="/">返回首页</a></div>
            <div style="float: left;margin-top: 60px;margin-left: 20px" class="min_choose"><a href="https://www.baidu.com/">百度</a></div>
        </div>
    </div>
</div>

</body>
</html>