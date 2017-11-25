$(function() {
	//
	WX_UPLOAD = $.extend(WX_UPLOAD, {
		fileType : 500003,
		saveHealthRecord : function() {

			var info = WX_UPLOAD.getCheckData();
			//
			return $.ajax({
				url : "/mkh1.0/wx/healthRecord/saveHospital.action",
				type : "post",
				dataType : 'json',
				data : info,
				success : function(data) {
					console.log(data);
				}
			});

		},
		getCheckData : function() {
			var obj = {};
			$('.write :input').each(function() {
				var name = $(this).attr("name");
				obj[name] = $(this).val();
			})
			obj.openid = "test";
			return obj;
		},
		loadData : function(data){
			$("#patient_id").val(data.user_id);
			$("#patient_name").val(data.user_name);
		}
	});
	WX_UPLOAD.init();
});