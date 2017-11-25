//闭包块
(function(m, $) {
	//命名空间
	var namespace = 'medicine';
	//默认配置
	var defaults = {
		//药品名称
		medicineName : "<option value='阿司匹林肠溶片'>阿司匹林肠溶片</option>",
		//规格
		specificationName : "<option value='1g/颗'>1g/颗</option>" +
			"<option value='150ml/瓶'>150ml/瓶</option>",
		//单位
		unit : "<option value='g'>g</option>" +
			"<option value='ml'>ml</option>"
	};
	//方法
	var methods = {
		init : $.noop,
		//初始化编辑页面
		initDetail : function() {
			//绑定添加方法
			this._bindSave();
			//
			var type = mkh.getUrlParam('type');
			if (type == 'edit') {
				//初始化选项
				this._initSelect();
				this._loadMedicineData();
			} else {
				//初始化选项
				this._initSelect();
				//慢病类型/用法
				this._chronicTypeData();
			}
		},
		//初始化显示
		initItem : function() {
			this._bindAdd();
			//显示数据
			this._initItemData();
			//绑定编辑事件
			this._bindEdit();
			//绑定删除
			this._bindDelete();
		},
		//初始化显示
		initItemData : function() {
			//显示数据
			this._initItemData();
		},
		//初始化药品
		_initSearchSelect : function() {
			$('#drug_id').multiselect({
				placeholder : "请选择",
				enableFiltering : true,
				buttonWidth : '332px',
				filterPlaceholder : '请输入搜索'
			});
		},
		//加载药品
		_loadDrugData : function() {
			var that = this;
			mkh.ajax({
				url : "../../../remind/message/getDrugList.action",
				type : "post",
				dataType : 'json',
				data : {},
				success : function(data) {
					if (data) {
						var html = "<option value='' selected>请选择</option>";
						$.each(data, function(i, item) {
							html += "<option value='" + item.order_id + "'>" + item.Generic_drug_name + "</option>";
						});
						$('#drug_id').append(html);
						that._initSearchSelect();
					}
				}
			});
		},
		//加载用药数据
		_loadMedicineData : function() {
			var order_id = mkh.getUrlParam('order_id');
			if (order_id) {
				$.when(this._chronicTypeData()).done(function() {
					mkh.ajax({
						url : "../../../remind/message/getMedicine.action",
						type : "post",
						dataType : 'json',
						data : {
							order_id : order_id
						},
						success : function(data) {
							//下拉
							$('select').each(function() {
								var name = this.name;
								$(this).find('option').each(function() {
									if ($(this).val() == data[name]) {
										$(this).attr('selected', 'selected');
									}
								});
							});
							//文本
							$('.input-text').each(function() {
								var name = this.name;
								if (data[name]) {
									$(this).val(data[name]);
								}
							});
						}
					});
				});
			}
		},
		//编辑事件
		_bindEdit : function() {
			$('.medicine-content').on('click', 'a.medicine_edit', function() {
				var order_id = $(this).parents('tr').attr('data-id');
				layer.open({
					type : 2,
					content : 'medicine-detail.html?order_id=' + order_id + "&type=edit",
					area : [ '50%', '68%' ],
					title : '修改'
				});
			});
		},
		//绑定删除
		_bindDelete : function() {
			$('.medicine-content').on('click', 'a.medicine_delete', function() {
				var order_id = $(this).parents('tr').attr('data-id');
				layer.confirm('你确定删除吗？', {
					btn : [ '确定', '取消' ]
				}, function(index, layero) {
					//删除
					mkh.ajax({
						url : "../../../remind/message/deleteMedicine.action",
						type : "post",
						dataType : 'json',
						data : {
							order_id : order_id
						},
						success : function(data) {
							//
							if(data == 1){
								//加载数据
								remind.reloadMedicineData();
								//关闭窗口
								layer.close(index);
								layer.msg('删除成功');
							}
						}
					});
				});
			});
		},
		//绑定添加方法
		_bindAdd : function() {
			$('.medicine-span', '.tabCon').on('click', function() {
				var user_id = mkh.getUrlParam('id');
				layer.open({
					type : 2,
					content : 'medicine-detail.html?id=' + user_id + "&type=add",
					area : [ '50%', '68%' ],
					title : '添加'
				});
			});
		},
		//显示数据
		_initItemData : function() {
			var that = this;
			var user_id = mkh.getUrlParam('id');
			return mkh.ajax({
				url : "../../../remind/message/getMedicineList.action",
				type : "post",
				dataType : 'json',
				data : {
					user_id : user_id
				},
				success : function(data) {
					//
					if (data) {
						that._clearItemData();
						$.each(data, function(i, item) {
							var chronic_code = item.chronic_code;
							if (chronic_code == '900002') {
								//高血压
								$('#highBlood').find('tbody').append(that._loadItemData(item));
							} else if (chronic_code == '900001') {
								//糖尿病
								$('#diabetes').find('tbody').append(that._loadItemData(item));
							} else if (chronic_code == '900003') {
								//高血脂
								$('#hyperlipidemia').find('tbody').append(that._loadItemData(item));
							} else if (chronic_code == '900004') {
								//慢性肺病
								$('#lungDisease').find('tbody').append(that._loadItemData(item));
							}
						});
					}
				}
			});
		},
		_clearItemData : function() {
			$('#highBlood').find('tbody').empty();
			$('#diabetes').find('tbody').empty();
			$('#hyperlipidemia').find('tbody').empty();
			$('#lungDisease').find('tbody').empty();
		},
		//绑定保存
		_bindSave : function() {
			var that = this;
			$('#medicine_save').on('click', function() {
				var ret = that._checkInput();
				if (ret) {
					that._medicineSaveOrUpate();
				}
			});
		},
		//加载用药数据
		_loadItemData : function(item) {
			//显示时间
			var one = item.medication_remind_time_one;
			var two = item.medication_remind_time_two;
			var three = item.medication_remind_time_three;
			var four = item.medication_remind_time_four;
			one = one ? (one.substring(0, one.lastIndexOf(':')) + "&nbsp;&nbsp;") : '';
			two = two ? (two.substring(0, two.lastIndexOf(':')) + "&nbsp;&nbsp;") : '';
			three = three ? (three.substring(0, three.lastIndexOf(':')) + "&nbsp;&nbsp;") : '';
			four = four ? (four.substring(0, four.lastIndexOf(':')) + "&nbsp;&nbsp;") : '';
			var time = one + two + three + four;
			//
			var html = "<tr data-id='" + item.order_id + "'><td>" + item.chronic_name + "</td><td>" + item.Generic_drug_name + "</td>" +
				"<td>" + item.drug_specification + "</td><td>" + item.drug_note + "</td>" +
				"<td>" + item.medication_remind_num + "</td><td>" + item.medication_remind_dosage + "</td>" +
				"<td>" + time + "</td><td><a class='medicine_edit btn btn-primary radius size-MINI'>修改</a>&nbsp;&nbsp;" +
				"<a class='medicine_delete btn btn-primary radius size-MINI'>删除</a></td></tr>";
			return html
		},
		//保存或者更新
		_medicineSaveOrUpate : function() {
			var order_id = mkh.getUrlParam('order_id');
			if (order_id) {
				//更新
				this._medicineEdit(order_id);
			} else {
				//保存方法
				this._medicineSave();
			}
		},
		//更新方法
		_medicineEdit : function(order_id) {
			var param = {}
			$(':input').each(function() {
				if ($(this).val())
					param[this.name] = $(this).val();
			});
			//
			param.order_id = order_id;
			//调用后台方法
			return mkh.ajax({
				url : "../../../remind/message/saveOrUpdateMedicine.action",
				type : "post",
				dataType : 'json',
				data : param,
				success : function(data) {
					//
					if (data) {
						layer.alert('保存成功', function() {
							//
							parent.remind.reloadMedicineData();
							//关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						//
						});
					}
				}
			});
		},
		//执行保存
		_medicineSave : function() {
			var param = {}
			$(':input').each(function() {
				param[this.name] = $(this).val();
			});
			var user_id = mkh.getUrlParam('id');
			param.user_id = user_id;
			//调用后台方法
			return mkh.ajax({
				url : "../../../remind/message/saveOrUpdateMedicine.action",
				type : "post",
				dataType : 'json',
				data : param,
				success : function(data) {
					//
					if (data) {
						layer.alert('保存成功', function() {
							parent.remind.reloadMedicineData();
							//关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						//
						});
					}
				}
			});
		},
		//验证
		_checkInput : function() {
			var flag = true;
			//验证选择框
			$('.select-style', '.row').each(function() {
				var val = $(this).val();
				if (val) {
					mkh.errTipsRemove('#' + $(this).attr('id'));
				} else {
					mkh.errTips('#' + $(this).attr('id'), "不能为空！");
					flag = false;
				}
			});
			//时间选择
			var len = 0;
			$('.select-style2', '.row').each(function() {
				if ($(this).val()) {
					len++;
				}
			});
			if (len > 0) {
				mkh.errTipsRemove('#medication_remind_time_four');
			} else {
				mkh.errTips('#medication_remind_time_four', "不能为空！");
				flag = false;
			}
			//验证文本
			$('.input-text').each(function() {
				var val = $(this).val();
				val = $.trim(val);
				if (val) {
					mkh.errTipsRemove('#' + $(this).attr('id'));
				} else {
					mkh.errTips('#' + $(this).attr('id'), "不能为空！");
					flag = false;
				}
			});
			return flag;
		},
		//初始化选择参数
		_initSelect : function() {
			//
			$('#medication_remind_time_one').append(this._hourData());
			$('#medication_remind_time_two').append(this._hourData());
			$('#medication_remind_time_three').append(this._hourData());
			$('#medication_remind_time_four').append(this._hourData());
			//
			$('#medication_remind_num').append(this._numData());

		},
		//慢病类型
		_chronicTypeData : function() {
			return mkh.ajax({
				url : "../../../remind/message/getUnicodeList.action",
				type : "post",
				dataType : 'json',
				data : {},
				success : function(data) {
					var items = data;
					var chronic_code_type = $('#chronic_code').attr('data-type');
					var drug_note_type = $('#drug_note').attr('data-type');
					$.each(items, function(i, item) {
						var html = "<option value='" + item.order_id + "'>" + item.unicode_name + "</option>";
						if (item.unicode_type == chronic_code_type) {
							$('#chronic_code').append(html);
						} else if (item.unicode_type == drug_note_type) {
							$('#drug_note').append(html);
						}
					});

				}
			});
		},
		//初始化次数
		_numData : function() {
			var data = "<option value=''>请选择</option>";
			for (var i = 1; i < 6; i++) {
				data += "<option value='" + i + "'>" + i + "</option>";
			}
			return data;
		},
		//初始化小时
		_hourData : function() {
			var data = "<option value=''>请选择</option>";
			for (var i = 0; i < 24; i++) {
				if (i < 10) {
					i = '0' + i;
				}
				data += "<option value='" + i + ":00:00'>" + i + ":00</option>";
			}
			return data;
		},
		//关闭窗口
		cancel_window : function() {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		},
		//销毁方法
		destory : $.noop
	};

	//方法入口
	m.medicine = function(method) {
		//私有方法
		if ($.type(method) === "string" && method.charAt(0) === "_") {
			return $.error("'" + namespace + "'" + "没有此方法");
		}
		//
		if (methods[method]) {
			return methods[method].apply(methods, Array.prototype.slice.call(arguments, 1));
		} else if ($.type(method) === 'object' || method === undefined) {
			return methods.init.apply(methods, arguments);
		} else {
			$.error('方法不存在');
		}
	};

})(remind, jQuery);