<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
%>
<html>
  <head>
  	<link rel="shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  	<meta http-equiv="charset" content="utf-8">
  	<title>写文章</title>
  	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/jquery-1.9.1.js"></script>
  	<style type="text/css">
        div{
            width:100%;
        }
    </style>
  </head>
  <body>
  	<form action="dynamics/writeArticle.action" name="dynamics" method="post" accept-charset="utf-8">
  	<span style="font-size:22px">标题：</span> <input type="text" id="title" name="title" style="width:300px;"/>
  	<br/><br/>
  	<script id="editor" type="text/plain" name="content" style="width:1024px;height:500px;"></script>
  	<input type="submit" value="发表" onclick="uptext();"/>
  	</form>
  </body>
  <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    function uptext(){
    	document.dynamics.submit();
    }
    function getContent() {
        var content = UE.getEditor('editor').getContent();
        var title = document.getElementById("title").value;
        var  d = 'title='+title+'&content='+content;
        alert(d);
        $.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/dynamics/writeArticle.action',
			dataType:'json',
			data:d,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						alert("保存文章成功~");
						location.href="${pageContext.request.contextPath}/main/toShowMain.action";
					}else{
						alert("保存文章失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
    }
</script>
</html>