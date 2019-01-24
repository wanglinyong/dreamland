<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册页面</title>
	<meta name="keywords" content="梦境网">
	<meta name="content" content="梦境网">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/dreamland.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>

<body class="login_bj" style="background-color: grey">

<div class="zhuce_body" >
    <div class="zhuce_kong" id="dre_div">
    	<div class="zc">
        	<div class="bj_bai" id="regist-left" style="height: 468px">
            <h3>欢迎注册</h3>
       	  	  <form action="${ctx}/doRegister" method="post" id="registerForm">
                  <span id="reg_span"></span>
                  <input id="phone" name="phone" type="text" class="kuang_txt phone" placeholder="手机号" onblur="checkPhone();"><span id="phone_ok"></span>
                  <br/>
                <span id="phone_span" style="color: red"></span>
                <input id="email" name="email" type="text" class="kuang_txt email" placeholder="邮箱" onblur="checkEmail();"><span id="email_ok"></span>
                  <br/>
                  <span id="email_span" style="color: red"></span>
                <input id="password" name="password" type="password" class="kuang_txt possword" placeholder="密码" onKeyUp="CheckIntensity(this.value)" onblur="checkPassword();">
                  <br/>
                  <span id="password_span"></span>
                <input id="nickName" name="nickName" type="text" class="kuang_txt possword" placeholder="昵称" onblur="checkNickName();">
                  <br/>
                  <span id="nickName_span" style="color: red"></span>
                  <input id="code" name="code" type="text" class="kuang_txt yanzm" placeholder="验证码" onblur="checkCode()">
                  <div>
                      <IMG id="captchaImg" style="CURSOR: pointer"
                           onclick="changeCaptcha()"
                           title="看不清楚?请点击刷新验证码!" align='absmiddle' src="${ctx}/captchaServlet"
                           height="18" width="55">
                      <a href="javascript:;"
                         onClick="changeCaptcha()" style="color: #666;">看不清楚</a> <span id="code_span" style="color: red"></span>
                  </div>
                <div>
               		<input id="protocol" type="checkbox" onclick="checkProtocol();"><span>已阅读并同意<a href="#" target="_blank" ><span class="lan">《梦境网用户协议》</span></a></span>
                    <br/>
                    <span id="protocol_span"></span>
                </div>
                <input name="注册" type="button" class="btn_zhuce" id ="to_register" value="注册">
                  <br/>
                  <span style="color: red">${error}</span>
                
                </form>
            </div>
        	<div id="regist-right" class="bj_right" style="height: 468px">
            	<p>使用以下账号直接登录</p>
                <div>
                    <a href="to_login"><img src="${ctx}/images/Connect_logo_3.png" /></a>
                </div>

                <p>已有账号？<a href="login.html">立即登录</a></p>
            
            </div>
        </div>
    </div>
</div>
    
<div style="text-align:center;">
</div>
</body>

<script type="text/javascript">
    var phoneWidth = parseInt(window.screen.width);
    var phoneScale = phoneWidth/640;
    var ua = navigator.userAgent;
    if (/Android (\d+\.\d+)/.test(ua)){
        var version = parseFloat(RegExp.$1);
        if(version>2.3){
            document.write('<meta name="viewport" content="width=640, minimum-scale = ‘+phoneScale+‘, maximum-scale = ‘+phoneScale+‘, target-densitydpi=device-dpi">');
        }else{
            document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
        }
    } else {
       // document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
    }

    //更换验证码
    function changeCaptcha() {
        $("#captchaImg").attr('src', '${ctx}/captchaServlet?t=' + (new Date().getTime()));
    }

    //根据内容增加而增加高度
    function increaseHeight() {

           var hgt = $("#regist-left").height();
            $("#regist-left").height(hgt+30);
            $("#regist-right").height(hgt+30);

    }
    //根据内容减少而减少高度
    function reduceHeight() {
        var hgt = $("#regist-left").height();
        $("#regist-left").height(hgt-30);
        $("#regist-right").height(hgt-30);
    }
    
    //校验手机号
    var v = 0;
    var flag2 = false
    function checkPhone(){
        var phone = $("#phone").val();
        phone = phone.replace(/^\s+|\s+$/g,"");
        if(phone == ""){
            $("#phone_span").text("请输入手机号码！");
            $("#phone_ok").text("");
            var hgt = $("#regist-left").height();

            if(v==0){
                $("#regist-left").height(hgt+30);
                $("#regist-right").height(hgt+30);
                v++;
            }

            flag2 =  false;
        }
        if(!(/^1[3|4|5|8|7][0-9]\d{8}$/.test(phone))){
            $("#phone_span").text("手机号码非法，请重新输入！");
            $("#phone_ok").text("");
            var hgt = $("#regist-left").height();
            if(v==0){
                $("#regist-left").height(hgt+30);
                $("#regist-right").height(hgt+30);
                v++;
            }
            flag2 = false;
        }else{
            $.ajax({
                type:'post',
                url:'/checkPhone',
                data: {"phone":phone},
                dataType:'json',
                success:function(data){
                    var val = data['message'];
                    if(val=="success"){
                        $("#phone_span").text("");
                        $("#reg_span").text("");
                        $("#phone_ok").text("√").css("color","green");

                        var content = $("#phone_ok").text();
                        if(content=="√" ){
                            var hgt = $("#regist-left").height();
                            if(v==1){
                                $("#regist-left").height(hgt-30);
                                $("#regist-right").height(hgt-30);
                            }
                            v=0;
                        }
                        flag2 =  true;

                    }else{

                        $("#phone_span").text("该手机号已经注册！");
                        $("#phone_ok").text("");
                        var hgt = $("#regist-left").height();
                        if(v==0){
                            $("#regist-left").height(hgt+30);
                            $("#regist-right").height(hgt+30);
                            v++;
                        }
                        flag2 =  false;
                    }
                }
            });

        }

        return flag2;
    }

    //校验密码强弱
    var flag = false;
    var i = 0;
    var c = 0;
    function CheckIntensity(pwd) {
        var val = $("#password_span").text();
        if(c==0 && (val==null || val =="")){
            increaseHeight();
            c++;
        }
        //去两边空格
        if(pwd!=null) {
            pwd = pwd.replace(/^\s+|\s+$/g, "");
        }
        var len = 0;
        if(pwd != "" && pwd!=null){
            len = pwd.length;
        }
        if( len < 6){
            $("#password_span").text("密码长度少于6位，请重新输入！").css("color","red");
        }else {
            if(/^[0-9]*$/.test(pwd) || /^[A-Za-z]+$/.test(pwd)){

                $("#password_span").text("密码强度弱！").css("color","red");
            }else if(/^[A-Za-z0-9]+$ /.test(pwd) || pwd.length <= 10){

                $("#password_span").text("密码强度中！").css("color","#FF6100");
            }else{

                $("#password_span").text("密码强度强！").css("color","green");

            }
            flag = true;


        }
        return flag;
    }

    //密码框离焦事件
    var cp = 0;
    function checkPassword() {
        var password = $("#password").val();
        password = password.replace(/^\s+|\s+$/g,"");
        if(password == ""){
            $("#password_span").text("请输入密码！").css("color","red");
            if(cp==0){
                increaseHeight();
                cp++;
            }
            return false;
        }
        if(password.length < 6){
            $("#password_span").text("密码长度少于6位，请重新输入！").css("color","red");
            if(cp==0){
                cp++;
                increaseHeight();
            }
            return false;
        }


        if(flag){
            $("#password_span").text("");
            $("#reg_span").text("");

            if(cp==1){
                reduceHeight();
                cp=0;
            }
            return true;
        }
    }




    //邮箱校验
    var e = 0;
    var flag_e =false;
    function checkEmail() {
        var email = $("#email").val();
        email = email.replace(/^\s+|\s+$/g,"");
        if(email == "" || email==null){
            if(e==0){
                increaseHeight();
                e++;
            }
            $("#email_span").text("请输入邮箱账号！");
            $("#email_ok").text("");
             flag_e = false;
        }
        if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/.test(email))){
            $("#email_span").text("邮箱账号不存在，请重新输入！");
            $("#email_ok").text("");
            if(e==0){
                increaseHeight();
                e++;
            }
             flag_e = false;
        }else{
            //验证邮箱是否已经注册
            $.ajax({
                type:'post',
                url:'/checkEmail',
                data: {"email":email},
                dataType:'json',
                success:function(data) {
                    var val = data['message'];
                    if(val=="success"){
                        $("#email_span").text("");
                        $("#reg_span").text("");
                        $("#email_ok").text("√").css("color","green");

                        var content = $("#email_ok").text();
                        if(content=="√" ){
                            var hgt = $("#regist-left").height();
                            if(v==1){
                                $("#regist-left").height(hgt-30);
                                $("#regist-right").height(hgt-30);
                            }
                            v=0;
                        }
                        flag_e = true;

                    }else{

                        $("#email_span").text("该Email已经注册！");
                        $("#email_ok").text("");
                        var hgt = $("#regist-left").height();
                        if(v==0){
                            $("#regist-left").height(hgt+30);
                            $("#regist-right").height(hgt+30);
                            v++;
                        }
                        flag_e =  false;
                    }
                }
           });

        }
        return flag_e;
    }

    //昵称校验
    function checkNickName() {
        var nickName = $("#nickName").val();
        nickName = nickName.replace(/^\s+|\s+$/g,"");
        if(nickName == ""){
            $("#nickName_span").text("请输入昵称！");
            return false;
        }else{
            $("#nickName_span").text("");
            $("#reg_span").text("");
            return true;
        }
    };

    //验证码校验
    var flag_c = false;
    function checkCode() {
        var code = $("#code").val();
        code = code.replace(/^\s+|\s+$/g,"");
        if(code == ""){
            $("#code_span").text("请输入验证码！").css("color","red");
            flag_c = false;
        }else{
            $.ajax({
                type: 'post',
                url: '/checkCode',
                data: {"code": code},
                dataType: 'json',
                success: function (data) {
                    var val = data['message'];
                    if (val == "success") {
                        $("#code_span").text("√").css("color","green");
                        $("#reg_span").text("");
                        flag_c = true;
                    }else {
                        $("#code_span").text("验证码错误！").css("color","red");
                        flag_c = false;
                    }
                }
            });

        }
        return flag_c;
    }

    //勾选用户协议校验
    function checkProtocol() {
        if($('#protocol').prop("checked"))
        {
            $("#reg_span").text("");
            return true;
        }
        else{
            return false;
        }

    };

    //提交注册信息
    $("#to_register").click (function(){
        if(!checkProtocol()){
            $("#protocol_span").text("请勾选\"阅读并接受梦境网用户协议\"！").css("color","red");
        }else{
            $("#protocol_span").text("");
        }

        if(checkPhone()  &&  checkPassword()&& checkEmail() && checkNickName()&& checkCode() && checkProtocol()){
            $("#registerForm").submit();
        }else {
            $("#reg_span").text("请将信息填写完整！").css("color","red");
        }

    });


</script>
</html>