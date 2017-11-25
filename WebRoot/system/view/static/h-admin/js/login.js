$(function(){
	/*使用enter回车登录*/
	$(document).keydown(function(e){
		if($(".bac").length==0)
		{
			if(!e) e = window.event;
			if((e.keyCode || e.which) == 13){
				var obtnLogin=$("#btn-login");
				obtnLogin.focus();
			}
		}
	});
//登录
	var loginUname=null;
	var loginPwd=null;
	$("#btn-login").click(function(){
		var mes=$("#lay-mesg");
		if($("#user_phone").val()==""){
			mes.html("请输入用户名！");
			$("#user_phone").focus();
		}else if($("#user_pwd").val()==""){
			mes.html("请输入密码！");
			$("#user_pwd").focus();
		}else{
		var data1=$("#login_form").serialize();
			$.ajax({
				type:"POST",
				url:"../user-platform/login.action",
				data:data1,
				success:function(arr){
					if(arr.flag==false){	
						mes.html(arr.errmsg);
					}else{
						var userId=arr.data.user_id;
						loginUname=$("[name='user_phone']").val();
						//loginPwd=$("[name='user_pwd']").val();
						//TODO 将用户信息保存到localStorage中
						window.localStorage.setItem("loginUname",loginUname);
						//window.localStorage.setItem("loginPwd",loginPwd);
						window.localStorage.setItem("userId",userId);
						//
						window.sessionStorage.setItem("userID", userId);
						window.location.href="index.html";
					}
				}
			})
		}
	})

});
	