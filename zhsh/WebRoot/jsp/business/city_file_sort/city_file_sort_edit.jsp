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
			if($("#city_file_sort_id").val()==""){
			$("#city_file_sort_id").tips({
				side:3,
	            msg:'请输入唯一标示id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#city_file_sort_id").focus();
			return false;
		}
		if($("#sort_name").val()==""){
			$("#sort_name").tips({
				side:3,
	            msg:'请输入分类名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sort_name").focus();
			return false;
		}
		if($("#sort_imageurl").val()==""){
			$("#sort_imageurl").tips({
				side:3,
	            msg:'请输入分类图片地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sort_imageurl").focus();
			return false;
		}
		if($("#sort_parent_id").val()==""){
			$("#sort_parent_id").tips({
				side:3,
	            msg:'请输入父类id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sort_parent_id").focus();
			return false;
		}
		if($("#open_statestatus").val()==""){
			$("#open_statestatus").tips({
				side:3,
	            msg:'请输入开启状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#open_statestatus").focus();
			return false;
		}
		if($("#sequence").val()==""){
			$("#sequence").tips({
				side:3,
	            msg:'请输入排序',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sequence").focus();
			return false;
		}
		if($("#city_file_id").val()==""){
			$("#city_file_id").tips({
				side:3,
	            msg:'请输入城市档案id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#city_file_id").focus();
			return false;
		}
		if($("#sort_type").val()==""){
			$("#sort_type").tips({
				side:3,
	            msg:'请输入所属等级',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sort_type").focus();
			return false;
		}
		if($("#createdate").val()==""){
			$("#createdate").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#createdate").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="city_file_sort/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="city_file_sort_id" id="city_file_sort_id" value="${pd.city_file_sort_id}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="city_file_sort_id" id="city_file_sort_id" value="${pd.city_file_sort_id}" maxlength="32" placeholder="这里输入唯一标示id" title="唯一标示id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sort_name" id="sort_name" value="${pd.sort_name}" maxlength="32" placeholder="这里输入分类名称" title="分类名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sort_imageurl" id="sort_imageurl" value="${pd.sort_imageurl}" maxlength="32" placeholder="这里输入分类图片地址" title="分类图片地址"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sort_parent_id" id="sort_parent_id" value="${pd.sort_parent_id}" maxlength="32" placeholder="这里输入父类id" title="父类id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="open_statestatus" id="open_statestatus" value="${pd.open_statestatus}" maxlength="32" placeholder="这里输入开启状态" title="开启状态"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sequence" id="sequence" value="${pd.sequence}" maxlength="32" placeholder="这里输入排序" title="排序"/></td>
			</tr>
			<tr>
				<td><input type="text" name="city_file_id" id="city_file_id" value="${pd.city_file_id}" maxlength="32" placeholder="这里输入城市档案id" title="城市档案id"/></td>
			</tr>
			<tr>
				<td><input type="text" name="sort_type" id="sort_type" value="${pd.sort_type}" maxlength="32" placeholder="这里输入所属等级" title="所属等级"/></td>
			</tr>
			<tr>
				<td><input type="text" name="createdate" id="createdate" value="${pd.createdate}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/></td>
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