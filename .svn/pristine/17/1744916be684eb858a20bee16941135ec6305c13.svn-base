function reload() {
	window.location.reload();
}

var initDataFun = function(param) {
	var data = {
		pageNo : 1
	};
	$('#form').find(":input").each(function() {
		var name = this.name;
		if (name) {
			data[name] = $(this).val();
		}
	});
	data = $.extend(data, param || {});
	
	var order_id = mkh.getUrlParam("id");
	data.order_id = order_id;
	return mkh.ajax({
		url : "../../../role/getRoleBindUser.action",
		type : "post",
		dataType : 'json',
		data : data,
		success : function(data) {
			//console.log(data);
			createTable(data);
		}
	});
}
//回调
initDataFun().done(function(data) {
	//console.log(data)
	if (data) {
		$('#Pagination').pagination(data.recordCount, {
			jump : true,
			page_count : data.pageCount,
			callback : function() {
				var pageNo = $('#pagevalue').val();
				var param = {
					pageNo : pageNo
				};
				initDataFun(param);
			}
		});
	}
});
//生成表
var createTable = function(json) {
	//
	var html = "";
	$.each(json.data.data, function(i, item) {
		//console.log(item);
		html += "<tr data-id='" + item.user_id + "'>" +
			"<td>" + item.user_id + "</td>" +
			"<td>" + item.user_phone + "</td><td>" + item.user_name + "</td><td>" + item.role_name +"</td>"+
			"</tr>";

	});
	$('.table tbody').empty();
	$('.table tbody').append(html);
}

//查询
var search_report = function() {
	$('.btn-success', '#form').on('click', function() {
		initDataFun().done(function(data) {
			//console.log(data);
			if (data) {
				$('#Pagination').pagination(data.recordCount, {
					jump : true,
					page_count : data.pageCount,
					callback : function() {
						var pageNo = $('#pagevalue').val();
						var param = {
							pageNo : pageNo
						};
						initDataFun(param);
					}
				});
			}
		});
	});
}();


//清空
var clear_report = function() {
	$('.btn-danger').on('click', function() {
		$('#form').find(":input").each(function() {
			$(this).val("");
		});
	});
}();



