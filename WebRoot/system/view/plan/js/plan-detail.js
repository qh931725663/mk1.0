//
$(function() {
	plan_detail.init();
});
//页面刷新方法关闭弹窗调用
var reload = function() {
	plan_detail.loadData();
}

//
var plan_detail = {
	//初始化
	init : function() {
		//
		this.loadData();
		//绑定
		this.bindClick();
	},
	//重载
	reload : function() {
		this.loadData();
	},
	//加载数据
	loadData : function() {
		var user_id = mkh.getUrlParam('id');
		var self = this;
		//
		$.ajax({
			url : "../../../chronicManage/userPlan.action",
			type : "post",
			dataType : 'json',
			data : {
				user_id : user_id
			},
			success : function(data) {
				if (data) {
					var user_info = data.user_info; //用户信息
					var hypertension = data['item_900002']; //高血压
					var hyperlipidemia = data['item_900003']; //高脂血症
					var diabetes = data['item_900001']; //糖尿病
					var pulmonary = data['item_900004']; //慢性阻塞性肺病
					//加载用户信息
					self.loadUserData(user_info);
					//加载计划
					self.loadPlanData({
						id : '#hypertension',
						items : hypertension
					});
					self.loadPlanData({
						id : '#hyperlipidemia',
						items : hyperlipidemia
					});
					self.loadPlanData({
						id : '#diabetes',
						items : diabetes
					});
					self.loadPlanData({
						id : '#pulmonary',
						items : pulmonary
					});
				}
			}
		});
	},
	//加载用户
	loadUserData : function(info) {
		$('.baseinfo-list').find('span').each(function() {
			var name = $(this).attr('data-name');
			if (name) {
				if (info[name]) {
					if (name == 'is_end') {
						$(this).text(info[name] == '101' ? '完成' : '未完成');
					} else {
						$(this).text(info[name]);
					}
				}
			}
		});
	},
	//加载计划
	loadPlanData : function(plan) {
		var id = plan.id;
		var html = "";
		if (plan.items) {
			$.each(plan.items, function(i, item) {
				var manage_text = "一年不少于" + "<span data-name='check_target'>" + item.check_target + "</span>" + "次" + "<span data-name='manage_text'>" + item.manage_text + "</span>";
				html += "<tr data-id='" + item.order_id + "'><td data-name='manage_text'>" + manage_text + "</td>" +
					"<td>" + "<span data-name='remarks'>" + item.remarks + "</span>" + "</td>" +
					"<td>" + (item.is_end == '101' ? "<span style='color:green'>完成<span>" : "<span style='color:red'>进行中<span>") + "</td>" +
					"<td><a class='plan_show btn btn-primary radius size-MINI'>查看</a>&nbsp;&nbsp;&nbsp;<a class='plan_edit btn btn-primary radius size-MINI'>修改</a></td>" +
					"<td>" + (item.is_end == '101' ? '' : "<a class='plan_follow btn btn-primary radius size-MINI'>跟踪</a>") + "</td></tr>";
			});
			$(id).find('tbody').empty();
			$(id).find('tbody').append(html);
		}
	},
	//新增
	addChronicManager : function() {
		var user_id = mkh.getUrlParam('id');
		layer_show('新增', 'plan-add.html?id=' + user_id, '50', '70');
	},
	//绑定事件
	bindClick : function() {
		//查看
		$('.collection-baseinfo').on('click', '.plan_show', function() {
			//添加选中
			$('table tr').removeClass('selected');
			$(this).parents('tr').addClass('selected');
			var user_id = mkh.getUrlParam('id');
			var order_id = $(this).parents('tr').attr('data-id');
			//
			//layer_show('编辑', 'plan-follow-show.html?id=' + order_id, '43', '50');
			window.location.href = 'plan-follow-show.html?id=' + order_id + "&userId=" + user_id;
		});
		//修改
		$('.collection-baseinfo').on('click', '.plan_edit', function() {
			//添加选中
			$('table tr').removeClass('selected');
			$(this).parents('tr').addClass('selected');
			//
			var order_id = $(this).parents('tr').attr('data-id');
			layer_show('编辑', 'plan-edit.html?id=' + order_id, '50', '60');
		});
		//跟踪
		$('.collection-baseinfo').on('click', '.plan_follow', function() {
			//添加选中
			$('table tr').removeClass('selected');
			$(this).parents('tr').addClass('selected');
			//
			var order_id = $(this).parents('tr').attr('data-id');
			var user_id = mkh.getUrlParam('id');
			layer_show('跟踪', 'plan-follow.html?id=' + order_id + "&userId=" + user_id, '52', '68');
		});
	},
	//加载修改数据
	reloadManagerData : function(data) {
		var len = $('table tr').filter('.selected').length;
		if (len > 0) {
			$('table tr').filter('.selected').find('span').each(function() {
				var name = $(this).attr('data-name');
				$(this).text(data[name]);
			});
		}
	},
	//加载计划
	addManagerData : function(data) {
		//
		var manage_text = "一年不少于" + "<span data-name='check_target'>" + data.check_target + "</span>" + "次" + "<span data-name='manage_text'>" + data.manage_text + "</span>";
		var html = "<tr data-id='" + data.order_id + "'><td data-name='manage_text'>" + manage_text + "</td>" +
			"<td>" + "<span data-name='remarks'>" + data.remarks + "</span>" + "</td>" +
			"<td>" + (data.is_end == '101' ? "<span style='color:green'>完成<span>" : "<span style='color:red'>进行中<span>") + "</td>" +
			"<td><a class='plan_show btn btn-primary radius size-MINI'>查看</a>&nbsp;&nbsp;&nbsp;<a class='plan_edit btn btn-primary radius size-MINI'>修改</a></td>" +
			"<td>" + (data.is_end == '101' ? '' : "<a class='plan_follow btn btn-primary radius size-MINI'>跟踪</a>") + "</td></tr>";
		//
		if (data.chronic_code == '900001') {
			$('#diabetes').find('tbody').append(html);
		} else if (data.chronic_code == '900002') {
			$("#hypertension").find('tbody').append(html);
		} else if (data.chronic_code == '900003') {
			$("#hyperlipidemia").find('tbody').append(html);
		} else if (data.chronic_code == '900004') {
			$("#pulmonary").find('tbody').append(html);
		}

	}
};