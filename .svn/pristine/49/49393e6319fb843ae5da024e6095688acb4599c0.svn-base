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
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.js"></script> 
<script type="text/javascript">
function post(){

  $.ajax({
		url:"smoking/plan.action",
		type:"post",
		//dataType:'data',
		//contentType:'application/json;charset=utf-8',
		//data:JSON.stringify({data:{systolic_blood_pressure:10,diastolic_blood_pressure:80,pulse:60}}),
		data:{
		user_id:365,
		quit_smoking_plan_reason:99,
		quit_smoking_plan_target:8,
		quit_smoking_plan_day:9,
		quit_smoking_plan_number:6,
		create_date:new Date(),
		//quit_smoking_plan_reason_text:'ender',
		//quit_smoking_plan_name:'ender'
		},
		success:function(result){
		   console.log(result);
		   $('#content').val(result);		
	
		}
	});
}

function get(){
$.ajax({
		url:"smoking/finduser.action",
		type:"post",
		data:{user_id:334605},	
		dataType:'json',
		success:function(result){
		 console.log(result);
		   $('#content').val(result);		
	
		}
	});
}

function del(){
$.ajax({
		url:"smoking/baseInfo.action",
		type:"post",
	    dataType:'json',
	    data:{
user_id:1,
smoked_year:22,
cigarettes_per_day:5,
smoked_last_time:'1974/12/12',
no_smoke_plan:101,
smoking_situation:400007
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}

function put(){
$.ajax({
		url:"smoking/findBaseInfo.action",
		type:"post",
	    dataType:'json',
	    data:{
             user_id:37
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}
function selectAll(){
$.ajax({
		url:"smoking/selectAll.action",
		type:"post",
	    dataType:'json',
	    data:{
             user_id:2
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}
function signate(){
$.ajax({
		url:"smoking/signate.action",
		type:"post",
	    dataType:'json',
	    data:{
             user_id:361
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}
function isSignate(){
$.ajax({
		url:"smoking/isSignate.action",
		type:"post",
	    dataType:'json',
	    data:{
             user_id:1
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}
function updatePlan(){
$.ajax({
		url:"smoking/updatePlan.action",
		type:"post",
	    dataType:'json',
	    data:{
             smoking_plan_order_id:29,
             
             
             update_reason_text:'wowowowowo',
             
             quit_smoking_plan_number:12,
             create_date:new Date()
             
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}
function findSmokingUser(){
$.ajax({
		url:"smoking/findSmokingUser.action",
		type:"post",
	    dataType:'json',
	    data:{

//	  dateAfter:'2017-5-12',
//	  dateBefore:'2017-5-14',
 //     user_name:'蒋永海',
//      user_card:'111',
//      user_phone:'22',
//      quit_smoking_plan_end:101,
      pageno:1
 
  
             
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});
}

function getQuitSmokingPlan(){
$.ajax({
		url:"smoking/getQuitSmokingPlan.action",
		type:"post",
	    dataType:'json',
	    data:{
                        user_id:4 
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});

}
function selectUserInfo(){
$.ajax({
		url:"smoking/selectUserInfo.action",
		type:"post",
	    dataType:'json',
	    data:{
                        user_id:585
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});

}
function selectAllPlanP(){
$.ajax({
		url:"smoking/selectAllPlanP.action",
		type:"post",
	    dataType:'json',
	    data:{
                        user_id:469,
                        pageno:1
	    },
		success:function(result){
		console.log(result);
		   $('#content').val(result);			
		}
	});

}
</script>
</head>
  
  <body>
  <form action="#">
  <input type="button" value="plan" onclick="post()"/><br>
  <input type="button" value="finduser" onclick="get()"/><br>
  <input type="button" value="baseInfo" onclick="del()"/><br>
  <input type="button" value="findBaseInfo" onclick="put()"/><br>
  <input type="button" value="signate" onclick="signate()"/><br>
  <input type="button" value="isSignate" onclick="isSignate()"/><br>
  <input type="button" value="updatePlan" onclick="updatePlan()"/><br>
    <input type="button" value="getQuitSmokingPlan" onclick="getQuitSmokingPlan()"/><br>
  <input type="button" value="findSmokingUser" onclick="findSmokingUser()"/><br>
    <input type="button" value="selectAll" onclick="selectAll()"/><br>
    <input type="button" value="selectUserInfo" onclick="selectUserInfo()"/><br>
    <input type="button" value="selectAllPlanP" onclick="selectAllPlanP()"/><br>
  <input type="text" id="content"/><br>
  </form>
  </body>
</html>
