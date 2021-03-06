<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>积分充值</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/pcstore/zhxx_jfzc.css">
	<style type="text/css">
	li{
		margin: 8px;
	}
	body{
		height:98%;	
	}
	</style>
	<script src="js/jquery-1.8.0.min.js"></script>
	<script src="js/jquery.qrcode.min.js"></script>
	<script type="text/javascript">
        if("${ispay}" != null && "${ispay}" != "" && "${ispay}" == "1"){
        	window.top.location="<%=basePath%>storepc/goStore.do";
        }
    </script>
</head>
<body>
<c:if test="${storeqx.look eq '1'}">
	<ul style="line-height:2.5; position: relative;">
		<li>
			<span>可用积分</span>：
			<span>${pd.now_wealth}</span>
		</li>
		<li>
			<span>冻结积分</span>：
			<span>${pd.tixian_money }</span>
		</li>
		<li>
			<span>充值现金</span>：
			<input type="text" class="inp-l" id="amount"  onkeyup="value=value.replace(/[^\.\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\.\d]/g,''))">
		</li>
		<li>
			<span style="vertical-align: top;" >支付方式：</span>
			<span class="act_box"   onclick="payway('alipay_pc_direct','1')">
				<img src="img/apay.png" alt="">
				<img src="img/active.png" alt="" class="act_img">
			</span>
			<span class="act_box"   onclick="payway('wx_pub_qr','2')">
				<img src="img/wechatpay.png" alt="">
				<img src="img/active.png" alt="" class="act_img">
			</span>
		</li>

		<li class="wx_ewm" style="display:none">
			<div class="img_box" id="erweimapay">
			
 				
			</div>
			<div class="ewm_title">
				30秒后将会自动刷新页面，请尽快支付
			</div>
		</li>
		<c:if test="${storeqx.add eq '1'}">
			<li style="text-align:left;">
				<span class="anniu-l" style="margin:20px 0 0 80px;" onclick="wap_pay()">确定</span>
				<span class="anniu-l" style="margin:20px 0 0 80px;" onclick="shuaxin()">刷新</span>
			</li>
		</c:if>
	</ul>
</c:if>
</body>
<script src="js/ping/pingpp.js" type="text/javascript"></script>
<script type="text/javascript">
		//刷新
        function shuaxin(){
        	window.location.href="<%=basePath%>storepc_wealth/list.do?store_id=${storepd.store_id}"; 
        }
    	
    	//更新支付方式
    	var channel="alipay_pc_direct";
    	function payway(value,type){
    		channel=value;
    	}
    	
      	//ping++支付
     	function wap_pay() {
      		if($("#amount").val() == "" || $("#amount").val() =="0"){
      			alert("金额不能为空");
      			return;
      		}
      		//清除定时器
      		if(window.t != null ){
      			clearInterval(t);
      		}
      		var url='<%=basePath%>storepc/goStore.do';
      		//获取charge
    		$.ajax({
				type:"post",
					url:"storepc_pay/store_PartyCz.do",
					data:"in_jiqi=4&money="+$("#amount").val()+"&pay_way="+channel+"&store_id="+"${storepd.store_id}"+"&store_operator_id="+"${storepd.oprator_id}"+"&url="+url,
					dataType:"json",
					success:function(data){
						var charge=data.data;
						if(charge == null || charge == ""){
							alert("支付渠道未开通");
						}else{
							if(channel == "alipay_qr"){
								var credential=charge.credential;
								var alipay_qr=credential.alipay_qr;
								$(".wx_ewm").show();
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ width: 150, height: 150,  text: alipay_qr }); 
 							}else if(channel == "wx_pub_qr" ){
 								var credential=charge.credential;
								var wx_pub_qr =credential.wx_pub_qr ;
								$(".wx_ewm").show();
								$("#erweimapay").empty();
								//生成二维码：商家ID以及zhuohao.生成的二维码下载，图片尺寸为5*6CM；
		 				        jQuery($("#erweimapay")).qrcode({ text: wx_pub_qr }); 
		 				        $("#erweimapay").append("<span>微信支付二维码</span>");
								//设置定时器
 								var time=30;
 	 		       				window.t=setInterval(function() {
	 			       				time--;
 	 			       				if(time == 0){
	 			       					shuaxin();
	 			       				}else{
	 			       					$(".ewm_title").html(time+"秒后将会自动刷新页面，请尽快支付");
	 			       				}
 	 			       			},1000);
  							}else if(channel == "alipay_pc_direct"  ||  channel == "alipay_wap"  || channel == "wx_pub"){
								//支付
								pingpp.createPayment(charge, function(result, err){
								    console.log(result);
								    console.log(err.msg);
								    console.log(err.extra);
								    if (result == "success") {
								    	alert("success");
								        // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
								    } else if (result == "fail") {
								    	alert("支付失败fail");
								        // charge 不正确或者微信公众账号支付失败时会在此处返回
								    } else if (result == "cancel") {
								    	alert("cancel");
								        // 微信公众账号支付取消支付
								    }
								}); 
							}
 						}
   					}
			});  
          }
    	
   
	var box=$(".act_box")
	box.click(function(e){
		box.css({"border":"1px solid #999"});
		$(".act_img").css({"display":"none"});
		$(".act_box").attr("act","0");
		$(e.target).parent().attr("act","1");
 		if ($(box[1]).attr("act") == "0") {
 			$(box[0]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[0]).css({"display":"block"})
		}else{
			$(box[1]).css({"border":"1px solid rgb(255,108,0)"})
			$($(".act_img")[1]).css({"display":"block"})
		}
	})
    $(".act_img")[0].click();


</script>
</html>