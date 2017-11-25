
//
var HEALTH_VIEW = {
	url : "",
	loadData : function() {
		var that = this;
		var order_id = this.getUrlParam("orderId");
		if (order_id) {
			return $.ajax({
				url : that.url,
				type : "post",
				dataType : 'json',
				data : {
					order_id : order_id
				},
				success : function(data) {
					if (data.status) {
						that.initData(data.data);
					}
				}
			});
		}
	},
	//初始化数据
	initData : function(data) {
		var that = this;
		$(".write .view-data").each(function() {
			var name = $(this).attr("data-name");
			if (data[name]){
				$(this).text(data[name]);
				$(this).removeClass("view-no");
			}
		});
		//显示照片
		that.viewPic(data.piclist);
	},
	//显示照片
	viewPic : function(pics) {
		if (pics.length > 0) {
			$.each(pics, function(i, v) {
				var html = "<div class='z_addImg'><img src='" + v.medical_picture_upload + "'></div>";
				$(".z_photo").append(html);
			});
		}
	},
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
};