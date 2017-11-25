$(function() {
	//
	WX_UPLOAD = $.extend(WX_UPLOAD, {
		fileType : 500001,
		saveHealthRecord : function() {

			var info = WX_UPLOAD.getCheckData();
			//
			return $.ajax({
				url : "/mkh1.0/wx/healthRecord/saveCheck.action",
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
			return obj;
		},
		loadData : function(data){
			$('.write :input').each(function(){
				var name = $(this).attr("name");
				if(data[name]){
					$(this).val(data[name]);
				}
			});
		}
	});
	WX_UPLOAD.init();
});