//闭包块
(function(m, $) {
	//命名空间
	var namespace = 'doctor';
	//默认配置
	var defaults = {};
	//方法
	var methods = {
		init : $.noop,
		initDetail : function() {
			//时间
			this._hourData();
			//
			this._bindSaveOrUpdate();
			//初始化数据
			var type = mkh.getUrlParam('type');
			if (type == 'edit') {
				this._loadDoctorData();
			} else {
				//初始化医院选择
				this._initHostipalList();
				//初始化科室选择
				this._initDefaultDepartSelect();
			}

		},
		initItem : function() {
			//绑定添加
			this._bindAdd();
			//加载数据
			this._loadItemData();
			//绑定编辑
			this._bindDoctorEdit();
			//绑定删除
			this._bindDoctorDelete();
		},
		initItemData : function() {
			//加载数据
			this._loadItemData();
		},
		_loadDoctorData : function() {
			var that = this;
			var order_id = mkh.getUrlParam('doctorId');
			mkh.ajax({
				url : "../../../remind/message/getDoctor.action",
				type : "post",
				dataType : 'json',
				data : {
					order_id : order_id
				},
				success : function(data) {
					var hosp_order_id = data.hosp_order_id;
					//初始化医院选择
					that._initHostipalList(hosp_order_id);
					//
					var department_id = data.department_id;
					that._initDepartList(hosp_order_id, department_id);
					//
					$('#doctor_remind_date').val(data.doctor_remind_date);
					//
					$('#doctor_remind_time').val(data.doctor_remind_time);
				}
			});
		},
		//修改
		_bindDoctorEdit : function() {
			$('.doctor-content').on('click', '.doctor_edit', function() {
				var order_id = $(this).parents('tr').attr('data-id');
				layer.open({
					type : 2,
					content : 'doctor-detail.html?doctorId=' + order_id + "&type=edit",
					area : [ '50%', '50%' ],
					title : '修改'
				});
			});
		},
		//删除
		_bindDoctorDelete : function() {
			$('.doctor-content').on('click', '.doctor_delete', function() {
				var order_id = $(this).parents('tr').attr('data-id');
				layer.confirm('你确定删除吗？', {
					btn : [ '确定', '取消' ]
				}, function(index, layero) {
					//删除
					mkh.ajax({
						url : "../../../remind/message/deleteDoctor.action",
						type : "post",
						dataType : 'json',
						data : {
							order_id : order_id
						},
						success : function(data) {
							//
							if (data == 1) {
								//加载数据
								remind.reloadDoctorData();
								//关闭窗口
								layer.close(index);
								layer.msg('删除成功');
							}
						}
					});
				});
			});
		},
		//绑定添加或者删除
		_bindSaveOrUpdate : function() {
			var that = this;
			$('#doctor_save').on('click', function() {
				var ret = that._checkInput();
				if (ret) {
					var order_id = mkh.getUrlParam('doctorId');
					if (order_id) {
						that._doUpdate();
					} else {
						that._doSave();
					}
				}
			});
		},
		_checkInput : function() {
			var flag = true;
			//就诊日期
			var doctor_remind_date = $('#doctor_remind_date').val();
			console.log(doctor_remind_date)
			if (doctor_remind_date) {
				mkh.errTipsRemove('#doctor_remind_date');
			} else {
				mkh.errTips('#doctor_remind_date', "不能为空！");
				flag = false;
			}
			//就诊时间
			var doctor_remind_time = $('#doctor_remind_time').val();
			if (doctor_remind_time) {
				mkh.errTipsRemove('#doctor_remind_time');
			} else {
				mkh.errTips('#doctor_remind_time', "不能为空！");
				flag = false;
			}
			//
			$('#hosp_order_id').next('.btn-group').attr('id', 'hospital');
			$('#hosp_order_id option:selected').each(function() {
				var val = $(this).val();
				if (val == '') {
					mkh.errTips('#hospital', "不能为空！");
					flag = false;
				} else {
					mkh.errTipsRemove('#hospital');
				}
			});
			$('#department_id').next('.btn-group').attr('id', 'department');
			$('#department_id option:selected').each(function() {
				var val = $(this).val();
				if (val == '') {
					mkh.errTips('#department', "不能为空！");
					flag = false;
				} else {
					mkh.errTipsRemove('#department');
				}
			});
			return flag;
		},
		//加载数据
		_loadItemData : function() {
			var user_id = mkh.getUrlParam('id');
			mkh.ajax({
				url : "../../../remind/message/getDoctorList.action",
				type : "post",
				dataType : 'json',
				data : {
					user_id : user_id
				},
				success : function(data) {
					if (data) {
						$('.doctor-content').find('tbody').empty();
						$.each(data, function(i, item) {
							var html = "<tr data-id='" + item.order_id + "'><td>" + item.doctor_remind_date + "</td>" +
								"<td>" + item.doctor_remind_time + "</td>" +
								"<td>" + item.doctor_remind_hospital + "</td>" +
								"<td>" + item.doctor_remind_department + "</td>" +
								"<td><a class='doctor_edit btn btn-primary radius size-MINI'>修改</a>&nbsp;&nbsp;" +
								"<a class='doctor_delete btn btn-primary radius size-MINI'>删除</a></td></tr>"
							$('.doctor-content').find('tbody').append(html);
						});

					}
				}
			});
		},
		//保存
		_doSave : function() {
			var param = {};
			param.patient_id = mkh.getUrlParam('id');
			//
			var doctor_remind_date = $('#doctor_remind_date').val();
			param.doctor_remind_date = doctor_remind_date;
			var doctor_remind_time = $('#doctor_remind_time').val();
			param.doctor_remind_time = doctor_remind_time;
			$('#hosp_order_id option:selected').each(function() {
				var hosp_order_id = $(this).val();
				param.hosp_order_id = hosp_order_id;
			});
			$('#department_id option:selected').each(function() {
				var department_id = $(this).val();
				param.department_id = department_id;
			});
			//
			mkh.ajax({
				url : "../../../remind/message/saveOrUpdateDoctor.action",
				type : "post",
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data) {
						layer.alert('保存成功', function() {
							//
							parent.remind.reloadDoctorData();
							//关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						//
						});
					}
				}
			});
		},
		//更新
		_doUpdate : function() {
			var param = {};
			param.order_id = mkh.getUrlParam('doctorId');
			//
			var doctor_remind_date = $('#doctor_remind_date').val();
			param.doctor_remind_date = doctor_remind_date;
			var doctor_remind_time = $('#doctor_remind_time').val();
			param.doctor_remind_time = doctor_remind_time;
			$('#hosp_order_id option:selected').each(function() {
				var hosp_order_id = $(this).val();
				param.hosp_order_id = hosp_order_id;
			});
			$('#department_id option:selected').each(function() {
				var department_id = $(this).val();
				param.department_id = department_id;
			});
			//
			mkh.ajax({
				url : "../../../remind/message/saveOrUpdateDoctor.action",
				type : "post",
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data) {
						layer.alert('保存成功', function() {
							//
							parent.remind.reloadDoctorData();
							//关闭
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						//
						});
					}
				}
			});
		},
		//绑定添加方法
		_bindAdd : function() {
			$('.doctor-span', '.tabCon').on('click', function() {
				var user_id = mkh.getUrlParam('id');
				layer.open({
					type : 2,
					content : 'doctor-detail.html?id=' + user_id + "&type=add",
					area : [ '50%', '50%' ],
					title : '添加'
				});
			});
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
			$('#doctor_remind_time').empty();
			$('#doctor_remind_time').append(data);
		},
		//加载医院列表
		_initHostipalList : function(selectedId) {
			var that = this;
			mkh.ajax({
				url : "../../../remind/message/getHospitalList.action",
				type : "post",
				dataType : 'json',
				data : {},
				success : function(data) {
					if (data) {
						var html = "<option value=''>请选择</option>";
						$.each(data, function(i, item) {
							html += "<option value='" + item.order_id + "'>" + item.hosp_name + "</option>";
						});
						$('#hosp_order_id').empty();
						$('#hosp_order_id').append(html);
						//
						that._initHospitalSelect(selectedId);
					}
				}
			});
		},
		//选择医院
		_initHospitalSelect : function(selectedId) {
			var that = this;
			$('#hosp_order_id').multiselect({
				placeholder : "请选择",
				enableFiltering : true,
				buttonWidth : '332px',
				filterPlaceholder : '请输入搜索',
				onChange : function(element, checked) {
					var order_id = element.val();
					if (order_id) {
						that._initDepartList(order_id);
					} else {
						that._initDefaultDepartSelect();
					}
				}
			});
			if (selectedId) {
				$('#hosp_order_id').multiselect('deselect', [ '' ]);
				$('#hosp_order_id').multiselect('select', [ selectedId ]);
			}
		},
		//初始化科室
		_initDepartList : function(order_id, selectedId) {
			var that = this;
			mkh.ajax({
				url : "../../../remind/message/getDepartList.action",
				type : "post",
				dataType : 'json',
				data : {
					hosp_order_id : order_id
				},
				success : function(data) {
					if (data && data.length > 0) {
						var html = "<option value=''>请选择</option>";
						$.each(data, function(i, item) {
							html += "<option value='" + item.order_id + "'>" + item.department_name + "</option>";
						});
						$('#department_id').empty();
						$('#department_id').append(html);
						//
						$('#department_id').multiselect('destroy');
						that._initDepartSelect(selectedId);
					} else {
						that._initDefaultDepartSelect();
					}
				}
			});
		},
		//初始化默认选择
		_initDefaultDepartSelect : function() {
			var html = "<option value=''>请选择</option>";
			$('#department_id').empty();
			$('#department_id').append(html);
			$('#department_id').multiselect('destroy');
			this._initDepartSelect();
		},
		//选择医院
		_initDepartSelect : function(selectedId) {
			$('#department_id').multiselect({
				placeholder : "请选择",
				enableFiltering : true,
				buttonWidth : '332px',
				filterPlaceholder : '请输入搜索',
				buttonContainer: '<div class="btn-group" />'
			});
			if (selectedId) {
				$('#department_id').multiselect('deselect', [ '' ]);
				$('#department_id').multiselect('select', [ selectedId ]);
			}
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
	m.doctor = function(method) {
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