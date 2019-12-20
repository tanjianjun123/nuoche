<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
		<title>115挪车</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<!--标准mui.css-->
		<link rel="stylesheet" href="${pageContext.request.contextPath }/weixin/css/mui.min.css">
		
	</head>
	<link href="${pageContext.request.contextPath }/weixin/css/css.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/weixin/css/font-awesome.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/jquery.form.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath }/weixin/js/popwin.js"></script>
<body>
	<header class="mui-bar mui-bar-nav">
			<h1 class="mui-title">绑定汽车</h1>
		</header>
	<div class="mui-content">
	
		<div class="mui-card">
		
			<div class="mui-collapse-content">
				<form class="mui-input-group" id="formgo">
					<div class="mui-input-row">
						<label style="width: 30%;">编&nbsp&nbsp&nbsp&nbsp号</label> <input type="number"  readonly="readonly"
							style="width: 70%;font-size: 14px;   " id="bianhao" name="bianhao"
							placeholder="" value="${user.id }">
					</div>
					<div class="mui-input-row">
						<label style="width: 30%;">车牌号</label> <input type="text"
							style="width: 70%;font-size: 14px;   " id="chepaihao" name="chepaihao"
							placeholder="请输入车牌号（如京A00001）">
					</div>
					<div class="mui-input-row">
						<label style="width: 30%;">姓&nbsp&nbsp&nbsp&nbsp名</label> <input
							type="text" style="width: 70%;font-size: 14px;" class="mui-input-clear"
							name="name" placeholder="请输入姓名">
					</div>
					<div class="mui-input-row">
						<label style="width: 30%;">电&nbsp&nbsp&nbsp&nbsp话</label> <input
							type="tel" id="tel" style="width: 70%;font-size: 14px;" class="mui-input-clear"
							name="tel" placeholder="请输入电话号码">
					</div>


					

					<div class="mui-button-row" style="height: 60px;">
						<button type="button"
							class="mui-btn mui-btn-primary mui-btn-block" onclick="go()">绑定并支付</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/lib/lib/jquery/1.9.1/jquery.min.js"></script>
<script src="./js/mui.min.js"></script>
<script  type="text/javascript">
	function go() {
		var re1 = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
		if (window.document.getElementById("chepaihao").value.search(re1) == -1) {
			alert("请正确输入车牌号");
			window.document.getElementById("chepaihao").value = "";
			return false;
		}
		
		var re2 = /^134[0-8]\d{7}$|^13[^4]\d{8}$|^14[5-9]\d{8}$|^15[^4]\d{8}$|^16[6]\d{8}$|^17[0-8]\d{8}$|^18[\d]{9}$|^19[8,9]\d{8}$ /;
		if (window.document.getElementById("tel").value.search(re2) == -1) {
			alert("请正确电话");
			window.document.getElementById("tel").value = "";
			return false;
		}
		//$("#formgo").submit();
		var form = document.getElementById("formgo");
		var chepaihao = form.chepaihao.value;
		var name = form.name.value;
		var tel = form.tel.value;
		var p = $(form).serialize();
		showPopWin(0,"正在处理，请稍候...");
		//window.location.href="${pageContext.request.contextPath}/applyforqrcode.do?method=submitaddr&name="+name+"&tel="+tel+"&address="+address;
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/weixinbangding.do?p=bangding_buy&qrid=${qrid}&uweixinhao=${uweixinhao}",
			data:{name:name,tel:tel,chepaihao:chepaihao},	
			success:function(data){
				if(data =="yes")
				{
					showPopWin(0,"订单已生成，正前往支付页面，请稍候...");
					window.location.href="${pageContext.request.contextPath}/applyforqrcode.do?method=showorder";			
				}

			},
			error:function(){
				showPopWin(1,"请求提交失败，请稍后重试！");
			}
		});

	}
	
</script>

<!-- 确认 弹窗 -->
	<div class="pop" id="checkWin" style="display: none;">
		<div class="popC">
			<div class="pop_tit">操作确认</div>
			<div class="popCC"><strong id="checkMsgTip"></strong></div>
			<div class="popBut">
				<a onclick="hidePopWin()">取 消</a>
				<a id="btnCheck" class="butRed">确 认</a>
			</div>
		</div>
	</div>

	<!-- 提示 弹窗 -->
	<div class="pop" id="msgWin" style="display: none;">
		<div class="popC">
			<div class="pop_tit">操作提示</div>
			<div class="popCC"><strong id="msgTip">提示语</strong></div>
			<div class="popBut">
				<a onclick="hidePopWin()" class="butRed">确 定</a>
			</div>
		</div>
	</div>

	<!-- loading 弹窗 -->
	<div class="pop" id="loadingWin" style="display: none;">
		<div class="popC">
			<div class="popCC"><strong id="loadingMsg"></strong></div>	
		</div>
	</div>
</html>