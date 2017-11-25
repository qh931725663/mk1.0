<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'RestfullTest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery.min.js"></script> 
<script type="text/javascript">
function post(){
  $.ajax({
		url:"blood_pressure/insert.action",
		type:"post",
		dataType:'data',
		contentType:'application/json;charset=utf-8',
		data:JSON.stringify({data:{HighPressure:10,LowPressure:80,PulseRate:60}}),
		success:function(result){
		   console.log(result);
		   alert(result);
		   $('#content').val(result);		
	
		}
	});
}

function get(){
$.ajax({
		url:"blood_pressure/queryByTime.action",
		type:"post",
				dataType:'data',
		
		data:{user_id:100,upTime:'2017-4-24 5:00:00',downTime:'2017-4-24 22:00:00'},
		success:function(result){
		   console.log(result);
		   $('#content').val(result);		
	
		}
	});
}

function del(){
$.ajax({
		url:"restful/delete.action",
		type:"delete",
		success:function(result){	
		   $('#content').val(result);		
		}
	});
}

function put(){
$.ajax({
		url:"restful/put.action",
		type:"put",
		success:function(result){
		   $('#content').val(result);		
		}
	});
}
</script>
</head>
  
  <body>
  <form action="#">
  <input type="button" value="post" onclick="post()"/><br>
  <input type="button" value="get" onclick="get()"/><br>
  <input type="button" value="delete" onclick="del()"/><br>
  <input type="button" value="put" onclick="put()"/><br>
  <input type="text" id="content"/><br>
  </form>
  </body>
</html>
