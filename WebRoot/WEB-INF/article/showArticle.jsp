<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.huangcf.dynamics.vo.DynamicsVO"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
DynamicsVO vo = (DynamicsVO)session.getAttribute("dynamics");
%>
<html>
  <head>
  	<link rel="shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  	<meta http-equiv="charset" content="utf-8">
  	<title>文章</title>
  	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/ueditor.parse.js"></script>
  	<script src="<%=request.getContextPath() %>/bootstrap-3.3.5-dist/js/jquery-1.9.1.js"></script>
 	<style type="text/css">
 		.read-more {
	font-family: 'Ubuntu', sans-serif;
	font-weight: 400;
	word-spacing: 1px;
	letter-spacing: 0.01em;
	text-align: left;
	margin-top: 20px;
}

.cl-effect-14 a {
	padding: 0 20px;
	height: 45px;
	line-height: 45px;
	position: relative;
	display: inline-block;
	margin: 15px 25px;
	letter-spacing: 1px;
	font-weight: 400;
	text-shadow: 0 0 1px rgba(0,0,0,0.3);
}

.cl-effect-14 a::before,
.cl-effect-14 a::after {
	position: absolute;
	width: 45px;
	height: 1px;
	background: #C3C3C3;
	content: '';
	-webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
	transition: all 0.3s;
	pointer-events: none;
}

.cl-effect-14 a::before {
	top: 0;
	left: 0;
	-webkit-transform: rotate(90deg);
	-moz-transform: rotate(90deg);
	transform: rotate(90deg);
	-webkit-transform-origin: 0 0;
	-moz-transform-origin: 0 0;
	transform-origin: 0 0;
}

.cl-effect-14 a::after {
	right: 0;
	bottom: 0;
	-webkit-transform: rotate(90deg);
	-moz-transform: rotate(90deg);
	transform: rotate(90deg);
	-webkit-transform-origin: 100% 0;
	-moz-transform-origin: 100% 0;
	transform-origin: 100% 0;
}

.cl-effect-14 a:hover::before,
.cl-effect-14 a:hover::after,
.cl-effect-14 a:focus::before,
.cl-effect-14 a:focus::after {
	background: #000;
}

.cl-effect-14 a:hover::before,
.cl-effect-14 a:focus::before {
	left: 50%;
	-webkit-transform: rotate(0deg) translateX(-50%);
	-moz-transform: rotate(0deg) translateX(-50%);
	transform: rotate(0deg) translateX(-50%);
}

.cl-effect-14 a:hover::after,
.cl-effect-14 a:focus::after {
	right: 50%;
	-webkit-transform: rotate(0deg) translateX(50%);
	-moz-transform: rotate(0deg) translateX(50%);
	transform: rotate(0deg) translateX(50%);
}
#title:hover{ transform:scale(1.5);-webkit-transform: scale(1.5); }
#title{ transition: 0.5s all; } 
.enroll{
		color:#1E90FF;
		text-decoration:none;
		font-family: 'Love Ya Like A Sister', cursive;
		font-weight:bold;
		align:center;
	}
 	</style>
 	<script type="text/javascript">
 		$(function(){
	  	 	uParse('.content',{
			    rootPath: '<%=request.getContextPath() %>/ueditor', 
			    liiconpath: 'http://bs.baidu.com/listicon/', //自定义列表标号图片的地址，默认是这个地址
			    listDefaultPaddingLeft: '20' //自定义列表标号与文字的横向间距
  	 		});
  	 	});
 	</script>
 </head>
 <body>
 	<canvas id="canv" style="position:absolute;z-index:-1;"></canvas> 
	<script>
	window.requestAnimFrame = (function() {
	  return window.requestAnimationFrame ||
	    window.webkitRequestAnimationFrame ||
	    window.mozRequestAnimationFrame ||
	    window.oRequestAnimationFrame ||
	    window.msRequestAnimationFrame ||
	    function(callback) {
	      window.setTimeout(callback, 1000 / 60);
	    };
	})();
	var c = document.getElementById('canv'),
	    $ = c.getContext('2d'),
	    w = c.width = window.innerWidth,
	    h = c.height = window.innerHeight,
	    arr = [],
	    u = 0;
	    o = 0, 
	
	$.fillStyle = '#FEFAE6';
	$.fillRect(0,0,w,h);
	$.globalCompositeOperation = "source-over";
	var inv = function() {
	  $.restore();
	  $.fillStyle = "#" + (o ? "FEFAE6" : "D1EEEE");
	  $.fillRect(0, 0, w, h);
	  $.fillStyle = "#" + (o ? "D1EEEE" : "FEFAE6");
	  $.save()
	};
	window.addEventListener("touchstart", function(e){
	  e.preventDefault();
	  o = !o;
	  inv()
	}, false);
	window.addEventListener("mousedown", function(e){
	    //o = !o;//不变换颜色了
	    //inv()
	}, false);
	window.addEventListener("keydown", function(keydn) {
	  if (keydn.keyCode == 32) {
	    o = !o;
	    inv()
	  }
	}, false);
	window.addEventListener('resize', function(){
	    c.width = window.innerWidth;
	    c.height = window.innerHeight;
	}, false);
	var ready = function() {
	    arr = [];
	    for (var i = 0; i < 20; i++) {   
	        set();
	    }
	}
	var set = function() {
	    arr.push({
	        x1: w,
	        y1: h,
	        _x1: w - Math.random() * w,
	        _y1: h - Math.random() * h,
	        _x2: w - Math.random() * w,
	        _y2: h - Math.random() * h,
	        x2: -w + Math.random() * w,
	        y2: -h + Math.random() * h,
	        rot: Math.random() * 180,
	        a1: Math.random() * 10,
	        a2: Math.random() * 10,
	        a3: Math.random() * 10
	    });
	}
	var pretty = function() {
	     u-=.2;
	    for (var i in arr) {
	        var b = arr[i];
	        b._x1 *= Math.sin(b.a1 -= 0.001);
	        b._y1 *= Math.sin(b.a1);
	        b._x2 -= Math.sin(b.a2 += 0.001);
	        b._y1 += Math.sin(b.a2);
	        b.x1 -= Math.sin(b.a3 += 0.001);
	        b.y1 += Math.sin(b.a3);
	        b.x2 -= Math.sin(b.a3 -= 0.001);
	        b.y2 += Math.sin(b.a3);
	        $.save();
	        $.globalAlpha = 0.03;
	        $.beginPath();
	        $.strokeStyle = 'hsla('+ u + ', 85%, 60%, .7)';
	        $.moveTo(b.x1, b.y1);
	        $.bezierCurveTo(b._x1, b._y1, b._x2, b._y2, b.x2, b.y2);
	        $.stroke();
	        $.restore();
	    }
	    window.requestAnimFrame(pretty);
	}
	ready();
	pretty();
	setTimeout(function() { 
	  var info= document.getElementById('info');
	  info.style.right = '-500px';
	}, 5500);
	</script>
	 
	<div class="read-more cl-effect-14">
		<a class="more-link" style="cursor:pointer;" onclick="location.href='javascript:history.go(-1);'"> <span>←</span>Go Back</a>
	</div>
		<div align="center" id="title" style="font-family:Verdana,Geneva,sans-serif;font-size:30px;margin:0pxautoautoauto;color:#36322b;"><span align="center"  style="cursor:pointer;"><%=vo.getTitle() %></span></div>
	 	<div id="content">
	    <p><%=vo.getContent() %></p>
	</div>
	<div align="center"><span class="enroll">背景不会变化时按空格键</span></div>
 </body>
 </html>