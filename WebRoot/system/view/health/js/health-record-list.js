//体检报告
$(".table").on('click','.check',function(){
	//alert("check");
	var id = $(this).parents('tr').attr('data-id');
		layer_show('体检报告上传','check-report.html?id=' + id,'58','72');
	//window.location.href = "check-report.html?id=" + id;
});
//住院报告
$(".table").on('click','.hospital',function(){
	//alert("hospital");
	var id = $(this).parents('tr').attr('data-id');
	layer_show('住院报告上传','hospital-report.html?id=' + id,'58','85');
	//window.location.href = "hospital-report.html?id=" + id;
});
//门诊报告
$(".table").on('click','.clinic',function(){
	//alert("clinic");
	var id = $(this).parents('tr').attr('data-id');
	layer_show('门诊报告上传','clinic-report.html?id=' + id,'58','85');
	//window.location.href = "clinic-report.html?id=" + id;
});
//医保记录
$(".table").on('click','.medical',function(){
	//alert("medical");
	var id = $(this).parents('tr').attr('data-id');
	layer_show('医保记录上传','medical-report.html?id=' + id,'58','85');
	//window.location.href = "medical-report.html?id=" + id;
});
//判断非大陆用户提示消息
$(".table").on('click','.medical_2',function(){
	layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 1, //不显示关闭按钮
		  area: ['250px', '100px'], //宽高
		  anim: 2,
		  shadeClose: true, //开启遮罩关闭
		  content: '<div style="margin-left:20px;margin-top:10px;">非大陆用户不能添加医保记录</div>'
	});
});
//明细
$(".table").on('click','.detail',function(){
	//alert("medical");
	var id = $(this).parents('tr').attr('data-id');
	window.location.href = "health-record-detail.html?id=" + id;
});

var initDataFun = function(param){
	var data = {pageNo : 1};
	$('#form').find("input").each(function(){
		var name = this.name;
		if(name){
			data[name] = $(this).val();
		}
	});
	data = $.extend(data, param||{});
	return mkh.ajax({
	    url : "../../../health-record/pageData.action",
	    type : "post",
	    dataType : 'json',
	    data : data,
	    success : function(data) {
	    	createTable(data);
	   }
	});
}
//回调
initDataFun().done(function(data){
	//console.log(data)
	if(data){
		$('#Pagination').pagination(data.recordCount, {
		    jump: true,
		    page_count:data.pageCount,
		    callback:function(){
		    	var pageNo = $('#pagevalue').val();
		    	var param = {pageNo : pageNo};
		    	initDataFun(param);
		    }
		});
	}
});
//生成表
var createTable = function(json){
	//
	var html = "";
	$.each(json.data, function(i, item){
		//console.log(item)
		html +="<tr data-id='"+item.user_id+"'><td>"+(i+1)+"</td><td>"+item.user_name+"</td>" +
			"<td>" + item.user_card + "</td><td>" + item.user_phone + "</td>" + "<td>" + item.doctor_name + "</td>"+
				"<td><a class='check btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe642;</i>&nbsp;上传</a></td>" +
				"<td><a class='hospital btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe642;</i>&nbsp;上传</a></td>" +
				"<td><a class='clinic btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe642;</i>&nbsp;上传</a></td>" +
				"<td><a class=' "+(item.mainland==1?'medical':'medical_2')+" btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe642;</i>&nbsp;上传</a></td>" +
				"<td><a class='detail btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe627;</i>&nbsp;查看详情</a></td></tr>";
				
	});
	$('.table tbody').empty();
	$('.table tbody').append(html);
}

//查询
var search_report = function(){
	$('.btn-success','#form').on('click', function(){
		initDataFun().done(function(data){
			//console.log(data)
			if(data){
				$('#Pagination').pagination(data.recordCount, {
				    jump: true,
				    page_count:data.pageCount,
				    callback:function(){
				    	var pageNo = $('#pagevalue').val();
				    	var param = {pageNo : pageNo};
				    	initDataFun(param);
				    }
				});
			}
		});
	});
}();


//清空
var clear_report = function(){
	$('.btn-danger').on('click', function(){
		$('#form').find("input").each(function(){
			$(this).val("");
		});
	});
}();