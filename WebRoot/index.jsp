<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.huangcf.login.vo.UserVO"%>
<%
	UserVO use = (UserVO)session.getAttribute("userInfo");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript" language="javascript">  
		function countDown(secs,surl){     
		 //alert(surl);     
		 var jumpTo = document.getElementById('jumpTo');
		 jumpTo.innerHTML=secs;  
		 if(--secs>0){     
		     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
		     }     
		 else{       
		     location.href=surl;     
		     }     
		 }  
       </script>
  </head>
  <body>
  		<%
  			if(use == null){
  		 %>
    	你未登录，<span id="jumpTo">5</span>秒后将跳转到登录页面
		<script type="text/javascript">countDown(5,'${pageContext.request.contextPath}/login/toLogin.action');</script>  
  		<%
  			}else{
  		 %>
  		 你已登录，<span id="jumpTo">5</span>秒后将跳转到主页
		<script type="text/javascript">countDown(5,'${pageContext.request.contextPath}/main/toShowMain.action');</script>  
  		 <%
  		 	}
  		  %>
  </body>
</html>
