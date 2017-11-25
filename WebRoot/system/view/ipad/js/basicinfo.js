//
$(function(){
	//
	basicinfo.init();
	
});
//
var basicinfo = { 
	//
	init : function(){
		//调用初始值函数
		var order_id = this.getUrlParam("order_id");
		//调用获取基本信息函数
		//order_id = 1017;
		this.basicinfo_detail(order_id);
	},
	//初始化函数
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},
	//定义基础信息函数
	basicinfo_detail : function(order_id){
		$.ajax({
			url:"../../../BasicInfoSurvey/getBasicInfoSurveyDetail.action",
			type:"get",
			dataType : 'json',
			data : {order_id:order_id},
			success : function(response){
				//console.log(response);
				var p1 = response.data.BasicInfo;
				var p2 = response.data.answers;
				if(response.status=="1"){
	 				//
					$(".basic-information .data").each(function(){
						var id = $(this).attr("id");
						if(p1[id] || p1[id] == 0){
							$(this).html(p1[id]);
						}
					});
					basicinfo.familyMbmber(p2);
					basicinfo.basic_answer(p2);
					basicinfo.diseases(p2);
					basicinfo.mxb_show();
					basicinfo.plus_over();
					basicinfo.never();
					basicinfo.yes_hide();
				}
			}
		});
	},
	//从不锻炼、从不吸烟、从不饮酒的情况
	never : function(){
		function never_do(class_name){
			var element = $(class_name);
			if(element.html() == "从不锻炼"){
				element.parent().parent().siblings().css("display","none");
			}else if(element.html() == "从不吸烟"||element.html() == "从不"){
				element.parent().parent().next("ul").css("display","none");
			}
		}
		
		never_do(".do>#show_answer");
		never_do(".smoked>#show_answer");
		never_do(".drink>#show_answer");
		
	},
	//显示家庭成员
	familyMbmber : function(item){
		if(item.t1){
			var html = "";
			$.each(item.t1, function(i,m) {
				html += "<p class='half'><span class='child' id='1101'>"+m[0].answer+"</span>"+
						"<span class='child-name' id='1102'>"+m[1].answer+"</span>"+
						"<span class='child-phone' id='1103'>"+m[2].answer+"</span></p>";
			});
			$("#family_info").html(html);
			this.child_color();
		}else{
			$("#family_info").html("");
			return false;
		}
		this.child();
	},
	//家庭成员的关系为女儿或孙女时，颜色变为粉红色，其他的为天蓝色
	child_color : function(){
		$(".child").each(function(){
			if($(this).html() == "女儿"||$(this).html() == "孙女"){
				$(this).css({"color":"#ff928d","background-color":"#fff4f3","border":"1px solid #ff928d"});
			}else{
				$(this).css({"color":"#b3d1f9","background-color":"#f5f9fe","border":"1px solid #b3d1f9"});
			}
		})
	},
	//家庭成员过多时的样式
	child : function(){
		var half = $(".half");
		if(half.length >= 2){
			$("#family_info").css({"float":"none","display":"block"});
			half.css({"width":"50%","display":"inline-block","float":"left","padding":"10px 0","text-align":"left"});
		}
	},
	//显示答案
	basic_answer : function(item){
		if(item.t0!=undefined){
			$("span.survey_answer").each(function(){
				var name = $(this).attr("id");
				var self = $(this);
				self.empty();
				$.each(item.t0.c0, function(i,m) {
					if(name == m.question_k_order_id){
						self.append('<span id="show_answer" style="font-size: 15px;padding-left: 15px;">'+m.answer+'</span>');
					}else{
						self.append("");
					}
				});
			});
		}else{
			$("span.survey_answer").each(function(){
				$(this).html("");
			});
		}
	},
	//疾病史
	diseases : function(item){
		//
		function handelHtml(data, selector){
			if(data){
				var html = "";
				$.each(data, function(i,m) {
					html += "<li><span>"+m[0].answer+"</span><span>"+((m[1] == undefined) ? "" : m[1].answer)+"</span></li>";
				});
				$(selector).append(html);
			}else{
				$(selector).html("");
				return false;
			}				
		}
		//传参
		handelHtml(item.t2, ".medical-history");
		
		handelHtml(item.t3, ".shoushu_list");
		
	    handelHtml(item.t4, ".injury_list");
		
	    handelHtml(item.t5, ".blood_list");
		
		
		if(item.t6){
			var mm="";
			$.each(item.t6,function(k,m){
				var html2="";
				$.each(m,function(k,m){
					html2+="<span><span>"+m.answer+"</span></span>";	
				});
				mm+="<li class='fhb'>"+html2+"</li>";
			});
			$(".familyd_list").append(mm);
		}else{
			$(".familyd_list").html("");
			return false;
		}
	},
	//既往史中，问题是的回答是“是”时，隐藏是
	yes_hide :function(){
		$(".yes_hide>#show_answer").each(function(){
			if($(this).html() == "是"){
				//console.log(this);
				$(this).css("display","none") ;
			}
		})
	},
	//慢性病列表隐藏
	mxb_show : function(){
		var height_b = $("#4201");
		var sugar_d = $("#4203");
		if(height_b.html() == ""){
			height_b.parent().css("display","none");
			sugar_d.parent().css("border-left","1px dashed #eff4fb");
		}
		if(sugar_d.html() == ""){
			sugar_d.parent().css("display","none");
		}
		if((height_b.html() == "")&&(sugar_d.html() == "")){
			$(".manb_list").css("display","none");
		}
	},
	//以下症状、以下慢病过多时
	plus_over : function(){
		var zz = $(".plus>#show_answer");
		var manb = $(".sort>#show_answer");
		//console.log(manb);
		if(zz.length >= 4){
			$(".plus").css({"display":"block","float":"none","padding":"10px 13px"});
		}
		if(manb.length >= 4){
			$(".sort").css({"display":"block","float":"none","padding":"10px 13px"});
		}
	}
	
};