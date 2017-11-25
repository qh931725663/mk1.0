//初始化
$(function() {
	//
	measure.init();
});
//
var measure = {
	init : function() {
		var type = mkh.getUrlParam('type');
		if (type == 'add') {
			this.init_add();
		} else if (type == 'edit') {
			this.init_edit();
		}
	},
	//初始化编辑
	init_edit : function() {
		var order_id = mkh.getUrlParam('orderId');
		if (order_id) {
			//绑定选择事件
			this.bind_measure_type_select();
			//时间选择
			this.init_time_select();
			//
			this.init_measure_type().done(function() {
				measure.edit_data(order_id);
			});
			//
			this.measure_save();

		}
	},
	edit_data : function(order_id) {
		var that = this;
		//
		mkh.ajax({
			url : "../../../remind/message/getMeasure.action",
			type : "get",
			dataType : 'json',
			data : {
				order_id : order_id
			},
			success : function(data) {
				//
				if (data) {
					//数据显示
					that.show_data(data);
				}
			}
		});
	},
	//数据显示
	show_data : function(data) {
		$('#measrtrment_remind_type').attr('disabled', true);
		//测量类型显示
		$('#measrtrment_remind_type').find('option').each(function() {
			if ($(this).val() == data.measrtrment_remind_type) {
				$(this).attr('selected', 'selected');
				return false;
			}
		});
		//重复显示
		var val = data.measrtrment_remind_type;
		if (val == '1400006' || val == '1400011') { //体重和尿液
			$('#multiple-select').empty();
			$('#multiple-select').removeAttr('multiple');
			$('#multiple-select').append(this.day_data());
			this.init_repeat_select();
		} else {
			$('#multiple-select').empty();
			$('#multiple-select').attr('multiple', "multiple");
			$('#multiple-select').append(this.week_data());
			this.init_repeat_select();
		}
		var arr = new Array();
		if (data.measrtrment_remind_type == '1400006' || data.measrtrment_remind_type == '1400011') {
			arr.push(data.interval_day);
		} else {
			if (data.monday) {
				arr.push(1);
			}
			if (data.tuesday) {
				arr.push(2);
			}
			if (data.wednesday) {
				arr.push(3);
			}
			if (data.thursday) {
				arr.push(4);
			}
			if (data.friday) {
				arr.push(5);
			}
			if (data.saturday) {
				arr.push(6);
			}
			if (data.sunday == 0) {
				arr.push(0);
			}
		}

		$('#multiple-select').multiselect('select', arr);

		//显示时间
		if (data.time1) {
			select_time('#time1', data.time1);
		}
		if (data.time2) {
			select_time('#time2', data.time2);
		}
		if (data.time3) {
			select_time('#time3', data.time3);
		}
		if (data.time4) {
			select_time('#time4', data.time4);
		}
		//开始时间
		$('#begin_day').val(data.begin_day);
		//
		function select_time(selector, time) {
			$(selector).find('option').each(function() {
				if ($(this).val() == time) {
					$(this).attr('selected', 'selected');
					return false;
				}
			});
		}
	},
	//初始化添加
	init_add : function() {
		//测量类型初始化
		this.init_measure_type();
		//
		this.measure_save();
		//
		this.bind_measure_type_select();
		//时间选择
		this.init_time_select();
	},
	//关闭窗口 
	cancel_open : function() {
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	},
	//保存提醒
	measure_save : function() {
		//
		$('#measure_save').on('click', function() {
			//验证
			var ret = measure.measure_tip();
			if (!ret) {
				return;
			}
			//保存
			var param = {};
			//ID
			var order_id = mkh.getUrlParam('orderId');
			if (order_id) {
				param.order_id = order_id;
			}
			//类型
			var measrtrment_remind_type = $('#measrtrment_remind_type').val();
			param.measrtrment_remind_type = measrtrment_remind_type;
			//日期
			$('#multiple-select option:selected').each(function() {
				var val = $(this).val();
				if (measrtrment_remind_type == '1400006' || measrtrment_remind_type == '1400011') {
					param.interval_day = val;
				} else {
					if (val == 1)
						param.monday = val;
					else if (val == 2)
						param.tuesday = val;
					else if (val == 3)
						param.wednesday = val;
					else if (val == 4)
						param.thursday = val;
					else if (val == 5)
						param.friday = val;
					else if (val == 6)
						param.saturday = val;
					else if (val == 0)
						param.sunday = val;
				}

			});
			//时间
			$('#time_select select').each(function(i) {
				var val = $(this).val();
				var time = 'time';
				if (val) {
					time = time + (i + 1);
					param[time] = val;
				}
			});
			//开始时间
			var begin_day = $('#begin_day').val();
			param.begin_day = begin_day;
			//用户
			param.user_id = mkh.getUrlParam('id');
			//保存
			mkh.ajax({
				url : "../../../remind/message/saveOrUpdateMeasure.action",
				type : "get",
				dataType : 'json',
				data : param,
				success : function(data) {
					//
					if (data.flag) {
						layer.alert('保存成功', function() {
							parent.location.reload();
							//关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						});
					}
				}
			});
		});
	},
	//提示
	measure_tip : function() {
		var flag = true;
		//测量类型
		var check = this.check_tip('#measrtrment_remind_type');
		if (!check) {
			flag = false;
		}
		//重复
		if ($('#multiple-select').length > 0 && $('.btn-group').length <= 0) {
			check = this.check_tip('#multiple-select');
			if (!check) {
				flag = false;
			}
		}
		var len = "";
		if ($('.btn-group').length > 0) {
			layer.close($('#multiple-select').data('tipIndex'));
			$('#multiple-select').removeData('hasTip').removeData('tipIndex');
			//
			len = $('#multiple-select option:selected').length;
			check = this.check_len_tip($('.btn-group'), len);
			if (!check) {
				flag = false;
			}
		}
		//多个时间选择
		len = $('.select-style2').filter(function() {
			//
			var val = $(this).val();
			val = $.trim(val);
			return !!val;
		}).length;
		var $select = $('.select-style2').last();
		//
		check = this.check_len_tip($select, len);
		if (!check) {
			flag = false;
		}
		//开始时间
		check = this.check_tip('#begin_day');
		if (!check) {
			flag = false;
		}
		return flag;
	},
	//
	check_tip : function(selector) {
		var flag = true;
		var val = $(selector).val();
		val = $.trim(val);
		//
		var hasTip = $(selector).data('hasTip');

		if (val) {
			if (hasTip) {
				layer.close($(selector).data('tipIndex'));
				$(selector).removeData('hasTip').removeData('tipIndex');
			}
		} else {
			if (!hasTip) {
				var index = layer.tips('请输入内容', $(selector), {
					time : 0,
					tipsMore : true
				});
				$(selector).data('hasTip', true).data('tipIndex', index);
			}
			//空值
			flag = false;
		}
		return flag;
	},
	//
	check_len_tip : function($select, len) {
		var flag = true;
		//
		if (len > 0) {
			layer.close($select.data('tipIndex'));
			$select.removeData('hasTip').removeData('tipIndex');
		} else {
			var hasTip = $select.data('hasTip');
			if (!hasTip) {
				var item_index = layer.tips('请输入内容', $select, {
					time : 0,
					tipsMore : true
				});
				$select.data('hasTip', true).data('tipIndex', item_index);
			}
			//
			flag = false;
		}
		return flag;
	},
	//清除tipsData
	clear_tips : function() {
		//移出普通文本Data
		$('.select-style').each(function() {
			$(this).removeData('hasTip').removeData('tipIndex');
		});
		//移出并列的最后一个文本Data
		$('.select-style2').last().removeData('hasTip').removeData('tipIndex');
	},
	//测量类型初始化
	init_measure_type : function() {
		var $type = $('#measrtrment_remind_type');
		//
		var user_id = mkh.getUrlParam('id');
		if (user_id) {
			var type = mkh.getUrlParam('type');
			if (type == 'edit') {
				var measrtrment_remind_type = $('#measrtrment_remind_type').attr('data-type');
				if (measrtrment_remind_type) {
					return mkh.ajax({
						url : "../../../unicode/type.action",
						type : "get",
						dataType : 'json',
						data : {
							type : measrtrment_remind_type
						},
						success : function(data) {
							if (data.status === 1) {
								var items = data.data;
								var html = "<option value=''>全部</option>";
								$.each(items, function(i, item) {
									html += "<option value='" + item.order_id + "'>" + item.unicode_name + "</option>";
								});
								$('#measrtrment_remind_type').empty();
								$('#measrtrment_remind_type').append(html);
							}
						}
					});
				}
			} else {
				return $.ajax({
					url : "../../../remind/message/getMeasureTypeList.action",
					type : "get",
					dataType : 'json',
					data : {
						user_id : user_id
					},
					success : function(data) {
						if (data) {
							var items = data;
							var html = "<option value='' selected>全部</option>";
							$.each(items, function(i, item) {
								if ('1400001' != item.order_id)
									html += "<option value='" + item.order_id + "'>" + item.unicode_name + "</option>";
							});
							$type.empty();
							$type.append(html);
						}
					}
				});
			}
		}
		return $.Deferred();
	},
	init_repeat_select : function() {
		$('#multiple-select').multiselect('destroy');
		$('#multiple-select').multiselect({
			includeSelectAllOption : true, //全选  
			nonSelectedText : '请选择', //没有值的时候button显示值  
			allSelectedText : '全部', //所有被选中的时候 全选（n）  
			nSelectedText : '个被选中', //有n个值的时候显示n个被选中  
			selectAllText : '全选',
			buttonWidth : '150px',
			buttonContainer: "<div class='btn-group' />"
		});
	},
	//周选择数据
	week_data : function() {
		var data = "";
		data += "<option value='1'>星期一</option>" +
			"<option value='2'>星期二</option>" +
			"<option value='3'>星期三</option>" +
			"<option value='4'>星期四</option>" +
			"<option value='5'>星期五</option>" +
			"<option value='6'>星期六</option>" +
			"<option value='0'>星期日</option>";
		return data;
	},
	//天数数据
	day_data : function() {
		var data = "<option value=''>请选择</option>";
		for (var i = 7; i <= 60; i++) {
			data += "<option value='" + i + "'>" + i + "天</option>";
		}
		return data;
	},
	//绑定选择事件
	bind_measure_type_select : function() {
		var that = this;
		$('#measrtrment_remind_type').on('change', function() {
			var val = $(this).val();
			if (val == '1400006' || val == '1400011') { //体重和尿液
				$('#multiple-select').empty();
				$('#multiple-select').removeAttr('multiple');
				$('#multiple-select').append(that.day_data());
				that.init_repeat_select();
			} else {
				$('#multiple-select').empty();
				$('#multiple-select').attr('multiple', "multiple");
				$('#multiple-select').append(that.week_data());
				that.init_repeat_select();

			}
		});
	},
	//小时数据
	hour_data : function() {
		var data = "<option value=''>请选择</option>";
		for (var i = 0; i < 24; i++) {
			if (i < 10) {
				i = '0' + i;
			}
			data += "<option value='" + i + ":00:00'>" + i + ":00</option>";
		}
		return data;
	},
	//时间选择
	init_time_select : function() {
		var html = this.hour_data();
		$('#time_select select').each(function() {
			$(this).empty();
			$(this).append(html);
		});
	}
};