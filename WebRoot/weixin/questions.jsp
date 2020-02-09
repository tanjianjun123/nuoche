<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<!--<base href="/static_files/"/>-->

<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<!--<meta content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;" name="viewport">-->
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="utf-8">
<meta name="description" content="" /><!--网站描述-->
<meta name="keywords" content="" /><!--网站关键字-->
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0,maximum-scale=1.0"/>
<!--width - viewport的宽度 height - viewport的高度
initial-scale - 初始的缩放比例
minimum-scale - 允许用户缩放到的最小比例
maximum-scale - 允许用户缩放到的最大比例
user-scalable - 用户是否可以手动缩放-->
<link href="${pageContext.request.contextPath }/weixin/css/css.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/font-awesome.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/jquery.form.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/popwin.js"></script>

	<script src="${pageContext.request.contextPath }/weixin/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/weixin/js/mui.min.js"></script>
    
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
    </script>


<!--页面滚动插件-->
<script src="${pageContext.request.contextPath }/weixin/js/iscroll.js"></script>
         
<title>常见问题</title>


</head>
<body>



<div id="wrapper" style="top: 0;bottom:0">
	<ul>
		<!--<li><img src="images/pay_banner.jpg" style="width: 100%; display: block;"></li>-->
		
		<li>
			<ul class="fillList lineHeightA" style="margin-top: 0; border-top: none;">
				<c:forEach items="${questions }" var="list" >
					<li>
						<span class="fill_inA" style="width:auto;text-align:left;">${list.title}</span>
						<span class="fill_inBs" id="totalPrice" >	<img src="${pageContext.request.contextPath }/weixin/images/symbol.png" style="width: 15px;"/></span>
					</li>
				</c:forEach>	
			</ul>
		</li>
	</ul>
</div>

</body>

</html>

