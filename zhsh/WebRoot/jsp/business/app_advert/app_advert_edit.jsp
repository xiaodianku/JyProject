<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		
		<meta charset="utf-8" />
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="renderer" content="webkit">
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
		<!--[if lt IE 9]><link rel="stylesheet" href="css/ace-ie.min.css" /><![endif]-->
		<!-- 下拉框 -->
		<link rel="stylesheet" href="css/chosen.css" />
		
		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	
	//保存
	function save(){
			if($("#ranking").val()==""){
			$("#ranking").tips({
				side:3,
	            msg:'请输入排序位置',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ranking").focus();
			return false;
		}
		if($("#image_name").val()==""){
			$("#image_name").tips({
				side:3,
	            msg:'请输入图片名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#image_name").focus();
			return false;
		}
		if($("#image_url").val()==""){
			$("#image_url").tips({
				side:3,
	            msg:'请输入图片url',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#image_url").focus();
			return false;
		}
		if($("#hyperlink_url").val()==""){
			$("#hyperlink_url").tips({
				side:3,
	            msg:'请输入跳转至url',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#hyperlink_url").focus();
			return false;
		}
		if($("#starttime").val()==""){
			$("#starttime").tips({
				side:3,
	            msg:'请输入有效时间：开始',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#starttime").focus();
			return false;
		}
		if($("#endtime").val()==""){
			$("#endtime").tips({
				side:3,
	            msg:'请输入有效时间：截止',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#endtime").focus();
			return false;
		}
		if($("#advert-type").val()==""){
			$("#advert-type").tips({
				side:3,
	            msg:'请输入广告类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#advert-type").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="app_advert/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="app_advert_id" id="app_advert_id" value="${pd.app_advert_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="ranking" id="ranking" value="${pd.ranking}" maxlength="32" placeholder="这里输入排序位置" title="排序位置"/></td>
			</tr>
			<tr>
				<td><input type="text" name="image_name" id="image_name" value="${pd.image_name}" maxlength="32" placeholder="这里输入图片名称" title="图片名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="image_url" id="image_url" value="${pd.image_url}" maxlength="32" placeholder="这里输入图片url" title="图片url"/></td>
			</tr>
			<tr>
				<td><input type="text" name="hyperlink_url" id="hyperlink_url" value="${pd.hyperlink_url}" maxlength="32" placeholder="这里输入跳转至url" title="跳转至url"/></td>
			</tr>
			<tr>
				<td><input type="text" name="starttime" id="starttime" value="${pd.starttime}" maxlength="32" placeholder="这里输入有效时间：开始" title="有效时间：开始"/></td>
			</tr>
			<tr>
				<td><input type="text" name="endtime" id="endtime" value="${pd.endtime}" maxlength="32" placeholder="这里输入有效时间：截止" title="有效时间：截止"/></td>
			</tr>
			<tr>
				<td><input type="text" name="advert-type" id="advert-type" value="${pd.advert-type}" maxlength="32" placeholder="这里输入广告类型" title="广告类型"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(window.parent.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>