<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.huangcf.login.vo.UserVO"%>
<%
	UserVO use = (UserVO)session.getAttribute("userInfo");
 %>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  	<link rel="shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  	<title>首页</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 页面布局相关的引入 -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/css/content.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/css/bootstrap.min1.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/css/APlayer.min.css">
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/bootstrap-me.js"></script>
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/bootstrap-me-2.js"></script>
    <script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/APlayer.min.js"></script>
    <style type="text/css">
    	.comments {  
		 width:100%;
		 overflow:auto;  
		 word-break:break-all;  
		}
		.box{text-align:center;}
    </style>
  	<script type="text/javascript">
  	 $(function(){//获取主页内容
  	 	queryUserMessage();
  	 	//playMp3("1","2");
  	 	initMp3();
  	 });
  	 //切歌功能停用
  	 function changeMp3(bz){
  	 	var rownum = document.getElementById("mp3_rownum").value;
  	 	playMp3(rownum,bz);
  	 }
  	 //MP3播放器初始化，加载资源文件
  	 function initMp3(){
  	 	var ap3 = new APlayer({
            element: document.getElementById('player3'),
            narrow: false,
            autoplay: false,
            showlrc: true,
            music: {
                title: '一个短篇',
                author: '腰乐队',
                url: '<%=basePath%>/resource/一个短篇.mp3',
                pic: '<%=basePath%>/bootstrap-3.3.5-dist/img/一个短篇.jpg'           
            }
        });
        //ap3.pause();
        ap3.init();
        //ap3.audio.pause();
        //$("#mp3_rownum").val(data.n);
  	 }
  	 //切歌功能停用
  	 function playMp3(rownum,bz){
  	 	$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/mp3/queryMp3.action',
			dataType:'json',
			data:'rownum='+rownum+'&bz='+bz,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putMp3Data(json.result);
					}else{
						alert("查询MP3失败~"+json.result);
					}
			},
			error:function(data){
				alert(data);
				alert("error:"+data);
			}
  	 	});
  	 }
  	 function putMp3Data(data){
  	 	var mp3 = $("#mp3");
  	 	mp3.empty();
  	 	mp3.append("<div id='player3' class='aplayer'></div>");
  	 	data = eval('('+data+')');
  	 	var ap3 = new APlayer({
            element: document.getElementById('player3'),
            narrow: false,
            autoplay: true,
            showlrc: false,
            music: {
                title: data.title,
                author: data.author,
                url: '<%=basePath%>/resource/'+data.mp3_src,
                pic: '<%=basePath%>/bootstrap-3.3.5-dist/img/'+data.mp3_vocer_src           
            }
        });
        //ap3.pause();
        ap3.init();
        //ap3.audio.pause();
        $("#mp3_rownum").val(data.n);
  	 }
  	 function queryUserMessage(){
  	 	var id = '<%=use.getId() %>';
  	 	var ip = '<%=use.getIp() %>';
  	 	$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/login/showUser.action',
			dataType:'json',
			data:'use.id='+id+'&use.ip='+ip,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putUserData(json.result);
					}else{
						alert("查询登录用户信息失败~"+json.result);
					}
			},
			error:function(data){
				alert(data);
				alert("error:"+data);
			}
  	 	});
  	 	queryDynamics('1','0');//查询完用户之后查询动态数据
  	 	queryVisitorSum();
  	 }
  	 //查询访问总人数
  	 function queryVisitorSum(){
  	 	$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/login/queryVisitorSum.action',
			dataType:'json',
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						$("#visitorSum").text(json.result);
					}else{
						alert("查询访问总人数失败~"+json.result);
					}
			},
			error:function(data){
				alert(data);
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //查询动态
  	 function queryDynamics(page,status){
  	 	var role = '<%=use.getRole() %>';
  	 	var id = '<%=use.getId() %>';
  	 	$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/dynamics/showDynamics.action',
			dataType:'json',
			data:'use.role='+role+'&page='+page+'&use.id='+id,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putDynamicsData(json.result);
					}else{
						alert("查询动态失败~"+json.result);
					}
			},
			error:function(data){
				alert(data);
				alert("error,textStatus:"+data);
			}
  	 	});
  	 	queryDynamicsPage(page);//查询页码
  	 	if(status == '0'){
  	 		queryLog('1');//查询完动态后查询更新日志
  	 	}
  	 }
  	 //查询动态总页数
  	 function queryDynamicsPage(page){
  	 	$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/dynamics/queryTotalPage.action',
			dataType:'json',
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putDynamicsPageData(json.page,page);
					}else{
						alert("查询动态总页数失败~"+json.result);
					}
			},
			error:function(data){
				alert(data);
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //设置动态页码
  	 function putDynamicsPageData(totalPage,page){
  	 	var dynamicsPage = $("#dynamics-page");
  	 	dynamicsPage.empty();
  	 	var html = "";
  	 	var pageSum = parseInt(totalPage);
  	 	var currentPage = parseInt(page);
  	 	if(page == '1'){//当前是第一页
  	 		if(totalPage == '1'){//总页数一页
  	 			html += "<ul class='pagination'>"+
					"<li class='disabled'><a>上一页</a></li>"+
					"<li class='active'><a>1</a></li>"+
					"<li class='disabled'><a>下一页</a></li>"+
				"</ul>";
  	 		}else{//总页数大于一页
  	 			html += "<ul class='pagination'>"+
					"<li class='disabled'><a>上一页</a></li>";
  	 			for(var i=1;i<=pageSum;i++){
  	 				if(currentPage == i){
  	 					html += "<li class='active'><a>"+i+"</a></li>";
  	 				}else{
  	 					html += "<li><a onclick='queryDynamics("+i+",1);'>"+i+"</a></li>";
  	 				}
  	 			}
  	 			if(currentPage == pageSum){//当前页等于总页数
  	 				html += "<li class='disabled'><a>下一页</a></li>";
  	 			}else{
  	 				html += "<li><a onclick='queryDynamics("+(currentPage+1)+",1);'>下一页</a></li>";
  	 			}
  	 			html += "</ul>";
  	 		}
  	 	}else{
  	 		html += "<ul class='pagination'>"+
					"<li><a  onclick='queryDynamics("+(currentPage-1)+",1);'>上一页</a></li>";
			for(var i=1;i<=pageSum;i++){
 				if(currentPage == i){
 					html += "<li class='active'><a>"+i+"</a></li>";
 				}else{
 					html += "<li><a onclick='queryDynamics("+i+",1);'>"+i+"</a></li>";
 				}
 			}
 			if(currentPage == pageSum){//当前页等于总页数
 				html += "<li class='disabled'><a>下一页</a></li>";
 			}else{
 				html += "<li><a onclick='queryDynamics("+(currentPage+1)+",1);'>下一页</a></li>";
 			}
 			html += "</ul>";
  	 	}
  	 	dynamicsPage.append(html);
  	 }
  	 //查询日志
  	 function queryLog(page){
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/log/showLog.action',
			dataType:'json',
			data:'page='+page,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putLogData(json.result);
					}else{
						alert("查询日志失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 	queryMessage('1');//查询留言
  	 }
  	 //赋更新日志的数据
  	 function putLogData(data){
  	 	data = eval('('+data+')');
  	 	$("#log").html("");//清空遗留的数据
  	 	var html = "";
  	 	for(var i in data){
  	 		var xh = parseInt(data[i].sid);
  	 		if(xh%2 == 0){//取余
  	 			html += '<tr class="error">';
  	 		}else{
  	 			html += '<tr class="success">';
  	 		}
  	 		var content = data[i].content;
  	 		if(content.length>=8){
  	 			content = content.substr(0,8)+"...";
  	 		}
  	 		html += "<td>"+data[i].sid+
						"</td><td>"+data[i].time+
						"</td><td><a href='#' class='tooltip-test' data-toggle='tooltip'  title='"+data[i].content+"'>"
						+content+
						"</a></td></tr>";
  	 	}
  	 	$("#log").append(html);
  	 	$("[data-toggle='tooltip']").tooltip();
  	 }
  	 //赋登录用户的值
  	 function putUserData(data){
  	 	data = eval('('+data+')');
  	 	$("#realname").text(data.realname);
  	 	$("#role").text(data.role);	
  	 	if(data.role == '游客'){
  	 		$("#count").text("【很抱歉，游客不能统计访问次数】");
  	 	}else{
  	 		$("#count").text(data.count);
  	 	}
  	 	$("#ip").text(data.ip);
  	 }
  	 //赋动态的值
  	 function putDynamicsData(data){
  	 	var html = "";
  	 	var div = $("#dynamics");
  	 	div.empty();//清空遗留的动态的内容
  	 	data = eval('('+data+')');
  	 	for(var i in data){//拼html
  	 		if(data[i].flag == '0'){
	  	 		html += "<blockquote>"+
					"<p>"+data[i].content+"</p>"+
					"<small><cite>"+data[i].username+"</cite> 发表于 <cite>"+data[i].time+"</cite></small>"+
					"<button type='button' class='btn btn-mini' data-toggle='modal'>评论[<span id='contentSum-"+data[i].id+"'>"+data[i].contentSum+"</span>]</button>&nbsp;&nbsp;&nbsp;&nbsp;"+
					"<button type='button' class='btn btn-mini' data-toggle='modal' onclick='likeDynamics("+data[i].id+")'><span id='dynamicsLike-"+data[i].id+"'>"+data[i].sfz+"</span>[<span id='likeSum-"+data[i].id+"'>"+data[i].likeSum+"</span>]</button>"+
				"</blockquote><div id='"+data[i].id+"' style='display:none;'></div>";
			}else if(data[i].flag == '1'){
				html += "<blockquote>"+
					"<a href='${pageContext.request.contextPath}/dynamics/showArticle.action?id="+data[i].id+"'>"+data[i].title+"</a>"+
					"<small><cite>"+data[i].username+"</cite> 发表于 <cite>"+data[i].time+"</cite></small>"+
					"<button  onclick='openContent("+data[i].id+","+1+");' type='button' class='btn btn-mini' data-toggle='modal'>评论[<span id='contentSum-"+data[i].id+"'>"+data[i].contentSum+"</span>]</button>&nbsp;&nbsp;&nbsp;&nbsp;"+
					"<button type='button' class='btn btn-mini' data-toggle='modal' onclick='likeDynamics("+data[i].id+")'><span id='dynamicsLike-"+data[i].id+"'>"+data[i].sfz+"</span>[<span id='likeSum-"+data[i].id+"'>"+data[i].likeSum+"</span>]</button>"+
				"</blockquote><div id='"+data[i].id+"' style='display:none;'></div>";
			}
  	 	}
  	 	div.append(html);
  	 }
  	 //赞或取消赞
  	 function likeDynamics(did){
  	 	var id = '<%=use.getId() %>';
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/like/likeDynamics.action',
			dataType:'json',
			async:false,
			data:'id='+id+'&did='+did,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						updateDynamicsLike(id,did);
					}else{
						alert("赞或取消赞失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //赞或取消赞后更新
  	 function updateDynamicsLike(id,did){
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/like/updateDynamicsLike.action',
			dataType:'json',
			async:false,
			data:'id='+id+'&did='+did,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putDynamicsLike(json.sum,json.sfz,did);
					}else{
						alert("查询日志失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //更新动态的赞
  	 function putDynamicsLike(sum,sfz,id){
  	 	$("#dynamicsLike-"+id).html(sfz);
  	 	$("#likeSum-"+id).html(sum);
  	 }
  	 //显示评论
  	 function openContent(id,page){
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/content/showContent.action',
			dataType:'json',
			async:false,
			data:'id='+id+'&page='+page,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putContentData(json.result,id);
					}else{
						alert("查询日志失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 	queryContentPage(id,page);
  	 }
  	 //查询评论总页数
  	 function queryContentPage(id,page){
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/content/queryTotalPage.action',
			dataType:'json',
			data:'id='+id,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putContentPage(json.page,id,page);
					}else{
						alert("查询日志失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //设置评论页码
  	 function putContentPage(totalPage,id,page){
  	 	var div = $("#content-page"+id);
  	 	div.empty();
  	 	var html = "";
  	 	var pageSum = parseInt(totalPage);
  	 	var currentPage = parseInt(page);
  	 	if(page == '1'){//当前是第一页
  	 		if(totalPage == '1'){//总页数一页
  	 			html += "<ul class='pagination'>"+
					"<li class='disabled'><a>上一页</a></li>"+
					"<li class='active'><a>1</a></li>"+
					"<li class='disabled'><a>下一页</a></li>"+
				"</ul>";
  	 		}else{//总页数大于一页
  	 			html += "<ul class='pagination'>"+
					"<li class='disabled'><a>上一页</a></li>";
  	 			for(var i=1;i<=pageSum;i++){
  	 				if(currentPage == i){
  	 					html += "<li class='active'><a>"+i+"</a></li>";
  	 				}else{
  	 					html += "<li><a onclick='openContentByPage("+id+","+i+");'>"+i+"</a></li>";
  	 				}
  	 			}
  	 			if(currentPage == pageSum){//当前页等于总页数
  	 				html += "<li class='disabled'><a>下一页</a></li>";
  	 			}else{
  	 				html += "<li><a onclick='openContentByPage("+id+","+(currentPage+1)+");'>下一页</a></li>";
  	 			}
  	 			html += "</ul>";
  	 		}
  	 	}else{
  	 		html += "<ul class='pagination'>"+
					"<li><a  onclick='openContentByPage("+id+","+(currentPage-1)+");'>上一页</a></li>";
			for(var i=1;i<=pageSum;i++){
 				if(currentPage == i){
 					html += "<li class='active'><a>"+i+"</a></li>";
 				}else{
 					html += "<li><a onclick='openContentByPage("+id+","+i+");'>"+i+"</a></li>";
 				}
 			}
 			if(currentPage == pageSum){//当前页等于总页数
 				html += "<li class='disabled'><a>下一页</a></li>";
 			}else{
 				html += "<li><a onclick='openContentByPage("+id+","+(currentPage+1)+");'>下一页</a></li>";
 			}
 			html += "</ul>";
  	 	}
  	 	div.append(html);
  	 }
  	 //根据页数显示评论
  	 function openContentByPage(id,page){
  	 	var div = $("#"+id);
  	 	div.empty();
  	 	div.css('display','none');
  	 	openContent(id,page);
  	 }
  	 //设置评论数据
  	 function putContentData(data,id){
  	 	var html = "";
  	 	var div = $("#"+id);
  	 	if(div.css('display')=='none'){
	  	 	data = eval('('+data+')');
	  	 	//最外层的大的评论框
	  	 	html += "<div class='col-sm-6'><div class='widget-box'><div class='widget-header'><h4 class='lighter smaller'><i class='icon-comment blue'></i>评论</h4></div><div class='widget-body'><div class='widget-main no-padding'><div class='dialogs'>";
	  	 	if(data.length == 0){
	  	 		html += "<div><p class='text-error'>目前还没有评论，请慷慨的写下你的看法吧~</p></div>";
	  	 	}
	  	 	for(var i in data){
	  	 		if(data[i].sex == '0'){
	  	 			html += "<div class='itemdiv dialogdiv'><div class='user'>"+
	  	 			"<img alt='"+data[i].username+"' src='<%=basePath%>/img/man.png' /></div><div class='body'><div class='time'><i class='icon-time'></i>";
	  	 		}else{
	  	 			html += "<div class='itemdiv dialogdiv'><div class='user'>"+
	  	 			"<img alt='"+data[i].username+"' src='<%=basePath%>/img/women.png' /></div><div class='body'><div class='time'><i class='icon-time'></i>";
	  	 		}
				var xh = parseInt(data[i].sid);
	  	 		if(xh%2 == 0){//取余
	  	 			html += "<span class='green'>"+data[i].time+"</span></div><div class='name'>";
	  	 		}else{
	  	 			html += "<span class='blue'>"+data[i].time+"</span></div><div class='name'>";
	  	 		}
				html += "<a href='#'>"+data[i].username+"</a></div>"+
				"<div class='text'>"+data[i].content+"</div></div></div>";
	  	 	}
	  	 	//写评论的
	  	 	html += "<div style='padding:0px 0px 0px 4px;'><form class='form-search'><input id='newContent-"+id+"'class='input-medium search-query' type='text' style='width:80%''/>&nbsp;&nbsp;"+
	  	 	"<button type='button' class='btn' onclick='writeContent("+id+");'>发表评论</button>"+
			"</form><div class='pagination pagination-larger pagination-centered' id='content-page"+id+"'></div></div></div></div></div>";
			div.append(html);
			div.css('display','block'); //设置显示
		}else{
			div.empty();//清除内容
			div.css('display','none');//设置隐藏 
		}
  	 }
  	 //给动态写评论
  	 function writeContent(id){
  	 	var content = $("#newContent-"+id).val();
  	 	if(content.length>100){
  	 		alert("因为显示效果的原因，评论字数控制在100字以内~");
  	 		return;
  	 	}
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/content/writeContent.action',
			dataType:'json',
			data:'id='+id+'&content='+content,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						$("#"+id).empty();//清空评论区内容
						$("#"+id).css('display','none');//需要先设置为隐藏，否则显示不出来
						openContent(id,'1');
						updateContentSum(id);
					}else{
						alert("写评论失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //给动态评论后，实时更新动态的评论数（按钮评论右边的数字）
  	 function updateContentSum(id){
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/content/updateContentSum.action',
			dataType:'json',
			data:'id='+id,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						$("#contentSum-"+id).html(json.sum);//更新评论总数
					}else{
						alert("更新评论数失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //写动态
  	 function writeDynamics(){
  	 	var dynamics = $("#newDynamics").val();
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/dynamics/writeDynamics.action',
			dataType:'json',
			data:'dynamics='+dynamics,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						$("#newDynamics").val('');//发表成功，清空内容
						$('#modal-container-writeDynamics').modal('hide');//隐藏弹出窗体
						queryDynamics('1');//写完之后查询动态并更新
					}else{
						alert("新增动态失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //写更新日志
  	 function writeLog(){
  	 	var log = $("#newLog").val();
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/log/writeLog.action',
			dataType:'json',
			data:'log='+log,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						$("#newLog").val('');//发表成功，清空内容
						$('#modal-container-writeLog').modal('hide');//隐藏弹出窗体
						queryLog('1');//写完之后查询动态并更新
					}else{
						alert("新增日志失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //查询留言
  	 function queryMessage(page){
  	 	$("#advise-li").removeClass("active");//清除建议的样式
  	 	$("#message-li").addClass("active");//留言增加样式
  	 	$("#message").empty();
  	 	$("#advise").empty();
  	 	$("#message").css('display','block');
  	 	$("#advise").css('display','none');
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/message/queryMessage.action',
			dataType:'json',
			data:'page='+page,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putMessageData(json.result);
					}else{
						alert("查询留言失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //设置留言数据
  	 function putMessageData(data){
  	 	var data = eval('('+data+')');
  	 	var div = $("#message");
  	 	var html = "";
  	 	html += "<div class='col-sm-6'><div class='widget-box'><div class='widget-header'><h4 class='lighter smaller'><i class='icon-comment blue'></i>留言</h4></div><div class='widget-body'><div class='widget-main no-padding'><div class='dialogs'>";
  	 	if(data.length == 0){
	  	 		html += "<div><p class='text-error'>目前还没有留言，你有什么想给博主说的？</p></div>";  	 		
  	 	}
  	 	for(var i in data){
  	 		if(data[i].sex == '0'){
  	 			html += "<div class='itemdiv dialogdiv'><div class='user'>"+
	  	 			"<img alt='"+data[i].name+"' src='<%=basePath%>/img/man.png' /></div><div class='body'><div class='time'><i class='icon-time'></i>";
			}else{
				html += "<div class='itemdiv dialogdiv'><div class='user'>"+
	  	 			"<img alt='"+data[i].name+"' src='<%=basePath%>/img/women.png' /></div><div class='body'><div class='time'><i class='icon-time'></i>";
			}
			var xh = parseInt(data[i].sid);
  	 		if(xh%2 == 0){//取余
  	 			html += "<span class='green'>"+data[i].time+"</span></div><div class='name'>";
  	 		}else{
  	 			html += "<span class='blue'>"+data[i].time+"</span></div><div class='name'>";
  	 		}
			html += "<a href='#'>"+data[i].name+"</a></div>"+
			"<div class='text'>"+data[i].content+"</div></div></div>";
  	 	}
  	 	html += "<div style='padding:0px 0px 0px 4px;' class='writeMessage'><form class='form-search'><input id='newMessage' class='input-medium search-query' type='text' style='width:90%''/>"+
	  	 	"<br/><br/><input type='button' class='btn btn-success' onclick='writeMessage(0);' value='发表留言'/>"+
			"</form></div></div></div></div>";
			div.append(html);
  	 }
  	 //写留言
  	 function writeMessage(secret){
  	 	var message = $("#newMessage").val();
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/message/writeMessage.action',
			dataType:'json',
			data:'message='+message+'&secret='+secret,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						queryMessage('1');
					}else{
						alert("写留言失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //查询建议
  	 function queryAdvise(page){
  	 	$("#message-li").removeClass("active");//清除建议的样式
  	 	$("#advise-li").addClass("active");//留言增加样式
  	 	$("#message").empty();
  	 	$("#advise").empty();
  	 	$("#advise").css('display','block');
  	 	$("#message").css('display','none');
  	 	$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/advise/queryAdvise.action',
			dataType:'json',
			data:'page='+page,
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						putAdviseData(json.result);
					}else{
						alert("查询建议失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 }
  	 //设置建议数据
  	 function putAdviseData(data){
  	 	var data = eval('('+data+')');
  	 	var div = $("#advise");
  	 	var html = "";
  	 	html += "<div class='col-sm-6'><div class='widget-box'><div class='widget-header'><h4 class='lighter smaller'><i class='icon-comment blue'></i>建议</h4></div><div class='widget-body'><div class='widget-main no-padding'><div class='dialogs'>";
  	 	if(data.length == 0){
	  	 		html += "<div><p class='text-error'>目前还没有建议，你对本站有什么看法？</p></div>";  	 		
  	 	}
  	 	for(var i in data){
  	 		html += "<div class='itemdiv dialogdiv'><div class='user'>"+
	  	 		"<img alt='"+data[i].name+"' src='<%=basePath%>/img/avatar.png' /></div><div class='body'><div class='time'><i class='icon-time'></i>";
			var xh = parseInt(data[i].sid);
  	 		if(xh%2 == 0){//取余
  	 			html += "<span class='green'>"+data[i].time+"</span></div><div class='name'>";
  	 		}else{
  	 			html += "<span class='blue'>"+data[i].time+"</span></div><div class='name'>";
  	 		}
			html += "<a href='#'>"+data[i].name+"</a></div>"+
			"<div class='text'>"+data[i].content+"</div></div></div>";
  	 	}
  	 	html += "<div style='padding:0px 0px 0px 4px;' class='writeMessage'><form class='form-search'><input id='newContent'class='input-medium search-query' type='text' style='width:90%''/>"+
	  	 	"<br/><br/><input type='button' class='btn btn-success' onclick='writeAdvise();' value='发表建议'/>"+
			"</form></div></div></div></div>";
		div.append(html);
  	 }
  	 //登出
  	 function logout(){
  	 	if(window.confirm("确定退出登录吗？")){
  	 		$.ajax({
  	 		type:'post',
			url:'${pageContext.request.contextPath}/login/logout.action',
			dataType:'json',
			beforeSend:function(XMLHttpRequest){
				//alert("beforeSend");
			},
			success:function(data){
					var json = eval('('+data+')');
					if(json.success){
						alert("退出登录成功~");
						location.href="${pageContext.request.contextPath}/login/toLogin.action";
					}else{
						alert("退出登录失败~"+json.result);
					}
			},
			error:function(data){
				alert("error,textStatus:"+data);
			}
  	 	});
  	 	}
  	 }
  	 //写文章
  	 function writeArticle(){
  	 	location.href="${pageContext.request.contextPath}/dynamics/toWriteArticle.action";
  	 }
  	 //显示关于
  	 function showAbout(){
  	 	location.href="${pageContext.request.contextPath}/dynamics/toShowAbout.action";
  	 }
  	</script>
  </head>
  <body>
  	<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container-fluid">
						 <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></a> <a href="#" class="brand">云隐浪爷</a>
						<div class="nav-collapse collapse navbar-responsive-collapse">
							<ul class="nav">
								<li class="active">
									<a href="#">主页</a>
								</li>
								<li>
									<a onclick="showAbout();" style="cursor:pointer;">关于本站</a>
								</li>
								<li>
									<a href="#">链接</a>
								</li>
								<li class="dropdown">
									 <a data-toggle="dropdown" class="dropdown-toggle" href="#">下拉菜单<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
										<li>
											<a href="#">下拉导航1</a>
										</li>
										<li>
											<a href="#">下拉导航2</a>
										</li>
										<li>
											<a href="#">其他</a>
										</li>
										<li class="divider">
										</li>
										<li class="nav-header">
											标签
										</li>
										<li>
											<a href="#">链接1</a>
										</li>
										<li>
											<a href="#">链接2</a>
										</li>
									</ul>
								</li>
							</ul>
							<ul class="nav pull-right">
								<li>
									<a style="cursor:default;">欢迎你，第[ <span id="visitorSum"></span> ]位访问者~</a>
								</li>
								<li>
									<a onclick="logout();" style="cursor:pointer;">退出登录</a>
								</li>
								<li class="divider-vertical">
								</li>
								<li class="dropdown">
									 <a data-toggle="dropdown" class="dropdown-toggle" href="#">下拉菜单<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
										<li>
											<a href="#">下拉导航1</a>
										</li>
										<li>
											<a href="#">下拉导航2</a>
										</li>
										<li>
											<a href="#">其他</a>
										</li>
										<li class="divider">
										</li>
										<li>
											<a href="#">链接3</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
						
					</div>
				</div>
				
			</div>
			<!-- <div class="carousel slide" id="carousel-245760">
				<ol class="carousel-indicators">
					<li data-slide-to="0" data-target="#carousel-245760">
					</li>
					<li data-slide-to="1" data-target="#carousel-245760">
					</li>
					<li data-slide-to="2" data-target="#carousel-245760" class="active">
					</li>
				</ol>
				<div class="carousel-inner">
					<div class="item">
						<img alt="" src="<%=basePath%>/img/1.jpg" />
						<div class="carousel-caption">
							<h4>
								棒球
							</h4>
							<p>
								棒球运动是一种以棒打球为主要特点，集体性、对抗性很强的球类运动项目，在美国、日本尤为盛行。
							</p>
						</div>
					</div>
					<div class="item">
						<img alt="" src="<%=basePath%>/img/2.jpg" />
						<div class="carousel-caption">
							<h4>
								冲浪
							</h4>
							<p>
								冲浪是以海浪为动力，利用自身的高超技巧和平衡能力，搏击海浪的一项运动。运动员站立在冲浪板上，或利用腹板、跪板、充气的橡皮垫、划艇、皮艇等驾驭海浪的一项水上运动。
							</p>
						</div>
					</div>
					<div class="item active">
						<img alt="" src="<%=basePath%>/img/3.jpg" />
						<div class="carousel-caption">
							<h4>
								自行车
							</h4>
							<p>
								以自行车为工具比赛骑行速度的体育运动。1896年第一届奥林匹克运动会上被列为正式比赛项目。环法赛为最著名的世界自行车锦标赛。
							</p>
						</div>
					</div>
				</div> <a data-slide="prev" href="#carousel-245760" class="left carousel-control">‹</a> <a data-slide="next" href="#carousel-245760" class="right carousel-control">›</a>
			</div>-->
		</div>
	</div>
	<div class="row-fluid">
		<div class="span3">
			<h3 class="text-left">
				用户信息
			</h3>
			<dl>
				<dt>
					用户名：
				</dt>
				<dd>
					<span id="realname"></span>
				</dd>
				<dt>
					用户角色：
				</dt>
				<dd>
					<span id="role"></span>
				</dd>
				<dt>
					累计登录次数：
				</dt>
				<dd>
					这是你第<span id="count"></span>次登录本站
				</dd>
				<dt>
					本次登录IP地址：
				</dt>
				<dd>
					<span id="ip"></span>
				</dd>
			</dl>
			<div id="mp3">
			<div id="player3" class="aplayer">
					<pre class="aplayer-lrc-content">
						[00:02.54]作曲 : 杨绍昆
						[00:25.31]作词 : 刘弢
						[00:49.55]旋转 跳跃喔
						[00:55.54]他感到每条路都在头痛
						[01:01.54]新鲜的帕特里克满脑子
						[01:04.40]都是开拓的自慰器
						[01:07.25]那些男人爱的男人爱市政
						[01:10.11]市政爱市民 市民爱流连
						 
						[01:13.54]旋转 跳跃喔
						[01:19.54]他感到飞鸟们也在头痛
						[01:25.54]冒牌的帕特里克满脑子
						[01:28.11]都是稳妥的独角戏
						[01:31.54]那些男孩爱的男人爱机器
						[01:34.39]机器爱法律 法律是你
						 
						[01:43.25]深夜里辛蒂蕾拉们倒下的地方
						[01:49.45]促成整片血红的高楼
						[01:55.45]在搞与不搞之间泛起淡淡的哀伤
						[02:01.46]他的来头已经腐朽
						[02:07.45]别担心没有哪一首歌能够
						[02:13.40]把这个现实唱到地狱去
						[02:19.68]当你还能享有这种静默我的老爷
						[02:27.11]这烂摊就不会收场
						 
						[03:19.72]旋转 跳跃吧
						[03:25.43]他感到连晚风也在头痛
						[03:31.35]狗娘养的帕特里克满脑子
						[03:34.49]关于体态的滑翔机
						[03:37.35]他说过那些女人爱的男人爱萝莉
						[03:41.63]萝莉爱包包 包包爱货币
						 
						[03:49.35]他在高级堡垒的方阵里走出
						[03:55.31]带来大会的消息
						[04:01.30]在幼犬和地皮商的征程里
						[04:06.12]他是发达的肯定句
						[04:13.54]等他和他们
						[04:16.11]他们和所有人之间
						[04:20.11]都搞不来信任的时候
						[04:25.53]只有冬和她的姨妈
						[04:28.53]从没有熄灯的窗口
						[04:31.53]无声眺望
						 
						[04:43.43]这夜派对 就要散场
						 
						[05:28.54]幽暗的最高频道还在
						[05:34.53]为全城遮盖下一百年的昂贵谜底
						[05:40.53]他倚靠在令人害羞的礼品堆裡
						[05:46.24]冉冉睡去
						[05:52.53]幽暗的最高频道还在
						[05:57.95]为全城遮盖下一百年的昂贵谜底
						[06:04.47]他倚靠在令人害羞的礼品堆裡
						[06:10.18]冉冉睡去
						[06:16.75]幽暗的最高频道还在
						[06:22.47]为全城遮盖下一百年的昂贵谜底
						[06:28.42]他倚靠在令人害羞的礼品堆裡
						[06:34.13]冉冉睡去
	    			</pre>
	        </div>
	        </div>
	        <input type="hidden" id="mp3_rownum"/>
	        <!--  <button type="button" class="btn btn-primary" onclick="changeMp3('0');">上一首</button>
	        <button type="button" class="btn btn-primary" onclick="changeMp3('1');">下一首</button>
			-->
		</div>
		<div class="span6">
			<form class="form-search">
				<input class="input-medium search-query" type="text" /> <button type="submit" class="btn">查找</button>&nbsp;&nbsp;&nbsp;
				<a id="modal-writeDynamics" href="#modal-container-writeDynamics" role="button" class="btn btn-primary" data-toggle="modal">写动态</a>&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-info" onclick="writeArticle();">写文章</button>
			</form>
			<div id="dynamics">			
			</div>
              	<div class="pagination pagination-larger pagination-centered" id="dynamics-page">
				<!-- 分页 -->
				</div>
		</div>
		<div class="span3">			
			<h3 class="text-left">
				更新日志&nbsp;&nbsp;&nbsp;<a id="modal-writeLog" href="#modal-container-writeLog" role="button" class="btn btn-primary" data-toggle="modal">写日志</a>
			</h3>
			<table class="table">
				<thead>
					<tr>
						<th>
							更新序号
						</th>
						<th>
							更新时间
						</th>
						<th>
							更新内容
						</th>
					</tr>
				</thead>
				<tbody id="log">
				</tbody>
			</table>
			<div class="pagination pagination-mini">
				<ul contenteditable="true">
					<li><a href="#">上一页</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">下一页</a></li>
				</ul>
			</div>			
            <ul class="nav nav-tabs" contenteditable="false">
            	<li class="active" id="message-li"><a onclick="queryMessage('1');" style=" cursor:pointer;">留言</a></li>
                <li id="advise-li"><a onclick="queryAdvise('1');" style=" cursor:pointer;">建议</a></li>
            </ul>
            <div id="message" style="display:block;">
            <!-- 留言 -->
            </div>
            <div id="advise" style="dispaly:none;">
            <!-- 建议 -->
            </div>
		</div>
	</div>
	<div class="row-fluid">
		<div id="modal-container-writeDynamics" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="myModalLabel">
						写新动态
					</h3>
				</div>
				<div class="modal-body">
					<form role="form">
					  <div class="form-group">
					    <textarea class="comments" rows="5" id="newDynamics"></textarea>
					  </div>
					</form>
				</div>
				<div class="modal-footer">
					 <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
					 <button type="button" class="btn btn-primary" onclick="writeDynamics();">发表</button>
				</div>
			</div>
			<div id="modal-container-writeLog" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="myModalLabel">
						写新更新日志
					</h3>
				</div>
				<div class="modal-body">
					<form role="form">
					  <div class="form-group">
					    <textarea class="comments" rows="5" id="newLog"></textarea>
					  </div>
					</form>
				</div>
				<div class="modal-footer">
					 <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
					 <button type="button" class="btn btn-primary" onclick="writeLog();">发表</button>
				</div>
			</div>
		<div class="span12">
			<p class="text-center">
				<strong>联系邮箱：</strong>hcf3377@163.com
			</p>
		</div>
	</div>
</div>
  </body>
  </html>