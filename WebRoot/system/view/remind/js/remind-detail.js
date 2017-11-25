//
var remind = {
	//初始化
	init : function() {
		//添加事件
		this.measure_add();
		//修改事件
		this.measure_edit();
		//加载数据
		this.init_measure();
		//绑定删除
		this.measure_delete();

	},
	//修改
	measure_edit : function() {
		var user_id = mkh.getUrlParam('id');
		$('span.edit-span').on('click', function() {
			var orderId = $(this).parent().find('.show_data').data('orderId');
			if (orderId) {
				//
				layer.open({
					type : 2,
					content : 'measure-detail.html?id=' + user_id + "&type=edit" + "&orderId=" + orderId,
					area : [ '50%', '50%' ],
					title : '修改'
				});
			} else {
				layer.msg('未添加提醒');
			}
		});
	},
	//删除
	measure_delete : function() {
		var user_id = mkh.getUrlParam('id');
		$('span.delete-span').on('click', function() {
			var orderId = $(this).parent().find('.show_data').data('orderId');
			if (orderId) {
				//删除
				layer.confirm('你确定删除吗？', {
					btn : [ '确定', '取消' ]
				}, function(index, layero) {
					//删除
					mkh.ajax({
						url : "../../../remind/message/deleteMeasure.action",
						type : "post",
						dataType : 'json',
						data : {
							order_id : orderId
						},
						success : function(data) {
							//
							if (data == 1) {
								//加载数据
								window.location.reload();
								//关闭窗口
								layer.close(index);
								layer.msg('删除成功');
							}
						}
					});
				});
			} else {
				layer.msg('未添加提醒');
			}
		});
	},
	//新增
	measure_add : function() {
		$('span.add-span').on('click', function() {
			var user_id = mkh.getUrlParam('id');
			//
			layer.open({
				type : 2,
				content : 'measure-detail.html?id=' + user_id + "&type=add",
				area : [ '50%', '50%' ],
				title : '新增'
			});
		});
	},
	//加载数据
	init_measure : function() {
		var id = mkh.getUrlParam('id');
		mkh.ajax({
			url : "../../../remind/message/getMeasureList.action",
			type : "post",
			dataType : 'json',
			data : {
				user_id : id
			},
			success : function(data) {
				if (data && data.length > 0) {
					//加载数据
					remind.load_measure_data(data);
				}
			}
		});
	},
	//加载数据
	load_measure_data : function(data) {
		//
		$.each(data, function(i, item) {
			var measrtrment_remind_type = item.measrtrment_remind_type;
			$('div.content').each(function() {
				var type = $(this).attr('data-type');
				if (type == measrtrment_remind_type) {
					remind.measure_show(item, $(this));
					return false;
				}
			});
		});
	},
	measure_show : function(item, $content) {
		var html = "测量时间（";
		if (item.measrtrment_remind_type == '1400006' || item.measrtrment_remind_type == '1400011') {
			html += item.interval_day + '天&nbsp;&nbsp;';
		} else {
			if (item.monday)
				html += '周一 &nbsp;&nbsp;';
			if (item.tuesday)
				html += '周二&nbsp;&nbsp;';
			if (item.wednesday)
				html += '周三&nbsp;&nbsp;';
			if (item.thursday)
				html += '周四&nbsp;&nbsp;';
			if (item.friday)
				html += '周五&nbsp;&nbsp;';
			if (item.saturday)
				html += '周六&nbsp;&nbsp;';
			if (item.sunday == 0)
				html += '周日&nbsp;&nbsp;';
		}
		html = html.substring(0, html.length - '&nbsp;&nbsp;'.length);
		//
		html += '）&nbsp;&nbsp;&nbsp;';
		//
		if (item.time1) {
			html = show_time(html, item.time1);
		}
		if (item.time2) {
			html = show_time(html, item.time2);
		}
		if (item.time3) {
			html = show_time(html, item.time3);
		}
		if (item.time4) {
			html = show_time(html, item.time4);
		}
		//时间显示
		function show_time(html, time) {
			time = time.substring(0, time.lastIndexOf(':'))
			return html += time + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		//
		$content.find('.show_data').html(html);
		$content.find('.show_data').data('orderId', item.order_id);
	},
	//选中
	reloadMedicineData : function() {
		remind.medicine('initItemData');
	},
	reloadDoctorData : function() {
		remind.doctor('initItemData');
	}
};