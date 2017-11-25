$(function(){
	//获取地址栏order_id方法
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	var order_id=getUrlParam("order_id");
	$(".haha").val(order_id);
	console.log(order_id);
	//获取基本信息
	function getInfo(order_id){
		
		$.ajax({
			url:'../../../hypertension-app/getBasicInfo.action',
			type:"GET",
			dataType:'json',
			data:{order_id:order_id},
			success: function(list){
				console.log(list);
				if(list.flag){
					//个人信息
					console.log(order_id);
					var info = list.data.patientInfo;
					var other = list.data.documentInfo;
					$('.infocla').each(function(){
						var id = $(this).attr('data-id');
						if(info[id] || info[id] == 0){
							if(info[id] == '200002' || info[id] == '200001'){
								$(this).text(info[id]='200002'?'男':'女');
							}else{
								$(this).text(info[id]);
							}
						}
					});
					$(".hgt").val(info.user_height);
					$(".wgt").val(info.user_weight);
					$(".wst").val(info.user_waist);
					getBMI();
					//文本
					$(".bitian").each(function(){
						var self = $(this).attr('name');
						if(other[self]){
						
							$(this).val(other[self][0][self]);
							/*alert(other[self][0][self]);*/
						}
					});
					$(".addInp").each(function(){
						var self = $(this).attr('name');
						if(other[self]){
							$(this).val(other[self][0][self]);
						}
					});
					$(".addInpT").each(function(){
						var self = $(this).attr('name');
						var dada = $(this);
						$.each(other, function(i,m) {
							$.each(m,function(j,n){
								if(n[self]){
									dada.val(n[self]);
								}
							})
							
						});
					})
					//复选框
					$(".plusBox").each(function(){
						var checkbox = $(this).find(".magic").get(0);
						var box_name = $(checkbox).attr("data-name");
						var that = this;
						if(other[box_name]){
							$.each(other[box_name], function(i, n){
								var value = n[box_name];
								$(that).find(".magic").each(function(){
									if(value == $(this).val()){
										$(this).attr("checked","checked");
										var next = $(this).next().next('.datetime');
										var _name = next.attr('data-name');
										if(n[_name]){
											next.val(n[_name]);
										}
									}
								});
							});
						}
					});
					$('.ck').each(function(){
						var name = $(this).attr('data-name');
						var val = $(this).val();
						var that = $(this);
						if(other[name]){
							$.each(other[name],function(i,n){
								var shuju = n[name];
								if(shuju == val){
									that.attr("checked","checked");
								}
							})
						}
						
					});
					//单选
					$(".rd").each(function(){
						var name = $(this).attr('name');
						
						if(other[name]){
							var val = other[name][0][name]
							if(val == $(this).val()){
								$(this).attr("checked","checked");
							}
							
						}
						
					});
					//从不锻炼
					var n = '010101';
					var sports = $('.sports').siblings('li');
					if(other[n][0][n]=='4'){
						sports.css('display','none');
						sports.find('input').removeClass('bitian');
						sports.find('input').val('');
					}else{
						sports.css('display','block');
						sports.find('input').addClass('bitian');
					}
					//吸烟
					var m = '010401';
					var notshow = $('.smoked');
					if(other[m][0][m]== '1'){
						notshow.siblings('li').css('display','none');
						notshow.siblings('li').find('input').removeClass('bitian');
						notshow.siblings('li').find('input').val('');
					}else if(other[m][0][m]== '2'){
						notshow.siblings('.sago').css('display','block');
						notshow.siblings('.sago').find('input').addClass('bitian');
						notshow.siblings('.nnever').css('display','none');
						notshow.siblings('.nnever').find('input').val('');
						notshow.siblings('.nnever').find('input').removeClass('bitian');
					}else{
						notshow.siblings('.nnever').css('display','block');
						notshow.siblings('.nnever').find('input').addClass('bitian');
						notshow.siblings('.sago').css('display','none');
						notshow.siblings('.sago').find('input').val('');
						notshow.siblings('.sago').find('input').removeClass('bitian');
					}
					//饮酒
					var nm = '010501';
					var drink = $('.drinking').siblings('li');
					if(other[nm][0][nm]== '1'){
						drink.css('display','none');
						drink.find(':text').removeClass('bitian');
					}else{
						drink.css('display','block');
						drink.find(':text').addClass('bitian');
						drink.find(':text').val('');
					}
					//多少年的高血压、糖尿病
					var num0 = '040102';
					if(other[num0][0][num0]=='1'){
						var numb = '040103';
						var date = other[num0][0][numb].substring(0,4);
						var nowdate = new Date();
						$('.gaoxueya').val(nowdate.getFullYear()-date);
					}else{
						$(".gaoxueya").val(0);
						
					}
					if(other[num0][1][num0]=='2'){
						var numb = '040103';
						var date = other[num0][1][numb].substring(0,4);
						var nowdate = new Date();
						$('.tangniaobing').val(nowdate.getFullYear()-date);
					}else{
						$(".tangniaobing").val(0);
						
					}
					//疾病史回选
					var num1 = '040101';
					if(other[num1][0][num1]=='1'){
						$('.showjibing').css('display','block');
						$('.addtime').each(function(){
							if($(this).is(':checked')){
								$(this).nextAll('.datetime').css('display','inline-block');
							}else{
								$(this).nextAll('.datetime').css('display','none');
							}
						});
						
					}
					//手术史回选
					var num2 = '040201';
					if(other[num2][0][num2]=='1'){
						$('.shuoshu').css('display','');
//						$(".shuoshuOnce").css('display','block');
					}
					//外伤史回选
					var num3 = '040301';
					if(other[num3][0][num3]=='1'){
						$('.waishang').css('display','');
//						$(".waishangOnce").css('display','block');
					}
					//输血史
					var num4 = '040401';
					if(other[num4][0][num4]=='1'){
						$('.shuxue').css('display','');
//						$(".shuxueOnce").css('display','block');
					}
					//家庭成员病史回选
					var num5 = '050101';
					if(other[num5][0][num5]=='1'){
						$('.member').css('display','block');
//						$(".memberOnce").css('display','block');
					}
					delBox();
					delMember();
//					$("#infoForm").data("data", list.data);
				}
			}
		});
	}
	getInfo(order_id);
	//删除家庭成员病史
	function delMember(){
		$(".del_member").on('click',function(){
			$(this).parentAll('.member').remove();
		})
	}
	
	//删除手术、外伤、输血史
	function delBox(){
		$('.del').on('click',function(){
			$(this).parentAll('.sstable').remove();
		});
	}
	//返回
		$('.goback').on('click',function(){
			window.location.href = "HBPindex.html?order_id="+order_id;
		});
		//下一步、上一步的tab函数封装
		function Change(n,m,i){
			$(n).removeClass(m);
			var spp = $(n).eq(i).addClass(m);
		}
		var flag = 0;
		//点击下一步
		function layers(){
			layer.open({
			    content: '信息不完整！',
			    skin: 'msg',
			    time: 3 //3秒后自动关闭
			});
		}
		$(".next").on('click',function(){
			var temp = false;
			$(".basicInfo").css('display','none');
			var see = $(this).parent().parent();
			var yeshu = $(this).parent().parent().index();
			var index = $(this).parent().parent().next().index();
			var bitian = $(this).parent().parent().find('.bitian');
			var flag1 = true;
			//校验单选框是否选中
			function inputRd(name){
				 var val=$("input:radio[name="+name+"]:checked");
				 if(val.val() == null){
					 see.css('display','block');
					 $("input[name="+name+"]").parent().prev().css("color","#CD412F");
					 temp = true;
					 flag1 = false;
				 }else{
					 $("input[name="+name+"]").parent().prev().css("color","#333");
				 }  
				 if(temp){
					 layers();
				}
			}
			if(yeshu == 0){
				inputRd('030403');
			}else if(yeshu == 1){
				inputRd('010101');
				inputRd('010201');
				inputRd('010301');
				inputRd('010401');
				inputRd('010501');
				inputRd('020101');
				inputRd('020201');
				inputRd('020202');
			}else if(yeshu == 2){
				inputRd('040101');
				inputRd('040201');
				inputRd('040301');
				inputRd('040401');
				inputRd('050101');
			}
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
		//体育锻炼
		function sports(){
			$("input[name='010101']").on('click',function(){
				var notshow = $(this).parent().parent().nextAll('li');
				if($(this).val() == 4){
					notshow.css('display','none');
					notshow.find('input').removeClass('bitian');
					notshow.find('input').val('');
				}else{
					notshow.css('display','block');
					notshow.find('input').addClass('bitian');
					notshow.find('input').val('');
				}
			});
		}
		sports();	
		//吸烟
		function smoking(){
			$("input[name='010401']").on('click',function(){
				var notshow = $(this).parent().parent()
				if($(this).val() == 1){
					notshow.nextAll('li').css('display','none');
					notshow.nextAll('li').find('input').removeClass('bitian');
					notshow.nextAll('li').find('input').val('');
				}else if($(this).val() == 2){
					notshow.nextAll('.sago').css('display','block');
					notshow.nextAll('.sago').find('input').addClass('bitian');
					notshow.nextAll('.nnever').css('display','none');
					notshow.nextAll('.nnever').find('input').val('');
					notshow.nextAll('.nnever').find('input').removeClass('bitian');
				}else{
					notshow.nextAll('.nnever').css('display','block');
					notshow.nextAll('.nnever').find('input').addClass('bitian');
					notshow.nextAll('.sago').css('display','none');
					notshow.nextAll('.sago').find('input').val('');
					notshow.nextAll('.sago').find('input').removeClass('bitian');
				}
			});
		}
		smoking();
		//饮酒
		function drinking(){
			$('input[name="010501"]').on('click',function(){
				var drink = $(this).parent().parent();
				if($(this).val() == 1){
					drink.siblings('li').css('display','none');
					drink.siblings('li').find(':text').removeClass('bitian');
				}else{
					drink.siblings('li').css('display','block');
					drink.siblings('li').find(':text').addClass('bitian');
					drink.siblings('li').find(':text').val('');
				}
			});
			
		}
		drinking();
		//是否有疾病史
		function isOK(){
			$("input[name='040101']").on('click',function(){
				var nextbox = $(this).parent().next();
				if($(this).val() == 0){
					nextbox.css('display','none');
					nextbox.find(':text').val('');
					nextbox.find(':checkbox').removeAttr("checked");  
				}else{
					nextbox.css('display','block');
				}
			});
		}
		isOK();
		
		//既往史有时，添加显示
		function getNoce(name,tableClass){
			$("input[name="+name+"]").on('click',function(){
				var list = $(this).parent().parent();
				if($(this).val() == '1'){
					list.find('.once').css('display','block');
					$(tableClass).css('display','');
					//console.log("选中");
				}else{
					list.find('.once').css('display','none');
					$(tableClass).css('display','none');
					list.find(":text").val('');
				}
			});
		}
		getNoce('040201',".shuoshu");//手术史
		getNoce('040301',".waishang");//外伤史
		getNoce('040401',".shuxue");//输血史
		//家庭病史是
		$("input[name='050101']").on('click',function(){
			var list = $(this).parent().parent();
			if($(this).val() == '1'){
				debugger;
				list.find('.once').css('display','block');
				list.find('.member').css('display','block');
				//console.log("选中");
			}else{
				//console.log("没中");
				list.find('.once').css('display','none');
				list.find('.member').css('display','none');
				list.find(':checkbox').removeAttr("checked"); 
			}
		});
		//点击添加确诊时间
		$(".addtime").on('change',function(){
			if($(this).is(':checked')){
				$(this).nextAll('.datetime').css('display','inline-block');
			}else{
				$(this).nextAll('.datetime').css('display','none');
			}
		})
		
		//添加手术史
		var num1 = 0;
		$(".addshoushu").on('click',function(){
			num1 +=1;
			var name_num = '0401020';
			var time_num = '0402030';
			name_num += num1;
			time_num += num1;
			var html = '<table class="sstable">'
						+'<tr class="first_tr"><td>（'+num1+'）</td><td><span class="del">删除</span></td></tr><tr>'
						+'<td>手术名称</td>'
						+'<td><input type="text" name="'+name_num+'" class="ssname" placeholder="点击填写"/></td></tr><tr>'
						+'<td>手术时间</td>'
						+'<td><input type="text" name="'+time_num+'" class="sstime" placeholder="点击填写"/></td></tr></table>';
			$(this).parent().parent().prev('.hide_table').append(html);	
			$(".del").on('click',function(){
				$(this).parent().parent().parent().parent().remove();
				num1 > 1? (num1 -=1):(num1 = 0);
			})
							
		})
		//添加外伤史
		var num2 = 0;
		$('.addshang').on('click',function(){
			num2 +=1;
			var ssname = '0403020';
			var sstime = '0403030';
			ssname += num2;
			sstime += num2;
			var html = '<table class="sstable">'
						+'<tr class="first_tr"><td>（'+num2+'）</td><td><span class="del">删除</span></td></tr><tr>'
						+'<td>外伤名称</td>'
						+'<td><input type="text" name="'+ssname+'" class="ssname" placeholder="点击填写"/></td></tr><tr>'
						+'<td>外伤时间</td>'
						+'<td><input type="text" name="'+sstime+'" class="sstime" placeholder="点击填写"/></td></tr></table>';
								
			$(this).parent().parent().prev('.hide_table').append(html);	
			$(".del").on('click',function(){
				$(this).parent().parent().parent().parent().remove();
				num2 > 1? (num2 -=1):(num2 = 0);
			})			
		});
		//添加输血史
		var num3 = 0;
		$(".addblood").on('click',function(){
			num3 +=1;
			var blood_name = '0404020';
			var blood_time = '0404030';
			blood_name += num3;
			blood_time += num3;
			var html = '<table class="sstable">'
						+'<tr class="first_tr"><td>（'+num3+'）</td><td><span class="del">删除</span></td></tr><tr>'
						+'<td>输血原因</td>'
						+'<td><input type="text" name="'+blood_name+'" class="ssname" placeholder="点击填写"/></td></tr>'
						+'<tr><td>输血时间</td>'
						+'<td><input type="text" name="'+blood_time+'" class="sstime" placeholder="点击填写"/></td></tr></table>';
			$(this).parent().parent().prev('.hide_table').append(html);	
			$(".del").on('click',function(){
				$(this).parent().parent().parent().parent().remove();
				num3 > 1? (num3 -=1):(num3 = 0);
			})		
		});
		//添加家庭病史
		var num4 = 0;
		$(".addmember").on('click',function(){
			num4 +=1;
			
			var mem_name = '0501020';
			var mem_time = '0501030';
			mem_name += num4;
			mem_time += num4;
			
			var html = '<div class="member">'
						+'<div class="first_tr member_num">'
						+'<p><span class="num_mm">（'+num4+'）</span><span class="del_member">删除</span></p></div>'
						+'<div class="info_left f_left">家庭成员</div>'
						+'<div class="info_rgt f_rgt">'
						+'<select name="'+mem_name+'" class="noboeder">'
						+'<option value="">请选择</option>'
						+'<option value="1">父亲</option>'
						+'<option value="2">母亲</option>'
						+'<option value="3">兄</option>'
						+'<option value="4">弟</option>'
						+'<option value="5">姐</option>'
						+'<option value="6">妹</option>'
						+'<option value="7">儿子</option>'
						+'<option value="8">女儿</option></select>'
						+'<img class="arrow" src="image/rgt.png"/></div>'
						+'<div class="info_left f_left">选择所患疾病</div>'
						+'<div class="info_rgt f_rgt"><input type="text" value="" placeholder="请选择"/><img class="arrow" src="image/rgt.png"/></div>'
						+'<div class="sickBox"><div class="opt">'
						+'<input class="magic" name="'+mem_time+'" value="1" type="checkbox">'
						+'<label class="lab">高血压</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="2" type="checkbox">'
						+'<label class="lab">糖尿病</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="3" type="checkbox">'
						+'<label class="lab">冠心病</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="4" type="checkbox">'
						+'<label class="lab">慢性阻塞性肺疾病</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="5" type="checkbox">'
						+'<label class="lab">恶性肿瘤</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="6" type="checkbox">'
						+'<label class="lab">脑卒中</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="7" type="checkbox">'
						+'<label class="lab">重性精神疾病</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="8" type="checkbox">'
						+'<label class="lab">结核病</label></div><br/><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="9" type="checkbox">'
						+'<label class="lab">肝炎</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="10" type="checkbox">'
						+'<label class="lab">先天畸形</label></div><div class="opt">'
					    +'<input class="magic" name="'+mem_time+'" value="11" type="checkbox">'
						+'<label class="lab">其他</label></div></div></div>';
			$(this).parent().parent().prev('.hide_table').append(html);	
			$(".del_member").on('click',function(){
				$(this).parent().parent().parent().remove();
				num4 > 1? (num4 -=1):(num4 = 0);
			});
			
			/*var data = $("#infoForm").data("data");
			var info = data.patientInfo;
			var other = data.documentInfo;
						
			//多选框
			var $sickBox = $(this).parents(".checkBox").find(".sickBox");
			$sickBox.each(function(){
				var checkbox = $(this).find(".magic").get(0);
				var box_name = $(checkbox).attr("name");
				var that = this;
				if(other[box_name]){
					$.each(other[box_name], function(i, n){
						var value = n[box_name];
						$(that).find(".magic").each(function(){
							if(value == $(this).val()){
								$(this).attr("checked","checked");
							}
						});
					});
				}
			});*/
		});
		//BMI
		$("#weight").on('blur',function(){
			var weight = $(this).val();
			var height = $("#height").val();
			var bmi = (weight/(height*height)*10000).toFixed(2);
			$("#BMI").val(bmi);
		})
		function getBMI(){
			var hgt = $("#height").val();
			var wgt = $("#weight").val();
			if(!hgt == '' && !wgt == ''){
				var bmi = (wgt/(hgt*hgt)*10000).toFixed(2);
				$("#BMI").val(bmi);
			}
		}
	//点击保存
	$('.tijiao').on('click',function(){
		var temp = false;
		var see = $(this).parent().parent();
		var flag1 = true;
		//校验单选框是否选中
		function inputRd(name){
			 var val=$("input:radio[name="+name+"]:checked");
			 if(val.val() == null){
				 $("input[name="+name+"]").parent().prev().css("color","#CD412F");
				 temp = true;
				 flag1 = false;
			 }else{
				 $("input[name="+name+"]").parent().prev().css("color","#333");
			 }  
			 if(temp){
				 layers();
			}
		}
		inputRd('060101');
		inputRd('060102');
		if(flag1){
			var dataList = $.param({order_id: order_id})+'&'+ serializeNotNull($('#infoForm').serialize());
			$.ajax({
				type:"post",
				url:"../../../hypertension-app/updateBasicInfo.action",
				dataType : 'json',
				data:dataList,
				success: function(data){
					console.log(data);
					if(data.flag){
						
						layer.open({
						    content: '保存成功！',
						    skin: 'msg',
						    time: 2 //2秒后自动关闭
						});
						$("#infoForm").get(0).reset();
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
	})
	//序列化排除空值
	function serializeNotNull(serStr){
	    return serStr.split("&").filter(function(str){return !str.endsWith("=")}).join("&");
	}
	
});
