<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <script type="text/javascript" src="../js/jQuery1.7.js"></script>
    <script type="text/javascript">
		$(function(){
			
		});
    </script>
	<div id="addLog">
      <form id="form" action="http://localhost:8080/QuanDiary/log/add.do" method="post">
        <div id="username">
          <label>学&nbsp;&nbsp;&nbsp;号：</label>
          <input type="text" id="stu_username_hide" name="loginName" value="输入学号"/>
        </div>
        <div id="password">
          <label>密&nbsp;&nbsp;&nbsp;码：</label>
          <input type="password" id="stu_password_hide" name="password" value="输入密码" nullmsg="密码不能为空！" datatype="*6-16" errormsg="密码范围在6~16位之间！" sucmsg="密码验证通过！"/>
        </div>
       <!--  <div id="code">
          <label>验证码：</label>
          <input type="text" id="stu_code_hide" name="code"  value="输入验证码" nullmsg="验证码不能为空！" datatype="*4-4" errormsg="验证码有4位数！" sucmsg="验证码验证通过！"/>
          <img src="../images/captcha.jpg" title="点击更换" alt="验证码占位图"/> </div> -->
        <div id="remember">
          <input type="checkbox" name="remember">
          <label>记住密码</label>
        </div>
        <div id="login">
          <button type="submit">登录</button>
        </div>
      </form>
	</div>
