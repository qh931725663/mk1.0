$(function(){
	//获取地址栏order_id
	function getUrlParam(name){
		var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r != null) return unescape(r[2]);
		return null;
	}
	var order_id = getUrlParam("order_id");
	$(".haha").val(order_id);
	/*加载信息*/
	function checkInfo(){
		$.ajax({
			type:"GET",
			url:"../../../hypertension-app/getCheckInfo.action",
			dataType: 'json',
			data: {order_id:order_id},
			success: function(response){
				console.log(response);
				if(response.flag){
					var shuju = response.data.documentInfo;
					var num = '080101';//降压
					var num2 = '080102';//降糖
					var num3 = '080103';//降脂
					var num4 = '080104';//其他药品
					function getNews(num,classname){
						if(shuju[num][0][num]=='0'){
							$(classname).find('input').removeClass('bitian');
							$(classname).find('select').removeClass('noboeder');
						}else{
							$(classname).css('display','block');
						}
					}
					getNews(num,".ya");
					getNews(num2,".tang");
					getNews(num3,".zhi");
					getNews(num4,".qita");
					//文本
					$(".bitian").each(function(){
						var self = $(this).attr('name');
						if(shuju[self]){
							$(this).val(shuju[self][0][self]);
						}
					});
					$(".yaopin").each(function(){
						var self = $(this).attr('data-name');
						if(shuju[self]){
							$(this).val(shuju[self][0][self]);
						}
					});
					//radio
					$(".rd").each(function(){
						var name = $(this).attr('name');
						
						if(shuju[name]){
							var val = shuju[name][0][name]
							if(val == $(this).val()){
								$(this).attr("checked","checked");
							}
							
						}
						
					});
					$(".ypr").each(function(){
						var name = $(this).attr('data-name');
						
						if(shuju[name]){
							var val = shuju[name][0][name]
							if(val == $(this).val()){
								$(this).attr("checked","checked");
							}
							
						}
						
					});
					//药品回选
					getYaopin(shuju,'080101','.ya');
					getYaopin(shuju,'080102','.tang');
					getYaopin(shuju,'080103','.zhi');
					getYaopin(shuju,'080104','.qita');
				}
				
			}
		});
	}
	function getYaopin(shuju,name,className){
		if(shuju[name][0][name]=='1'){
			$(className).css('display','block');
		}
	}
	checkInfo();
	//返回
	$('.goback').on('click',function(){
		window.location.href = "HBPindex.html?order_id="+order_id;
	});
	//空腹血糖单位变化
	$(".mond_op").on('change',function(){
		console.log($(this).val());
		if($(this).val() == 1){
			$('.mol').attr('type','text');
			$('.mg').attr('type','hidden');
		}else{
			$('.mol').attr('type','hidden');
			$('.mg').attr('type','text');
		}
	});
	$('.mol').on('blur',function(){
		$('.mg').val(($(this).val())*18);
	});
	$(".mg").on('blur',function(){
		$('.mol').val(($(this).val())/18);
	});
	//点击下一步
	//下一步、上一步的函数封装
	function Change(n,m,i){
		$(n).removeClass(m);
		var spp = $(n).eq(i).addClass(m);
	}
	function layers(){
		layer.open({
		    content: '信息不完整！',
		    skin: 'msg',
		    time: 3 //3秒后自动关闭
		});
	}
	function getEat(name){
		$("input[name="+name+"]").on('click',function(){
//				$(this).parent().next('.checkBox').css('display','none');
			if($(this).val() == 1 ){
				$(this).parent().next('.checkBox').css('display','block');
				$(this).parent().next('.checkBox').find('input:text').addClass('bitian');
				$(this).parent().next('.checkBox').find('select').addClass('noboeder');
			}else{
				$(this).parent().next('.checkBox').css('display','none');
				$(this).parent().next('.checkBox').find('input:text').removeClass('bitian');
				$(this).parent().next('.checkBox').find('select').removeClass('noboeder');
			}
		});
	}
	getEat('080101');
	getEat('080102');
	getEat('080103');
	getEat('080104');
	$(".next").on('click',function(){
		$(".basicInfo").css('display','none');
		var temp = false;
		var see = $(this).parent().parent();
		var index = $(this).parent().parent().next().index();
		var bitian = $(this).parent().parent().find('.bitian');
		var flag1 = true;
		if(bitian.length > 0){
			bitian.each(function(){
				var val = $(this).val();
				if(!val){
					see.css('display','block');
					temp = true;
					flag1 = false;
					//
					$(this).parent().parent().find(".info_left").css({"color":"#CD412F"});
				}else{
					$(this).parent().parent().find(".info_left").css({"color":"#333"});
				}
			});
			if(temp){
				layers();
			}
		}
		//校验单选框是否选中
		function inputRd(name){
			 var val=$("input:radio[name="+name+"]:checked");
			 if(val.val() == null){
				 see.css('display','block');
				 $("input[name="+name+"]").parent().prev().css("color","#CD412F");
				 temp = true;
				 flag = false;
			 }else{
				 $("input[name="+name+"]").parent().prev().css("color","#333");
			 }
			 if(temp){
				 layers();
			}
		}
		inputRd('071101');
		inputRd('071201');
		inputRd('071401');
		if(flag1){
			
			see.next().css('display','block');
			Change('.dot','on',index);
			Change('.txt','onFont',index);
		}
	})
	//点击上一步
	$(".back").on('click',function(){
		$(".basicInfo").css('display','none');
		$(this).parent().parent().prev().css('display','block');
		var index = $(this).parent().parent().prev().index();
		Change('.dot','on',index);
		Change('.txt','onFont',index);
	})
	//既往史单选
	$(".both").on('change',function(){
		
		if($(this).attr("checked")){
			console.log("no");
		}else{
			console.log("ok");
		}
	});
	/*保存，提交信息*/
	$(".tijiao").on('click',function(){
		var temp = false;
		var see = $(this).parent().parent();
		var index = $(this).parent().parent().next().index();
		var bitian = $(this).parent().parent().find('.bitian');
		var flag1 = true;
		if(bitian.length > 0){
			bitian.each(function(){
				var val = $(this).val();
				if(!val){
					see.css('display','block');
					temp = true;
					flag1 = false;
					//
					$(this).parent().parent().find(".info_left").css({"color":"#CD412F"});
				}else{
					$(this).parent().parent().find(".info_left").css({"color":"#333"});
				}
			});
			if(temp){
				layers();
			}
		}
		function inputRd(name){
			 var val=$("input:radio[name="+name+"]:checked");
			 if(val.val() == null){
				 see.css('display','block');
				 $("input[name="+name+"]").parent().prev().css("color","#CD412F");
				 temp = true;
				 flag = false;
			 }else{
				 $("input[name="+name+"]").parent().prev().css("color","#333");
			 }
			 if(temp){
				 layers();
			}
		}
		inputRd('080101');
		inputRd('080102');
		inputRd('080103');
		inputRd('080104');
		if(flag1){
			var dataList = $.param({order_id: order_id})+'&'+serializeNotNull($("#checkForm").serialize());
			$.ajax({
				type:"GET",
				url:"../../../hypertension-app/updateCheckInfo.action",
				dataType : 'json',
				data: dataList,
				success: function(data){
					if(data.flag){
						layer.open({
						    content: '保存成功！',
						    skin: 'msg',
						    time: 2 //2秒后自动关闭
						});
						$("#checkForm").get(0).reset();
						setTimeout(function(){//两秒后跳转  
							window.location.href = "HBPindex.html?order_id="+order_id;
                         },1000);
					}else{
						layer.open({
						    content: '保存失败！',
						    skin: 'msg',
						    time: 2 //2秒后自动关闭
						});
					}
				}
			});
		}
	});
	//序列化排除空值
	function serializeNotNull(serStr){
	    return serStr.split("&").filter(function(str){return !str.endsWith("=")}).join("&");
	}
		
	
});
