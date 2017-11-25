$(function(){
	//获取地址栏order_id方法
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	var order_id=getUrlParam("order_id");
	$("#order_id").val(order_id);
	/* 加载信息 */
	console.log(order_id);
	function getinfo(order_id){
		$.ajax({
			url: "../../../hypertension-app/getBasicInfo.action",
			type: "GET",
			dataType: 'json',
			data: {order_id : order_id},
			success: function(list){
				if(list.flag){
					console.log(list);
					var info = list.data.patientInfo;
					$('.info').each(function(){
						var name = $(this).attr('name');
						if(info[name] || info[name]==0){
							if(info[name] == "200001" || info[name] == "200002"){
								$(this).val(info[name] = "200001" ? '男':'女');
							}else{
								$(this).val(info[name]);
							}
						}
					});
					/*输入框*/
					
				}
			}
		});
	}
	getinfo(order_id);
	/*体育锻炼*/
	$(".sports").on('change',function(){
		if($(this).val() == '4'){
			$(this).parent().siblings().css('display','none');
		}else{
			$(this).parent().siblings().css('display','inline-block');
		}
	});
	/*吸烟状况*/
	$(".dosmoke").on('change',function(){
		if($(this).val() == '1'){
			$(this).parent().siblings().css('display','none');
		}else if($(this).val() == '2'){
			$(".smoking").css('display','none');
			$(".smoked").css('display','inline-block');
		}else{
			$(".smoking").css('display','inline-block');
			$(".smoked").css('display','none');
		}
	});
	/*饮酒情况*/
	$(".drinking").on('change',function(){
		if($(this).val() == '1'){
			$(this).parent().siblings().css('display','none');
		}else{
			$(this).parent().siblings().css('display','inline-block');
		}
	});
	/*是否有疾病史*/
	$(".sick").on('click',function(){
		if($(this).val() == '0'){
			$(this).parent().next().css('display','none');
		}else{
			$(this).parent().next().css('display','block');
		}
	});
	/*添加手术*/
	var num = 0;
	$(".add-shoushu").on('click',function(){
		num ++;
		var nameNum = '0402020'+num;
		var timeNum = '0402030'+num;
		var del = $(this).parent().nextAll('.delBox');
		var html = '<li style="width:98%;">'+
				'<label class="problem">手术名称：</label>'+
				'<input class="tetbox" type="text" name="'+nameNum+'"/>'+
				'<label class="problem">手术时间：</label>'+
				'<input class="tetbox" type="date" name="'+timeNum+'"/>'+
				'</li>';
		del.before(html);
		del.css('display','inline-block');
	});
	/*添加外伤*/
	var num = 0;
	$(".add-waishang").on('click',function(){
		num ++;
		var nameNum = '0403020'+num;
		var timeNum = '0403030'+num;
		var del = $(this).parent().nextAll('.delBox');
		var html = '<li style="width:98%;">'+
				'<label class="problem">外伤名称：</label>'+
				'<input class="tetbox" type="text" name="'+nameNum+'"/>'+
				'<label class="problem">外伤时间：</label>'+
				'<input class="tetbox" type="date" name="'+timeNum+'"/>'+
				'</li>';
		del.before(html);
		del.css('display','inline-block');
	});
	/*添加输血*/
	var num = 0;
	$(".add-blood").on('click',function(){
		num ++;
		var nameNum = '0404020'+num;
		var timeNum = '0404030'+num;
		var del = $(this).parent().nextAll('.delBox');
		var html = '<li style="width:98%;">'+
				'<label class="problem">输血原因：</label>'+
				'<input class="tetbox" type="text" name="'+nameNum+'"/>'+
				'<label class="problem">输血时间：</label>'+
				'<input class="tetbox" type="date" name="'+timeNum+'"/>'+
				'</li>';
		del.before(html);
		del.css('display','inline-block');
	});
	/*添加家庭成员病史*/
	var num = 0;
	$(".add-memjibing").on('click',function(){
		num ++;
		var nameNum = '0501020'+num;
		var timeNum = '0501030'+num;
		var del = $(this).parent().nextAll('.delBox');
		var html = '<li style="width:98%;">'+
				'<label class="problem">家庭成员：</label>'+
				'<select name="'+nameNum+'" class="sel_op">'+
					'<option value="">请选择</option>'+
					'<option value="1">父亲</option>'+
					'<option value="2">母亲</option>'+
					'<option value="3">哥哥</option>'+
					'<option value="4">弟弟</option>'+
					'<option value="5">姐姐</option>'+
					'<option value="6">妹妹</option>'+
					'<option value="7">儿子</option>'+
					'<option value="8">女儿</option>'+
				'</select><br/>'+
				'<label class="problem" style="margin-top: 10px;">疾病名称：</label>'+
				'<p>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="1" id="familymb1"/>'+
					'<label class="problem" for="familymb1">高血压 </label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="2" id="familymb2"/>'+
					'<label class="problem" for="familymb2">糖尿病 </label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="3" id="familymb3"/>'+
					'<label class="problem" for="familymb3">冠心病 </label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="4" id="familymb4"/>'+
					'<label class="problem" for="familymb4">慢性阻塞性肺疾病</label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="5" id="familymb5"/>'+
					'<label class="problem" for="familymb5">恶性肿瘤</label><br/>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="6" id="familymb6"/>'+
					'<label class="problem" for="familymb6">脑卒中</label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="7" id="familymb7"/>'+
					'<label class="problem" for="familymb7">重性精神疾病 </label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="8" id="familymb8"/>'+
					'<label class="problem" for="familymb8">结核病 </label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="9" id="familymb9"/>'+
					'<label class="problem" for="familymb9">肝炎</label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="10" id="familymb10"/>'+
					'<label class="problem" for="familymb10">先天畸形</label>'+
					'<input class="ck" type="checkbox" name="'+timeNum+'" value="11" id="familymb11"/>'+
					'<label class="problem" for="familymb11">其他</label>'+
				'</p></li>';
		del.before(html);
		del.css('display','inline-block');
	});
	/*删除*/
	$(".delpre").on('click',function(){
		var length = $(this).parent().siblings();
		var preLi = $(this).parent().prev();
		if(length.length > 1){
			preLi.remove();
			num --;
		}
	});
	/*点击保存*/
	$("#do-save").on('click',function(){
		$(".allinput").each(function(){
			var self = $(this).attr("data-name");
			console.log($("#"+self).val());
			if ($("#"+self).val() == '') {
				mkh.errTips("#"+self, "请填写！");
			} else {
				mkh.errTipsRemove("#"+self);
			}
		});
	});
	
	
});