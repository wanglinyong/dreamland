<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_OK); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!doctype html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>404 - 对不起，您查找的页面不存在！</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/404/main.css">

</head>
<body>
<div id="wrapper"><a class="logo" href="/"></a>
  <div id="main">
    <header id="header">
      <h1><span class="icon">!</span>404<span class="sub">page not found</span></h1>
    </header>
    <div id="content">
      <h2>您打开的这个的页面不存在！</h2>
      <p>当您看到这个页面,表示您的访问出错,这个错误是您打开的页面不存在,请确认您输入的地址是正确的,或者请通过下边的搜索重新查找资源,感谢您的支持!</p>
      <div class="utilities">
        <form  name="formsearch" action="" id="formkeyword">
          <div class="input-container">
            <input type="text" class="left" name="q" size="24"  value="在这里搜索..." onfocus="if(this.value=='在这里搜索...'){this.value='';}"  onblur="if(this.value==''){this.value='在这里搜索...';}" id="inputString" onkeyup="lookup(this.value);" onblur="fill();" placeholder="搜索..." />
            <button id="search"></button>
          </div>
        </form>
        <a class="button left"  onClick="history.go(-1);return true;">返回</a>
        <div class="clear"></div>
      </div>
    </div>
    <div id="footer">
      <ul>
        <li><a href="/">网站首页</a></li>
      

      </ul>
    </div>
  </div>
</div>
</body>
</html>