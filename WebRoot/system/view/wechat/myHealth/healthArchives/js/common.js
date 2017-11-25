//文件上传
var WX_UPLOAD = {
	url : "/mkh1.0/wx/healthRecord/upload.action",
	url_new : "",
	//选择文件
	fileInput : "#file",
	fileType : "",
	files : [], //上传文件
	//保存档案
	saveHealthRecord : $.noop,
	//上传多文件
	uploadFiles : function() {
		var that = this;
		//
		$.each(that.files, function(k, v) {
			that.uploadFile(v);
		});
	},
	//上传单个文件
	uploadFile : function(file) {

		var that = this;
		var formdata = new FormData();
		formdata.append("file", file);
		//
		var xhr = new XMLHttpRequest();

		// 完成
		xhr.addEventListener("load", function(e) {
			console.log("完成");
		}, false);
		// 错误
		xhr.addEventListener("error", function(e) {
			console.log("失败");
		}, false);
		//上传文件
		xhr.open("POST", that.url_new, true);
		xhr.send(formdata);
	},
	loadData : function(data) {},
	initData : function() {
		var that = this;
		var openid = WX_UPLOAD.getUrlParam("openid");
		return $.ajax({
			url : "/mkh1.0/wx/healthRecord/userInfo.action",
			type : "post",
			dataType : 'json',
			data : {
				openid : openid
			},
			success : function(data) {
				if (data.status) {
					that.loadData(data.data);
				}
			}
		});
	},
	//初始化
	init : function() {

		var that = this;
		//初始化数据
		this.initData();
		//初始化医院数据
		this.loadHospitalInfo();
		//
		$(this.fileInput).on("change", function(e) {
			that.getFiles(e);
			//选择图片
			that.selectFile();
		});
		//绑定保存方法
		this.bindSave();
		//绑定弹窗方法
		this.bindOpenWindow();
	},
	//加载医院
	loadHospitalInfo : function() {
		var that = this;
		return $.ajax({
			url : "/mkh1.0/wx/healthRecord/hospitalInfo.action",
			type : "post",
			dataType : 'json',
			success : function(data) {
				if (data.status) {
					var html = ""
					$.each(data.data, function(i, item) {
						html += "<option value='" + item.order_id + "'>" + item.hosp_name + "</option>";
					});
					$("#hosp_order_id").append(html);
					//
					that.changeHospital();
				}
			}
		});
	},
	//选择医院
	changeHospital : function() {
		//
		var that = this;
		$("#hosp_order_id").on("change", function() {
			var name = $(this).find("option:selected").text();
			if ($(this).find("option:selected").is($(this).find("option").eq(0))) {
				$("#hosp_name").val("");
			} else {
				$("#hosp_name").val(name);
			}
			//加载科室
			that.loadDepartmentInfo($(this).val());
		});
	},
	//加载科室
	loadDepartmentInfo : function(hosp_order_id) {
		if ($("#department_order_id").length > 0) {
			//重置科室列表
			$('#department_order_id').empty();
			$('#department_name').val("");
			$('#department_order_id').append("<option value=''>请选择科室</option>");
			//重置医生
			$('#report_doctor_id').empty();
			$('#report_doctor').val("");
			$('#report_doctor_id').append("<option value=''>请选择医生</option>");
			//
			if (hosp_order_id) {
				//
				var that = this;
				return $.ajax({
					url : "/mkh1.0/wx/healthRecord/departmentInfo.action",
					type : "post",
					dataType : 'json',
					data : {
						hosp_order_id : hosp_order_id
					},
					success : function(data) {
						//加载科室
						if (data.status) {
							var html = ""
							$.each(data.data, function(i, item) {
								html += "<option value='" + item.order_id + "'>" + item.department_name + "</option>";
							});

							$('#department_order_id').append(html);
							//绑定选择事件
							that.changeDepartment();
						}
					}
				});
			}
		}
	},
	//选择部门
	changeDepartment : function() {
		//
		var that = this;
		$("#department_order_id").on("change", function() {
			var name = $(this).find("option:selected").text();
			if ($(this).find("option:selected").is($(this).find("option").eq(0))) {
				$("#department_name").val("");
			} else {
				$("#department_name").val(name);
			}
			//加载
			that.loadDoctorInfo($(this).val());
		});
	},
	//加载医生
	loadDoctorInfo : function(department_order_id) {
		//重置科室列表
		$('#report_doctor_id').empty();
		$('#report_doctor').val("");
		$('#report_doctor_id').append("<option value=''>请选择医生</option>");
		if (department_order_id) {
			//
			var that = this;
			return $.ajax({
				url : "/mkh1.0/wx/healthRecord/doctorInfo.action",
				type : "post",
				dataType : 'json',
				data : {
					department_order_id : department_order_id
				},
				success : function(data) {
					//加载科室
					if (data.status) {
						var html = ""
						$.each(data.data, function(i, item) {
							html += "<option value='" + item.doctor_id + "'>" + item.doctor_name + "</option>";
						});
						$('#report_doctor_id').append(html);
						//选择医生
						that.changeDoctor();
					}
				}
			});
		}
	},
	//选择医生
	changeDoctor : function() {
		var that = this;
		$("#report_doctor_id").on("change", function() {
			var name = $(this).find("option:selected").text();
			if ($(this).find("option:selected").is($(this).find("option").eq(0))) {
				$("#report_doctor").val("");
			} else {
				$("#report_doctor").val(name);
			}
		});
	},
	//获取文件
	getFiles : function(e) {
		var that = this;
		// 从事件中获取选中的所有文件
		var new_files = e.target.files || e.dataTransfer.files;
		$.each(new_files, function(i, v) {
			debugger;
			that.files.push(v);
		});
	},
	//删除文件
	deleteFile : function() {
		var that = this;
		//
		$(".z_delImg").unbind().on("click", function(e) {
			//删除文件
			var index = $(this).parent().attr("data-index");
			//
			that.files.splice(index, 1);
			//删除元素
			$(this).parent().remove();
			//重置序号
			that.resetFileIndex();
		});
	},
	//选择图片
	selectFile : function() {
		//获取点击的文本框
		var file = document.getElementById("file");
		//存放图片的父级元素
		var imgContainer = document.getElementsByClassName('z_photo')[0];
		//获取的图片文件
		var fileList = file.files;
		//文本框的父级元素
		var input = document.getElementsByClassName('z_file')[0];
		//遍历获取到得图片文件
		for (var i = 0; i < fileList.length; i++) {
			var imgUrl = window.URL.createObjectURL(file.files[i]);
			//显示图片
			var img = document.createElement("img");
			img.setAttribute("src", imgUrl);
			//删除图片按钮
			var del = document.createElement("img");
			del.setAttribute("src", "image/del.png");
			del.setAttribute("class", "z_delImg");
			//
			var imgAdd = document.createElement("div");
			imgAdd.setAttribute("class", "z_addImg");
			imgAdd.appendChild(img);
			imgAdd.appendChild(del);
			imgContainer.appendChild(imgAdd);
		}
		//绑定删除事件
		this.deleteFile();
		//图片标记
		this.resetFileIndex();
	},
	//重置标记
	resetFileIndex : function() {
		$(".z_addImg").each(function(i) {
			$(this).attr("data-index", i);
		});
	},
	//绑定保存方法
	bindSave : function() {
		var that = this;
		//
		$(".btn-save").on("click", function() {
			//保存体检报告
			that.saveHealthRecord().done(function(data) {
				var order_id = data.data;
				//保存图片
				that.url_new = that.url + "?report_type=" + that.fileType + "&openid=test" + "&order_id=" + order_id;
				that.uploadFiles();
			});

		});
	},
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},
	//弹出窗口
	openWindow : function(title) {
		var content = "<div class='win'>" +
			"<div class='title'>" +
			"<img class='jt' src='image/jt-left.png'/>" +
			"<span class='center-text'>" + title + "</span>" +
			"<span class='right-text'>确认</span>" +
			"</div>" +
			"<textarea class='input-content' autofocus placeholder='来简单记录一下吧~'></textarea>" +
			"<div class='remark'>0/200</div>" +
			"</div>";
		//弹窗
		layer.open({
			type : 1,
			shadeClose : false,
			content : content,
			anim : 'up',
			style : "width: 100%;height: 100%;border:none;background: #F0F0F0;"
		});
		$("html,body").animate({scrollTop : $("body").offset().top},0);
	},
	//绑定弹窗
	bindOpenWindow : function(){
		var that = this;
		//
		$("#clinic_report_disease,#hospital_report_disease").on("focus", function(){
			that.openWindow("病症");
			var $this = $(this);
			var text = $this.val();
			$(".win .input-content").val(text);
			//
			$(".win .jt").on("click", function(){
				layer.closeAll();
			});
			$(".win .right-text").on("click", function(){
				text = $(".win .input-content").val();
				$this.val(text);
				layer.closeAll();
			});
		});
		//
		$("#clinic_report_result,#hospital_report_result").on("focus", function(){
			that.openWindow("诊断");
			var $this = $(this);
			var text = $this.val();
			$(".win .input-content").val(text);
			//
			$(".win .jt").on("click", function(){
				layer.closeAll();
			});
			$(".win .right-text").on("click", function(){
				text = $(".win .input-content").val();
				$this.val(text);
				layer.closeAll();
			});
		});
	}
};