$(function() {
	HEALTH_ARCHIVES.init();
});

var HEALTH_ARCHIVES = {
	init : function() {
		this.bindClick();
		this.loadData();
	},
	//绑定点击事件
	bindClick : function() {
		$(".nav li").eq(0).on("click", function() {
			window.location.href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/checkAdd.html";
		});
		$(".nav li").eq(1).on("click", function() {
			window.location.href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/examineAdd.html";
		});
		$(".nav li").eq(2).on("click", function() {
			window.location.href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/hospitalAdd.html";
		});
		$(".nav li").eq(3).on("click", function() {
			window.location.href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/medicalAdd.html";
		});
	},
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},
	//加载数据
	loadData : function() {
		var that = this;
		return $.ajax({
			url : "/mkh1.0/wx/healthRecord/queryList.action",
			type : "post",
			dataType : 'json',
			data : {},
			success : function(data) {
				if (data.status) {
					if(data.data.length > 0){
						that.initData(data.data);
					}else{
						$(".content").append("<div class='data-no'>暂无数据~</div>");
					}
				}else{
					$(".content").append("<div class='data-no'>暂无数据~</div>");
				}
			}
		});
	},
	//初始化数据
	initData : function(data) {
		var years = $.map(data, function(i, v) {
			var create_date = i.create_date;
			create_date = create_date.substr(0, 4);
			return create_date;
		});
		years = this.arrUnique(years);
		$.each(years, function(i, v) {
			var html = "<div class='year'>" + v + "</div><ul class='item-list' data-time='" + v + "'></ul></div>";
			$(".content").append(html);
		});
		//加载数据
		this.initItem(data);
	},
	//加载明细
	initItem : function(data) {
		var that = this;
		$(".content .item-list").each(function() {
			var year = $(this).attr("data-time");
			var $item = $(this);
			$.each(data, function(i, v) {
				var create_date = v.create_date;
				create_date = create_date.substr(0, 4);
				if (year == create_date) {
					//日期
					var date = v.create_date.substr(5, 5);
					//图片
					var imgsrc = "";
					var num1 = "";
					var href = "";
					var num3= "";
					if (v.type == "500001") {
						num1 = "体检报告-" + v.hosp_name;
						num3 = "体检时间：" + v.time;
						imgsrc = "image/tj-img.png";
						href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/checkView.html?orderId=" + v.order_id;
					} else if (v.type == "500002") {
						num1 = "门诊报告-" + v.hosp_name;
						num3 = "就诊时间：" + v.time;
						imgsrc = "image/mz-img.png";
						href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/examineView.html?orderId=" + v.order_id;
					} else if (v.type == "500003") {
						num1 = "住院报告-" + v.hosp_name;
						num3 = "住院时间：" + v.time;
						imgsrc = "image/zy-img.png";
						href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/hospitalView.html?orderId=" + v.order_id;
					} else if (v.type == "500004") {
						num1 = "医保记录-" + v.hosp_name;
						num3 = "报销时间：" + v.time;
						imgsrc = "image/yb-img.png";
						href = "/mkh1.0/system/view/wechat/myHealth/healthArchives/medicalView.html?orderId=" + v.order_id;
					}
					//
					var num2 = "姓名：" + v.user_name;
					that.createItem($item, date, imgsrc, num1, num2, num3, href);
				}
			});
		});
	},
	createItem : function(item, date, imgsrc, num1, num2, num3, href) {
		var html = "";
		html += "<a href='" + href + "'><li class='item'>"
			+ "<div class='time'>" + date + "</div>"
			+ "<img class='item-pic' alt='' src='" + imgsrc + "'>"
			+ "<div class='data'>"
			+ "<div class='view'>"
			+ "<p>" + num1 + "</p>"
			+ "<p>" + num2 + "</p>"
			+ "<p>" + num3 + "</p>"
			+ "</div></div><img class='jt' alt='' src='image/jt.png'></li></a>";

		item.append(html);
	},
	//数组去重
	arrUnique : function(arr) {
		var res = [];
		var json = {};
		for (var i = 0; i < arr.length; i++) {
			if (!json[arr[i]]) {
				res.push(arr[i]);
				json[arr[i]] = 1;
			}
		}
		return res;
	}
}