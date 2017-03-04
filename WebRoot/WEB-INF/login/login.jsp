<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  	<link rel="shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  	<title>登录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/css/bootstrap-theme.css" rel="stylesheet">
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/jquery-1.9.1.js"></script>
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
     <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/npm.js"></script>
    <style type="text/css">
      body {
        padding-top: 120px;
        padding-bottom: 40px;
        background-color: #BEBEBE;
      }
      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      div#div1{
		position:fixed;
		top:0;
		left:0;
		bottom:0;
		right:0;
		z-index:-1;
	}
	div#div1 > img {
		height:100%;
		width:100%;
		border:0;
	} 
	.enroll{
		color:#CDBA96;
		text-decoration:none;
		font-family: 'Love Ya Like A Sister', cursive;
		font-weight:bold;
		align:center;
	}
	.text-error{
		color:#FF3030;
	}
      </style>
      <script type="text/javascript">
      //输完用户名后，去cookie中找密码
      	function getCookie(){
      		var loginName = $("#loginName").val();
      		var username = getcookie("username");
      		var password = "";
      		if(loginName == username){//如果存的用户名和输入的用户名一致，则取出存的密码，放到密码框中
      			password = getcookie("password");
      		}
      		$("#password").val(password);	 
      	}
      	//获取指定名称的cookie的值
      	function getcookie(objname){
			var arrstr = document.cookie.split("; ");
			for(var i = 0;i < arrstr.length;i ++){
				var temp = arrstr[i].split("=");
				if(temp[0] == objname){ 
					return unescape(temp[1]);
				}
			}
		}
		//登录
		function login(){
			document.getElementById("msgDiv").style.display="none";
			var username = $("#loginName").val();
			var password = $("#password").val();
			var remember = $("#remember").val();
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/login/doLogin.action',
				dataType:'json',
				data:'use.username='+username+'&use.password='+password+'&use.remember='+remember,
				beforeSend:function(XMLHttpRequest){
					//alert("beforeSend");
				},
				success:function(data){
					var json = eval('('+data+')');
					if(json.success == true){
						alert("登录成功~");
						location.href="${pageContext.request.contextPath}/main/toShowMain.action";
					}else{
						document.getElementById("msgDiv").style.display="";
						$("#message").html(json.result);
					}
				},
				error:function(data){
					alert("error,textStatus:"+data);
				}
			});
		}
		//打开注册遮罩窗体
		function enroll(){
			$("#enrollModal").modal('show');
		}
		//注册
		function toEnroll(){
			var realname = $("#e_realname").val();
			realname = $.trim(realname);
			var loginname = $("#e_loginname").val();
			loginname = $.trim(loginname);
			var password = $("#e_password").val();
			password = $.trim(password);
			var email = $("#e_email").val();
			email = $.trim(email);
			var sex = $('input[name="e_sex"]:checked').val();
			if(realname == '' || loginname == '' || password == '' || sex == ''){
				shake("e_message");
				return;
			}
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/login/doEnroll.action',
				dataType:'json',
				data:'realname='+realname+'&loginname='+loginname+'&password='+password+'&email='+email+'&sex='+sex,
				beforeSend:function(XMLHttpRequest){
					//alert("beforeSend");
				},
				success:function(data){
					var json = eval('('+data+')');
					if(json.success == true){
						alert("注册成功~请点击登录按钮登录~");
						$("#loginName").val(loginname);
						$("#password").val(password);
						$("#enrollModal").modal('hide');
					}else{
						alert("注册失败~"+json.result);
					}
				},
				error:function(data){
					alert("error,textStatus:"+data);
				}
			});
		}
		//抖动提示
		function shake(o){
		    var $panel = $("#"+o);
		    //box_left = ($(window).width() -  $panel.width()) / 2;
		    box_left = 5;
		    $panel.css({'left': box_left,'position':'absolute'});
		    for(var i=1; 4>=i; i++){
		        $panel.animate({left:box_left-(40-10*i)},50);
		        $panel.animate({left:box_left+2*(40-10*i)},50);
		    }
		}
		var flag = true;
		//判断登录名是否存在
		function checkLoginname(){
			var loginname = $("#e_loginname").val();
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/login/checkLoginname.action',
				dataType:'json',
				data:'loginname='+loginname,
				beforeSend:function(XMLHttpRequest){
					//alert("beforeSend");
				},
				success:function(data){
					var json = eval('('+data+')');
					if(json.success == true){
						flag = true;
					}else{
						alert(json.result);
						flag = false;
					}
				},
				error:function(data){
					alert("error,textStatus:"+data);
				}
			});
		}
		//游客访问
		function visitor(){
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/login/visitorLogin.action',
				dataType:'json',
				beforeSend:function(XMLHttpRequest){
					//alert("beforeSend");
				},
				success:function(data){
					var json = eval('('+data+')');
					if(json.success == true){
						alert("登录成功~");
						location.href="${pageContext.request.contextPath}/main/toShowMain.action";
					}else{
						alert("登录失败~"+json.result);
					}
				},
				error:function(data){
					alert("error,textStatus:"+data);
				}
			});
		}
      </script>
  </head>
  <body class="container">
  <div id="div1"><img src="<%=request.getContextPath() %>/img/breakingbad.jpg" /></div>
  <div class="container">
  <form  class="form-signin" action="login/toLogin" method="post">      
        <h2 class="form-signin-heading">请登录</h2>
        <label for="loginName" class="sr-only">用户名</label>
        <input type="text" id="loginName" name="use.username" class="form-control" onblur="getCookie();" placeholder="用户名" required autofocus>
        <label for="password" class="sr-only">密码</label>
        <input type="password" id="password" name="use.password" class="form-control" placeholder="密码" required>
        <div class="checkbox">       
          <label>
            <input type="checkbox" value="remember" id="remember" name="use.remember"> 记住密码（需要打开cookie）
          </label>
          <div id="msgDiv" style="display: none">
          <p class="text-error" id="message"></p>
		</div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="login();">登录</button>
        <span class="enroll">没有用户？去<a onclick="enroll();" style=" cursor:pointer;">注册</a>一个,你还可以<a onclick="visitor();" style=" cursor:pointer;">以游客身份访问</a></span>
        </form>     
      </div>
      <div class="modal fade" id="enrollModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close"
		               data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h1 class="modal-title" id="myModalLabel">
		               注册
		            </h1><div id="e_message"><span class="label label-info">红色为必输项！</span></div>
		         </div>
		         <div class="modal-body">
		         <table>
		         <tr style="height:70px;">
		         <td align="right" style="width:100px;"><p class="text-error">用户名：</p></td>
		         <td style="width:200px;" align="right">
					<div class="col-sm-15">
				         <input type="text" class="form-control" id="e_realname" placeholder="用户名称">
				      </div>
				      </td>
				      </tr>
				      <tr style="height:70px;">
		         <td align="right"><p class="text-error">登录名：</p></td>
		         <td>
				      <div class="col-sm-15">
				         <input type="text" class="form-control" id="e_loginname" placeholder="登录名" onblur="checkLoginname();">
				      </div>
				      </td>
				      </tr>
				      <tr style="height:70px;">
		         <td align="right"><p class="text-error">密码：</p></td>
		         <td>
				       <div class="col-sm-15">
				         <input type="text" class="form-control" id="e_password" placeholder="登录密码">
				      </div>
				      </td>
				      </tr>
				      <tr style="height:70px;">
		         <td align="right">邮箱：</td>
		         <td>
				       <div class="col-sm-15">
				         <input type="text" class="form-control" id="e_email" placeholder="你的联系邮箱">
				      </div>
				      </td>
				      </tr>
				      <tr style="height:70px;">
		         <td align="right"><p class="text-error">性别：</p></td>
		         	<td align="center">
				       <label class="checkbox inline">
						  <input type="radio" id="inlineCheckbox1" name="e_sex" value="0" checked="checked"> 男
						</label>
				      </td>
				      <td>
				       <label class="radio inline">
						  <input type="radio" id="inlineCheckbox1" name="e_sex"  value="1"> 女
						</label>
				      </td>
				      </tr>
				      <tr><td colspan="3" align="center"><button type="button" class="btn btn-success"
		               onclick="toEnroll();">注册 》》
		            </button></td></tr>
				     </table>
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-primary"
		               data-dismiss="modal">关闭
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
		</div>
		<div id="myAlert" class="alert alert-warning">
		   <a href="#" class="close" data-dismiss="alert">&times;</a>
		   <strong>警告！</strong>您的网络连接有问题。
		</div>
  </body>
</html>