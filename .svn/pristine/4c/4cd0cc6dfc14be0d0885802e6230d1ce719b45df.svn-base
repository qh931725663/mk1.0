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
	console.log(order_id);
	//加载信息
	$(function(){
		$.ajax({
			type:"GET",
			url:"../../../hypertension-app/getWorkStationInfo.action",
			dataType: 'json',
			data : {order_id: order_id},
			success: function(data){
				if(data.flag){
					console.log(data);
					var news = data.data.documentInfo;
					//单选
					$(".rd").each(function(){
						var name = $(this).attr('name');
						
						if(news[name]){
							var val = news[name][0][name]
							if(val == $(this).val()){
								$(this).attr("checked","checked");
							}
							
						}
						
					});
					//文本
					var num = '090901';
					if(news[num]){
						$(".number").val(news[num][0][num]);
					}
				}
			}
		});
	});
	//返回
	$('.goback').on('click',function(){
		window.location.href = "HBPindex.html?order_id="+order_id;
	});
	//统计危险因素个数
	$(":radio").bind('change',function(){
		var num = $("input:checked[value=1]").size();
		$(".number").val(num);
	});
	//点击保存
	$(".tijiao").on('click',function(){
		var temp = false;
		var flag = true;
		//校验单选框是否选中
		function inputRd(name){
			 var val=$("input:radio[name="+name+"]:checked");
			 if(val.val() == null){
				 $("input[name="+name+"]").parent().prev().css("color","#CD412F");
				 temp = true;
				 flag = false;
			 }else{
				 $("input[name="+name+"]").parent().prev().css("color","#333");
			 }
			 if(temp){
				layer.open({
				    content: '信息不完整！',
				    skin: 'msg',
				    time: 3 //3秒后自动关闭
				});
			}
		}
		inputRd('090101');
		inputRd('090201');
		inputRd('090301');
		inputRd('090401');
		inputRd('090501');
		inputRd('090601');
		inputRd('090701');
		inputRd('090801');
		inputRd('risk');
		if($('.number').val() == ''){
			 $(".number").parent().prev().css("color","#CD412F");
			 temp = true;
			 flag = false;
			 if(temp){
				layer.open({
				    content: '信息不完整！',
				    skin: 'msg',
				    time: 3 //3秒后自动关闭
				});
			}
		}else{
			$(".number").parent().prev().css("color","#333");
		}
		if(flag){
			var dataList = $.param({order_id: order_id})+'&'+ $("#assessForm").serialize();
			$.ajax({
				type:"GET",
				url:"../../../hypertension-app/updateWorkStationInfo.action",
				dataType : 'json',
				data: dataList,
				success: function(data){
					console.log(data);
					if(data.flag){
						layer.open({
						    content: '保存成功！',
						    skin: 'msg',
						    time: 2 //2秒后自动关闭
						});
						$("#assessForm").get(0).reset();
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
	
});
