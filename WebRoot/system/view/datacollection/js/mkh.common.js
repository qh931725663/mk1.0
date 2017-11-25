//
var mkh = {
	errTips : function(selector, msg) {
		this.errTipsRemove(selector);
		$(selector).tips({
			msg : msg,
			side : 2,
			time : 0,
			x : 10
		})
	},
	errTipsRemove : function(selector) {
		if (selector.indexOf('#') != 0) {
			throw new Error('必须传入ID！');
		}
		var id = $(selector).attr('id');
		var error_element = "div.jq_tips_box." + id;
		if ($(error_element).length != 0) {
			$(error_element).remove();
		}
	},
	initHospital : function(selector) {
		$(selector).on("change", function() {
			var $input = $(this).prev();
			if ($input.length > 0) {
				if ($(this).val())
					$input.val($(this).find("option:selected").text());
				else
					$input.val("");
			}
		});
		$.ajax({
			type : "post",
			url : "../../../health-record/getHosptialList.action",
			dataType : "json",
			success : function(data) {
				if (data) {
					var html = ""
					$.each(data.data, function(i, item) {
						html += "<option value='" + item.order_id + "'>" + item.hosp_name + "</option>";
					});
					$(selector).append(html);
				}
			}
		});
	},
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},
	addPicture : function() {
		//
		var html = "<form class='form'><div class='upload_pic' style='margin-right:4px;'><img src='../images/browse.jpg' " +
			"class='bor' style='width:120px;height:120px;cursor: pointer;' " +
			"onclick='javascript:$(this).next().click()'><input class='image file'  " +
			"name='image' type='file' MULTIPLE='true' ACCEPT='image/gif,image/jpeg,image/jpg,image/png' " +
			"style='display:none'><span class='btn-upload form-group'><input type='file' multiple " +
			"name='image1' class='input-file'></span><img src='../images/delete.png' class='delete_img'></div></form>";
		$('#add_pic').before(html);
	},
	initPictures : function() {
		var self = this;
		$('#add_pic').on('click', function() {
			self.addPicture();
		});
		//
		$('#upload_file').on('mouseover mouseout', '.upload_pic', function() {
			if (event.type == "mouseover") {
				$(this).find('.delete_img').show();
			} else if (event.type == "mouseout") {
				$(this).find('.delete_img').hide();
			}
		});
		$('#upload_file').on('click', '.delete_img', function() {
			$(this).parent().remove();
		});
	},
	hospitalAutocomplete : function(options) {
		var param = {
			showDept : $.noop
		}
		param = $.extend(param, options || {});
		$("#hosp_name").autocomplete({
			source : "../../../health-record/getHosptialList.action",
			select : function(event, data) {
				//
				$("#hosp_name").val(data.item.label);
				$('#hosp_order_id').val(data.item.order_id);
				//联动
				param.showDept(data.item.order_id);
				// 必须阻止事件的默认行为，否则autocomplete默认会把ui.item.value设为输入框的value值
				event.preventDefault();
			},
			open : function(event, ui) {
				$(".ui-menu-item").each(function(i) {
					var text = $(this).find('a').text();
					var input_val = $("#hosp_name").val();
					if (text.indexOf(input_val) < 0) {
						$(this).remove();
					}
				});
			}
		});
		//绑定科室change
		$('#department_order_id').on('change', function() {
			var department_order_id = $(this).val();
			var department_name = $(this).find("option:selected").text();
			$("#department_name").val(department_name);
			//
			if (department_order_id) {
				$.ajax({
					url : "../../../health-record/getDoctorList.action",
					type : "post",
					dataType : 'json',
					data : {
						department_order_id : department_order_id
					},
					success : function(data) {
						var html = "<option value=''>全部</option>";
						if (data && data.length > 0) {
							$.each(data, function(i, item) {
								html += "<option value='" + item.doctor_id + "''>" + item.doctor_name + "</option>";
							});
						}
						$('#doctor_id').empty();
						$('#doctor_id').append(html);
					}
				});
			}
		});
		//绑定医生change
		$('#doctor_id').on('change', function() {
			var doctor_id = $(this).val();
			var doctor_name = $(this).find("option:selected").text();
			$("#doctor_name").val(doctor_name);

		});
	},
	filterStr : function(str) {
		var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]");
		var specialStr = "";
		for (var i = 0; i < str.length; i++) {
			specialStr += str.substr(i, 1).replace(pattern, '');
		}
		return specialStr;
	},
	//过滤输入框
	filterInput : function() {
		$(":input:visible").each(function() {
			//
			if ($(this).val()) {
				var newstr = mkh.filterStr($(this).val());
				$(this).val(newstr);
			}
		});
	},
	//图片上传初始化
	initUpload : function(options) {
		options = $.extend({
			id : "#zyupload",
			report_type : 500001,
			getParams : $.noop
		}, options || {});
		//获取用户
		var user_id = this.getUrlParam('id');
		// 初始化插件
		$(options.id).zyUpload({
			width : "700px", // 宽度
			height : "200px", // 宽度
			itemWidth : "140px", // 文件项的宽度
			itemHeight : "115px", // 文件项的高度
			url : "../../../health-record/upload.action?user_id=" + user_id + "&report_type=" + options.report_type, // 上传文件的路径
			fileType : [ "jpg", "JPG", "png", "PNG", "gif", "GIF" ], // 上传文件的类型
			fileSize : 51200000, // 上传文件的大小
			multiple : true, // 是否可以多个文件上传
			dragDrop : true, // 是否可以拖动上传文件
			tailor : false, // 是否可以裁剪图片
			del : true, // 是否可以删除文件
			finishDel : false, // 是否在上传文件完成后删除预览
			/* 外部获得的回调接口 */
			onComplete : function(response) { // 上传完成的回调方法
				//
				layer.alert('上传成功');
			},
			checkParams : function() {
				return options.getParams();
			}
		});
	},
	//ajax请求
	ajax: function(options) {
		//默认配置
		var settings = {
			contentType: "application/x-www-form-urlencoded",
			type: "GET"
		};
		settings = $.extend(settings, options || {});
		//处理权限
		var name = sessionStorage.getItem("name");
		if(!name) {
			if(self != top) {
				top.location.href = "http://localhost:8080/mkh1.0/system/login.html";
			}else {
				self.location.href = "http://localhost:8080/mkh1.0/system/login.html";
			}
			return;
		}
		//请求原生jQuery的ajax
		return $.ajax({
			type: settings.type,
			url: settings.url,
			data: settings.data,
			dataType: settings.dataType,
			success: settings.success,
			accepts: settings.accepts,
			async: settings.async,
			beforeSend: settings.beforeSend,
			cache: settings.cache,
			complete: settings.complete,
			contents: settings.contents,
			contentType: settings.contentType,
			context: settings.context,
			converters: settings.converters,
			crossDomain: settings.crossDomain,
			dataFilter: settings.dataFilter,
			error: settings.error,
			global: settings.global,
			headers: settings.headers,
			ifModified: settings.ifModified,
			isLocal: settings.isLocal,
			jsonp: settings.jsonp,
			jsonpCallback: settings.jsonpCallback,
			mimeType: settings.mimeType,
			password: settings.password,
			processData: settings.processData,
			scriptCharset: settings.scriptCharset,
			statusCode: settings.statusCode,
			traditional: settings.traditional,
			timeout: settings.timeout,
			username: settings.username,
			xhr: settings.xhr,
			xhrFields: settings.xhrFields
		});
	}
};