//
$(function() {
	//初始化配置
	app.init();
	//加载数据
	app.loadData();
});
//
var app = {
	//初始化方法
	init : function() {
		this.initData();
		//绑定点击事件
		this.more_click();
		this.more_select();
		this.check_click();
		this.click_after();
		this.click_before();
		this.check_alone();
		//
		this.win_add_item();
		//
		this.doSave();
		
		//
		this.examineCheck();
		this.diseaseHistory();
		//既往史
		this.clickHistory();
		//基础
		this.clickBase();
		//生活方式
		this.clickLife();
	},
	//回显数据
	loadData : function() {
		var that = this;
		var order_id = this.getUrlParam("orderId");
		order_id = 341;
		if(order_id){
			$.ajax({
				url : "../../../BasicInfoSurvey/getBasicInfoSurveyDetail.action",
				type : "get",
				dataType : 'json',
				data : {
					order_id : order_id
				},
				success : function(data) {
					console.log(data);
					if(data.status == 1){
						info = data.data;
						//
						that.baseInfo(info.BasicInfo);
						
					}
				}
			});
		}
	},
	//基础数据
	baseInfo : function(info){
		//文本
		$(".active-base .info").find(".data").each(function(){
			var name = $(this).attr("data-name");
			if(info[name] || info[name] == 0){
				$(this).text(info[name]);
			}
		});
		//输入框
		$(".active-base li").find(".input-text").each(function(){
			var name = $(this).attr("data-name");
			if(info[name] || info[name] == 0){
				$(this).val(info[name]);
			}
		});
		//显示单选
		$(".active-base .select-more").each(function(){
			//文字
			var $span = $(this).find(".data");
			var name = $span.attr("data-name");
			//文本
			if(info[name] || info[name] == 0){
				$span.text(info[name]);
				$span.css("color", "#545454");
			}
			//选择框架
			$(this).find(".more-check").each(function(){
				$(this).find(".check-item").each(function(){
					if(info[name] || info[name] == 0){
						if($(this).text() == info[name]){
							$(this).addClass("select-item");
						}
					}
				});
			});
		});
	},
	//初始化数据
	initData : function(){
		var user_id = this.getUrlParam("userId");
		if(user_id){
			$.ajax({
				url : "../../../BasicInfoSurvey/getBasicInfo.action",
				type : "get",
				dataType : 'json',
				data : {user_id : user_id},
				success : function(data) {
					//console.log(data);
					data = data.data;
					//
					$(".active-base").find(".info .data").each(function(){
						var name = $(this).attr("data-name")
						$(this).text(data[name]);
					});
				}
			});
		}
	},
	//生活方式
	clickLife : function(){
		//
		$(".active-life .on-check").find(".never").on("touchend", function(){
			if($(this).parents(".life-more").hasClass("exercise")){
				$(".active-life .exercise-item").hide();
				$(".active-life .exercise-item").find(".input-text").val("");
			}else if($(this).parents(".life-more").hasClass("smoke")){
				$(".active-life .smoke-item").hide();
				$(".active-life .smoke-item").find(".input-text").val("");
			}else if($(this).parents(".life-more").hasClass("wine")){
				$(".active-life .wine-item").hide();
				//文本
				$(".active-life .wine-item").find(".input-text").val("");
				//多选
				$(".active-life .wine-item").find(".more-check").find(".check-img").attr("src", "image/more-def.png");
				$(".active-life .wine-item").find(".more-check").find(".check-img").removeClass("more_check");
				//单选
				$(".active-life .wine-item").find(".check-item").removeClass("select-item");
			}
		});
		//
		$(".active-life .on-check").find(".check-item").not(".never").on("touchend", function(){
			//
			if($(this).parents(".life-more").hasClass("exercise")){
				$(".active-life .exercise-item").show();
			}else if($(this).parents(".life-more").hasClass("smoke")){
				$(".active-life .smoke-item").show();
			}else if($(this).parents(".life-more").hasClass("wine")){
				$(".active-life .wine-item").show();
			}
		});
		//
	},
	//基础信息
	clickBase : function(){
		$(".active-base .check-yes .alone-img").on("touchend", function(){
			$(this).parents(".select-more").find(".base-btn").show();
		});
		$(".active-base .check-no .alone-img").on("touchend", function(){
			$(this).parents(".select-more").find(".base-btn").hide();
		});
	},
	//点击既往史
	clickHistory : function(){
		$(".active-history .check-yes .alone-img").on("touchend", function(){
			$(this).parents(".history-more").find(".more-check").show();
			$(this).parents(".history-more").find(".history-btn").show();
		});
		$(".active-history .check-no .alone-img").on("touchend", function(){
			$(this).parents(".history-more").find(".more-check").hide();
			$(this).parents(".history-more").find(".history-btn").hide();
			//多选
			$(this).parents(".history-more").find(".more-check").find(".check-img").attr("src", "image/more-def.png");
			$(this).parents(".history-more").find(".more-check").find(".check-img").removeClass("more_check");
			//弹窗
		});
	},
	//问卷调查绑定
	examineCheck : function(){
		//高血压
		$("#hypertension").on("click", function() {
			if($(this).hasClass("more_check")){
				$("#chronicDisease_4201").val("高血压");
				layer.open({
					content : '是否服用降压药？',
					btn : [ '是', '否' ],
					yes : function(index) {
						layer.close(index);
						$("#chronicDisease_4202").val("已服药");
					},
					no : function(){
						$("#chronicDisease_4202").val("未服药");
					}
				});
			}else{
				$("#chronicDisease_4201").val("");
				$("#chronicDisease_4202").val("");
			}
		});
		//糖尿病
		$("#diabetes").on("click", function() {
			if($(this).hasClass("more_check")){
				$("#chronicDisease_4203").val("糖尿病");
				layer.open({
					content : '是否服用糖尿病？',
					btn : [ '是', '否' ],
					yes : function(index) {
						layer.close(index);
						$("#chronicDisease_4204").val("已服药");
					},
					no : function(){
						$("#chronicDisease_4204").val("未服药");
					}
				});
			}else{
				$("#chronicDisease_4203").val("");
				$("#chronicDisease_4204").val("");
			}
		});
	},
	//疾病史绑定
	diseaseHistory : function(){
		$("#diseaseHistory .check-img").on("click", function() {
			
			var that = this;
			if($(this).hasClass("more_check")){
				//
				layer.open({
					type : 1,
					content : "<div style='height:300px'><ul class='disease-time'><li><span class='title lable'>确诊日期</span><input dir='rtl' class='input-text' placeholder='请输入确诊日期' type='text'></li><div class='cancel'>取消</div><div class='confirm'>确定</div></div>",
					anim : 'up',
					style : 'position:fixed; bottom:0; left:0; width: 100%; height: 200px; padding:10px 0; border:none;'
				});
				//
				$(".disease-time .cancel").on("touchend", function(){
					layer.closeAll();
				});
				$(".disease-time .confirm").on("touchend", function(){
					var val = $(this).parent().find(".input-text").val();
					$(that).parent().next("input").val(val);
					layer.closeAll();
				});
			}
		});
	},
	//获取URL参数
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},
	//保存基础档案
	doSave : function(){
		var that = this;
		$("#do-save").on("click", function(){
			//校验
			/*var flag = that.doCheck();
			if(!flag){
				layer.open({
				    content: '基础档案信息不完整！'
				    ,btn: '确定'
				});
				return;
			}*/
			//
			var obj = {};
			obj.user_id = that.getUrlParam("userId");
			/*obj.doctor_id = that.getUrlParam("doctorId");*/
			//
			var name,val,$more;
			//基础信息
			$(".active-base .data").each(function(i){
				if($(this).parents("li").hasClass("select-more")){
					$more = $(this).parents("li").find(".more-check .select-item");
					if($more.length > 0 ){
						val = $(this).parents("li").find(".more-check .select-item").text();
					}else{
						//错误提示
						val = "";
					}
					
				}else{
					val = $(this).text();
				}
				name = $(this).attr("data-name");
				obj[name] = val;
			});
			
			$(".active-base .input-text").each(function(i){
				val = $(this).val();
				name = $(this).attr("data-name");
				obj[name] = val;
			});
			//家庭成员
			val = $(".family-btn", ".active-base").parents(".select-more").find(".alone-check").next().text();
			name = $(".family-btn", ".active-base").parents(".select-more").attr("data-name");
			obj[name] = val;
			//
			var family_flag = $(".family-btn").parents("li").find(".check-yes").find("img").hasClass("alone-check");
			if(family_flag){
				$(".family .item").each(function(i){
					name = $(this).attr("data-name");
					$(this).find("li").each(function(n){
						if($(this).hasClass("familey-select")){
							if($(this).find(".select-item").length > 0){
								val = $(this).find(".select-item").text();
							}else{
								//错误提示
								val = "";
							}
						}else{
							val = $(this).find(".input-text").val();
						}
						var item_name = $(this).attr("data-name");
						item_name = item_name + "-" + (i + 1) + "-" + n;
						obj[item_name] = val;
					});
				});
			}
			//生活方式
			$(".active-life .life-more").each(function(){
			    name = $(this).attr("data-name");
				if($(this).hasClass("check-more")){
					//more_check
					if($(this).find(".more-item").find(".more_check").length > 0){
						$(this).find(".more-item").find(".more_check").each(function(i){
							val = $(this).next(".opt").text();
							var item_opt = name + "-" + (i + 1);
							obj[item_opt] = val;
						});
					}else{
						val = "";
						obj[name] = val;
					}
					
				}else{
					if($(this).find(".select-item").length > 0){
						val = $(this).find(".select-item").text();
					}else{
						//错误提示
						val = "";
					}
					obj[name] = val;
				}
			});
			//
			$(".active-life .life-item").each(function(){
				val = $(this).find(".input-text").val();
				name = $(this).attr("data-name");
				obj[name] = val;
			});
			//既往史
			$(".active-history .alone-check").each(function(){
				name = $(this).parent().attr("data-name");
				val = $(this).next().text();
				obj[name] = val;
			});
			
			$(".active-history .history-more").each(function(i){
				if($(this).hasClass("more")){
					name = $(this).attr("data-name");
					var $t = $(this);
					if($(this).find(".more-item").find(".more_check").length > 0){
						$(this).find(".more-item").find(".more_check").each(function(i){
							//
							if($t.attr("data-name") == 3201){
								val = $(this).next(".opt").text();
								var item_opt = name + "-" + (i + 1)+ "-" +(i + 1);
								obj[item_opt] = val;
								//
								val = $(this).parent().next().val();
								item_opt = 3211 + "-" + (i + 1)+ "-" +(i + 1);
								obj[item_opt] = val;
							}else{
								val = $(this).next(".opt").text();
								var item_opt = name + "-" + (i + 1);
								obj[item_opt] = val;
							}
						});
					}else{
						val = "";
						obj[name] = val;
					}
				}else if($(this).hasClass("input")){
					val = $(this).find(".input-text").val();
					name = $(this).find(".input-text").attr("data-name");
					obj[name] = val;
				}else if($(this).hasClass("more-input")){
					if($(this).find(".operation-btn").length > 0){
						var operation_flag = $(".operation-btn").parents("li").find(".check-yes").find("img").hasClass("alone-check");
						//
						if(operation_flag){
							$(".operation").find(".content").each(function(i){
								name = $(this).attr("data-name");
								$(this).find(".input-text").each(function(n){
									var more_name = $(this).attr("data-name");
									more_name = more_name + "-" + (i + 1) + "-" + n;
									val = $(this).val();
									obj[more_name] = val;
								});
							});
						}
						
					}else if($(this).find(".trauma-btn").length > 0){
						var trauma_flag = $(".trauma-btn").parents("li").find(".check-yes").find("img").hasClass("alone-check");
						if(trauma_flag){
							$(".trauma").find(".content").each(function(i){
								name = $(this).attr("data-name");
								$(this).find(".input-text").each(function(n){
									var more_name = $(this).attr("data-name");
									more_name = more_name + "-" + (i + 1) + "-" + n;
									val = $(this).val();
									obj[more_name] = val;
								});
							});
						}
						
					}else if($(this).find(".transfusion-btn").length > 0){
						var transfusion_flag = $(".transfusion-btn").parents("li").find(".check-yes").find("img").hasClass("alone-check");
						if(transfusion_flag){
							$(".transfusion").find(".content").each(function(i){
								name = $(this).attr("data-name");
								$(this).find(".input-text").each(function(n){
									var more_name = $(this).attr("data-name");
									more_name = more_name + "-" + (i + 1) + "-" + n;
									val = $(this).val();
									obj[more_name] = val;
								});
							});
						}
						
					}else if($(this).find(".illness-btn").length > 0){
						var illness_flag = $(".illness-btn").parents("li").find(".check-yes").find("img").hasClass("alone-check");
						if(illness_flag){
							$(".illness").find(".content").each(function(i){
								name = $(this).attr("data-name");
								$(this).find(".family-select").each(function(n){
									var more_name = $(this).attr("data-name");
									var more_item;
									if(n == 0){
										//select-item
										if($(this).find(".select-item").length > 0){
											val = $(this).find(".select-item").text();
										}else{
											val = "";
										}
										more_item = more_name + "-" + (i + 1) + "-" + n;
										obj[more_item] = val;
									}else if(n == 1){
										//
										if($(this).find(".more-item").find(".more_check").length > 0 ){
											$(this).find(".more-item").find(".more_check").each(function(m){
												more_item = more_name + "-" + (i + 1) + "-" + m;
												val = $(this).next(".opt").text();
												obj[more_item] = val;
											});
											
										}else{
											more_item = more_name + "-" + (i + 1) + "-" + n;
											val = "";
											obj[more_item] = val;
										}
									}
								});
							});
						}
						//
					}
				}
			});
			
			//问卷调查
			$(".active-examine .examine-more").each(function(){
				name = $(this).attr("data-name");
				if($(this).hasClass("more")){
					if($(this).find(".item").find(".more_check").length > 0){
						$(this).find(".item").find(".more_check").each(function(i){
							var more_name = name + "-" + i;
							val = $(this).next(".opt").text();
							obj[more_name] = val;
						});
					}else {
						val = "";
						obj[name] = val;
					}
				}else{
					if($(this).find(".select-item").length > 0){
						val = $(this).find(".select-item").text();
					}else{
						val = "";
					}
					obj[name] = val;
				}
			});
			//疾病历史
			$(".active-examine .hide").each(function(i){
				name = $(this).attr("data-name");
				val = $(this).val();
				obj[name] = val;
			});
			//辅助调查
			$(".active-auxiliary .auxiliary-more").each(function(){
				name = $(this).attr("data-name");
				if($(this).find(".select-item").length > 0){
					val = $(this).find(".select-item").text();
				}else{
					val = "";
				}
				obj[name] = val;
			});
			//预防检疫
			$(".active-prevent .prevent-more").each(function(){
				name = $(this).attr("data-name");
				if($(this).find(".select-item").length > 0){
					val = $(this).find(".select-item").text();
				}else{
					val = "";
				}
				obj[name] = val;
			});
			
			console.log(obj)
			return;
			$.ajax({
				url : "../../../BasicInfoSurvey/addBasicInfoSurvey.action",
				type : "post",
				dataType : 'json',
				data : obj,
				success : function(data) {
					if(data.status == 1){
						var order_id = data.data.order_id;
						window.location.href = "../../../system/view/ipad/basicinfo.html?order_id=" + order_id;
					}
				}
			});
		});
	},
	//添加弹窗
	win_add_item : function(){
		//
		function defaultCheck(that){
			$(that).parents("li").find(".alone-img").attr("src", "image/alone-def.png");
			$(that).parents("li").find(".alone-img").removeClass("alone-check");
			
			$(that).parents("li").find(".check-yes").find("img").attr("src", "image/alone-check.png");
			$(that).parents("li").find(".check-yes").find("img").addClass("alone-check");
		}
		
		//家庭
		$(".family-btn").on("touchend", function(){
			//defaultCheck(this);
			$(".core").hide();
			$(".win.family").show();
			$("html,body").animate({scrollTop : $(".family").offset().top},0);
		});
		//手术
		$(".history-btn .operation-btn").on("touchend", function(){
			//defaultCheck(this);
			$(".core").hide();
			$(".win.operation").show();
			$("html,body").animate({scrollTop : $(".operation").offset().top},0);
		});
		//
		$(".history-btn .trauma-btn").on("touchend", function(){
			//defaultCheck(this);
			$(".core").hide();
			$(".win.trauma").show();
			$("html,body").animate({scrollTop : $(".trauma").offset().top},0);
		});
		//
		$(".history-btn .transfusion-btn").on("touchend", function(){
			//defaultCheck(this);
			$(".core").hide();
			$(".win.transfusion").show();
			$("html,body").animate({scrollTop : $(".transfusion").offset().top},0);
		});
		//
		$(".history-btn .illness-btn").on("touchend", function(){
			//defaultCheck(this);
			$(".core").hide();
			$(".win.illness").show();
			$("html,body").animate({scrollTop : $(".illness").offset().top},0);
		});
		//
		$(".win .btn-click").on("touchend", function(){
			$(this).parents(".win").find(".content").last().after($(this).parents(".win").find(".content").first().clone(true));
			//默认选择
			$(this).parents(".win").find(".content").last().find(".data").text("请选择");
			$(this).parents(".win").find(".content").last().find(".check-item").removeClass("select-item");
			$(this).parents(".win").find(".content").last().find(".input-text").val("");
			$(this).parents(".win").find(".content").last().find(".check-img").attr("src", "image/more-def.png");
			$(this).parents(".win").find(".content").last().find(".check-img").removeClass("more_check");
		});
		
		//
		$(".win .del").on("touchend", function(){
			if($(this).parents(".win").find(".content").length == 1) return;
			$(this).parents(".content").remove();
		});
		
		$(".win .return,.win .btn-save").on("touchend", function(){
			$(".core").show();
			$(this).parents(".win").hide();
			if($(this).parents(".win").hasClass("family")){
				$("html,body").animate({scrollTop : $(".base-btn").parents("li").offset().top},0);
			}else if($(this).parents(".win").hasClass("operation")){
				$("html,body").animate({scrollTop : $(".operation-btn").parents("li").offset().top},0);
			}else if($(this).parents(".win").hasClass("trauma")){
				$("html,body").animate({scrollTop : $(".trauma-btn").parents("li").offset().top},0);
			}else if($(this).parents(".win").hasClass("transfusion")){
				$("html,body").animate({scrollTop : $(".transfusion-btn").parents("li").offset().top},0);
			}else if($(this).parents(".win").hasClass("illness")){
				$("html,body").animate({scrollTop : $(".illness-btn").parents("li").offset().top},0);
			}
		});
		
	},
	//点击事件
	more_click : function() {
		$(".jt,.data").on("touchend", function() {
			if ($(this).parent().find(".more-check").is(":hidden")) {
				$(this).parents(".content").find(".more-check").hide();
				$(".more-check").parent().find(".jt").attr("src", "image/jt-right.png");
				$(this).parent().find(".more-check").show();
				$(this).parents("li").find(".jt").attr("src", "image/jt-but.png");
			} else {
				$(this).parents(".content").find(".more-check").hide();
				$(this).parents("li").find(".jt").attr("src", "image/jt-right.png");
			}
		});
	},
	//选择
	more_select : function(){
		$(".more-check .check-item").on("touchend", function(){
			$(this).parent().find(".check-item").removeClass("select-item");
			$(this).addClass("select-item");
			var val = $(this).text();
			$(this).parents("li").find(".data").text(val).css({"color": "#666666"});
			$(this).parents(".content").find(".more-check").hide();
			$(this).parents("li").find("img").attr("src", "image/jt-right.png");
		})
	},
	//多选
	check_click : function(){
		$(".check-img").on("touchend", function(){
			if($(this).hasClass("more_check")){
				$(this).attr("src", "image/more-def.png");
				$(this).removeClass("more_check");
			}else{
				$(this).attr("src", "image/more-check.png");
				$(this).addClass("more_check");
			}
		});
	},
	//单选
	check_alone : function(){
		$(".alone-img").on("touchend", function(){
			$(this).parents("li").find(".alone-img").attr("src", "image/alone-def.png");
			$(this).parents("li").find(".alone-img").removeClass("alone-check");
			$(this).attr("src", "image/alone-check.png");
			$(this).addClass("alone-check");
		});
	},
	//下一步
	click_after : function(){
		$(".btn-after").on("touchend", function(){
			$(this).parent().hide();
			$(this).parent().next("div").show();
			//
			$(".nav .tab").removeClass("tab-select");
			$(".spot").find("img").attr("src", "image/tab-gray.png").addClass("tab-img");
			//
			if($(this).parent().next("div").hasClass("active-life")){
				$(".nav .tab").eq(1).addClass("tab-select");
				$(".tab-life").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().next("div").hasClass("active-history")){
				$(".nav .tab").eq(2).addClass("tab-select");
				$(".tab-history").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().next("div").hasClass("active-examine")){
				$(".nav .tab").eq(3).addClass("tab-select");
				$(".tab-examine").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().next("div").hasClass("active-auxiliary")){
				$(".nav .tab").eq(4).addClass("tab-select");
				$(".tab-auxiliary").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().next("div").hasClass("active-prevent")){
				$(".nav .tab").eq(5).addClass("tab-select");
				$(".tab-prevent").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}
		});
	},
	//上一步
	click_before : function(){
		$(".btn-before").on("touchend", function(){
			$(this).parent().hide();
			$(this).parent().prev("div").show();
			//
			$(".nav .tab").removeClass("tab-select");
			$(".spot").find("img").attr("src", "image/tab-gray.png").addClass("tab-img");
			//
			if($(this).parent().prev("div").hasClass("active-life")){
				$(".nav .tab").eq(1).addClass("tab-select");
				$(".tab-life").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().prev("div").hasClass("active-history")){
				$(".nav .tab").eq(2).addClass("tab-select");
				$(".tab-history").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().prev("div").hasClass("active-examine")){
				$(".nav .tab").eq(3).addClass("tab-select");
				$(".tab-examine").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().prev("div").hasClass("active-auxiliary")){
				$(".nav .tab").eq(4).addClass("tab-select");
				$(".tab-auxiliary").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}else if($(this).parent().prev("div").hasClass("active-base")){
				$(".nav .tab").eq(0).addClass("tab-select");
				$(".tab-base").find("img").attr("src", "image/tab-select.png").removeClass("tab-img").addClass("tab-select-img");
			}
		});
	},
	//错误校验
	doCheck : function(){
		var flag = true;
		var len,val,check,temp;
		/*基础信息*/
		//输入框校验
		$(".active-base").find(".input-text").each(function(i){
			//#545454
			val = $.trim($(this).val());
			if(!val){
				flag = false;
				$(this).parents("li").find(".title").css({"color":"red"});
			}else{
				$(this).parents("li").find(".title").css({"color":"#545454"});
			}
		});
		//选择框校验
		$(".active-base").find(".more-check").each(function(i){
			//#545454
			len = $(this).find(".select-item").length;
			if(len < 1){
				flag = false;
				$(this).parents("li").find(".title").css({"color":"red"});
			}else{
				$(this).parents("li").find(".title").css({"color":"#545454"});
			}
			
		});
		//多选输入框
		check = $(".family-btn", ".select-more").parents("li").find(".check-no .alone-img").hasClass("alone-check");
		if(check){
			$(this).parents("li").find(".title").css({"color":"#545454"});
		}else{
			temp = true;
			$(".family").find(".input-text").each(function(){
				val = $.trim($(this).val());
				if(!val){
					flag = false;
					temp = false;
				}
			});
			$(".family").find(".more-check").each(function(i){
				len = $(this).find(".select-item").length;
				if(len < 1){
					flag = false;
					temp = false;
				}
			});
			if(temp){
				$(".family-btn").parents("li").find(".title").css({"color":"#545454"});
			}else{
				$(".family-btn").parents("li").find(".title").css({"color":"red"});
			}
		}
		/*生活方式*/
		//体育锻炼
		len = $(".active-life .exercise").find(".select-item").length;
		if(len < 1){
			flag = false;
			$(".active-life .exercise").prev().css({"color":"red"});
		}else{
			$(".active-life .exercise").prev().css({"color":"#545454"});
			//
			check = $(".active-life .exercise").find(".select-item").hasClass("never");
			if(!check){
				temp = true;
				$(".active-life .exercise-item").find(".input-text").each(function(){
					val = $.trim($(this).val());
					if(!val){
						flag = false;
						temp = false;
					}
				});
				if(temp){
					$(".active-life .exercise").prev().css({"color":"#545454"});
				}else{
					$(".active-life .exercise").prev().css({"color":"red"});
				}
			}else{
				$(".active-life .exercise").prev().css({"color":"#545454"});
			}
		}
		
		//饮食习惯
		len = $(".active-life .food-item").find(".select-item").length;
		temp = true;
		if(len < 1){
			flag = false;
			temp = false;
		}
		
		len = $(".active-life .food-check").find(".more_check").length;
		if(len < 1){
			flag = false;
			temp = false;
			
		}
		//
		if(temp){
			$(".active-life .food-item").prev().css({"color":"#545454"});
		}else{
			$(".active-life .food-item").prev().css({"color":"red"});
		}
		//吸烟
		len = $(".active-life .smoke").find(".select-item").length;
		if(len < 1){
			flag = false;
			$(".active-life .smoke").prev().css({"color":"red"});
		}else{
			$(".active-life .smoke").prev().css({"color":"#545454"});
			//
			check = $(".active-life .smoke").find(".select-item").hasClass("never");
			if(!check){
				temp = true;
				$(".active-life .smoke-item").find(".input-text").each(function(){
					val = $.trim($(this).val());
					if(!val){
						flag = false;
						temp = false;
					}
				});
				if(temp){
					$(".active-life .smoke").prev().css({"color":"#545454"});
				}else{
					$(".active-life .smoke").prev().css({"color":"red"});
				}
			}else{
				$(".active-life .smoke").prev().css({"color":"#545454"});
			}
		}
		//饮酒
		len = $(".active-life .wine").find(".select-item").length;
		if(len < 1){
			flag = false;
			$(".active-life .wine").prev().css({"color":"red"});
		}else{
			$(".active-life .wine").prev().css({"color":"#545454"});
			//
			check = $(".active-life .wine").find(".select-item").hasClass("never");
			if(!check){
				temp = true;
				$(".active-life .wine-item").find(".input-text").each(function(){
					val = $.trim($(this).val());
					if(!val){
						flag = false;
						temp = false;
					}
				});
				//wine-alone
				len = $(".active-life .wine-item").find(".wine-alone .select-item").length;
				if(len < 1){
					flag = false;
					temp = false;
				}
				//check-more
				len = $(".active-life .wine-item").find(".more-check .more_check").length;
				if(len < 1){
					flag = false;
					temp = false;
				}
				if(temp){
					$(".active-life .wine").prev().css({"color":"#545454"});
				}else{
					$(".active-life .wine").prev().css({"color":"red"});
				}
			}else{
				$(".active-life .wine").prev().css({"color":"#545454"});
			}
		}
		//既往史
		$(".active-history .more").each(function(){
			check = $(this).find(".check-no .alone-img").hasClass("alone-check");
			if(check){
				$(this).find(".title").css({"color":"#545454"});
			}else{
				len = $(this).find(".more-check .more_check").length;
				if(len < 1){
					flag = false;
					$(this).find(".title").css({"color":"red"});
				}else{
					$(this).find(".title").css({"color":"#545454"});
				}
			}
		});
		//选择输入
		if(!this.checkInputBtn("operation")){
			flag = false;
		}
		if(!this.checkInputBtn("trauma")){
			flag = false;
		}
		if(!this.checkInputBtn("transfusion")){
			flag = false;
		}
		
		//加载病史
		var check = $(".illness-btn").parents("li").find(".check-no .alone-img").hasClass("alone-check");
		if (check) {
			$(".illness-btn").parents("li").find(".title").css({
				"color" : "#545454"
			});
		} else {
			var temp = true;
			$(".illness").find(".illness-alone").each(function() {
				len = $(this).find(".select-item").length;
				if(len < 1){
					temp = false;
					flag = false;
				}
			});
			$(".illness").find(".illness-more").each(function() {
				len = $(this).find(".more_check").length;
				if(len < 1){
					temp = false;
					flag = false;
				}
			});
			if (temp) {
				$(".illness-btn").parents("li").find(".title").css({
					"color" : "#545454"
				});
			} else {
				$(".illness-btn").parents("li").find(".title").css({
					"color" : "red"
				});
			}
		}
		//遗传病
		var check = $(".active-history .input").find(".check-no .alone-img").hasClass("alone-check");
		if(check){
			$(".active-history li.input").find(".title").css({"color":"#545454"});
		}else{
			val = $(".active-history .input").find(".input-text").val();
			val = $.trim(val);
			if(!val){
				flag = false;
				$(".active-history li.input").find(".title").eq(0).css({"color":"red"});
			}else{
				$(".active-history li.input").find(".title").css({"color":"#545454"});
			}
		}

		//
		$(".active-examine .more").each(function(){
			len = $(this).find(".more_check").length;
			if(len < 1){
				flag = false;
				$(this).find(".title").css({"color":"red"});
			}else{
				$(this).find(".title").css({"color":"#545454"});
			}
		});
		
		$(".active-examine .alone").each(function(){
			len = $(this).find(".select-item").length;
			if(len < 1){
				flag = false;
				$(this).find(".title").css({"color":"red"});
			}else{
				$(this).find(".title").css({"color":"#545454"});
			}
		});
		
		
		$(".active-auxiliary .auxiliary-more").each(function(){
			len = $(this).find(".select-item").length;
			if(len < 1){
				flag = false;
				$(this).find(".title").css({"color":"red"});
			}else{
				$(this).find(".title").css({"color":"#545454"});
			}
		});
		
		
		$(".active-prevent .prevent-more").each(function(){
			len = $(this).find(".select-item").length;
			if(len < 1){
				flag = false;
				$(this).find(".title").css({"color":"red"});
			}else{
				$(this).find(".title").css({"color":"#545454"});
			}
		});
		
		return flag;
		
	},
	checkInputBtn : function(name) {
		var flag_btn = true;
		var flag_check = $("." + name + "-btn").parents("li").find(".check-no .alone-img").hasClass("alone-check");
		if (flag_check) {
			$("." + name + "-btn").parents("li").find(".title").css({
				"color" : "#545454"
			});
		} else {
			var flag_temp = true;
			$("." + name).find(".input-text").each(function() {
				val = $.trim($(this).val());
				if (!val) {
					flag_btn = false;
					flag_temp = false;
				}
			});
			if (flag_temp) {
				$("." + name + "-btn").parents("li").find(".title").css({
					"color" : "#545454"
				});
			} else {
				$("." + name + "-btn").parents("li").find(".title").css({
					"color" : "red"
				});
			}
		}
		return flag_btn;
	}
}

























































