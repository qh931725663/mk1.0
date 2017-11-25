$(function(){
	vital.init();
});



$(window).scroll(function(){
	　　var scrollTop = $(this).scrollTop();
	　　var scrollHeight = $(document).height();
	　　var windowHeight = $(this).height();
	　　if(scrollTop + windowHeight == scrollHeight){
			if(vital.pageNo+1 <= vital.pagecount){
				vital.getInfo(vital.pageNo+1);
			}
	　　}
});

    
var vital = {
	pageNo : "",
	pagecount: '',
	init : function(){
		this.getData();
	},
	//获取查看月数据
	getData : function(){
		$.ajax({
			url : "../../../../../electrocardiograph-wx/months.action",
			type : "GET",
			dataType : 'json',
			data :'',
			success : function(data){
//				console.log(data);
				var list = data.data.list;
				$.each(list, function(i,m) {
					//console.log(list[i].yearmonth);
					var html = '';
					html +=  '<option>'+list[i].yearmonth+'</option>';
					$(".sel_year").append(html);
				});
			}
		});
		this.getInfo(1);
		this.getDetail();
	},
	//获取数据
	getInfo : function(current){
		var data = $.param({pageno:current})+'&'+$("#form_pressure_show").serialize();
		//console.log(123);
		$.ajax({
			url : "../../../../../electrocardiograph-wx/page.action",
			type :"POST",
			dataType : 'json',
			data :data,
			success : function(arr){
//				console.log(arr);
				if(arr.status==0){
					var monthlist = arr.data.monthlist;
					vital.pageNo = arr.data.pageno;
					vital.pagecount = arr.data.pagecount;
					$.each(monthlist, function(i,m) {
						var datalist = m.datalist;
						var html = "";
						
						var time = m.yearmonth;
	            		var dl = "";
	            		var len = $('.blood-data-show').find('.sel_month').length;
	            		if(len == 0){
	            			dl = "<dl><dt class='sel_month'>" + time + "</dt><dd><ul></ul></dd></dl>";
	            			$('.blood-data-show').append(dl);
	            		}else{
	            			var temp = true;
	            			$('.blood-data-show').find('.sel_month').each(function(){
	            				if($(this).text() == time){
	            					temp = false;
	            				}
	            			});
	            			if(temp){
	            				dl = "<dl><dt class='sel_month'>" + time + "</dt><dd><ul></ul></dd></dl>";
	            				
	            				$('.blood-data-show').append(dl);
	            			}
	            		}
						
						$.each(datalist, function(j,n) {
							var sun;
							if(datalist[j].period==-1){
								sun = 'morning_01.png';
							}else if(datalist[j].period==0){
								sun = 'afternoon_01.png';
							}else{
								sun = 'nigth_01.png';
							}
							html += '';
							html += '<li class="line-align-left clearfix">';
                    		html += '<img src="../../common/image/myhealth/'+sun+'" alt="" style="width: 10%;"/>';
                    		html += '<span class="datetime">'+datalist[j].datetime.substr(5)+'</span>';
                    		html += '<span>'+n.HeartRate+'<span style="font-size: 14px;">次/分钟</span></span>'
                    		html += '</li>';
						});
						//
						$(".blood-data-show").find(".sel_month").each(function(){
							if($(this).text() == time){
								$(this).next().children().first().append(html);
							}
						});
					});
				}
			}
		});
	},
	//切换月份
	getDetail : function(){
		$(".sel_year").on('change',function(){
			var content = $(this).val();
			//点击查看月数据时，显示全部
			if(content != '查看月数据'){
				//console.log(content);
				$(".sel_month").each(function(){
					var html = $(this).html();
					if(html==content){
						$(this).parent().siblings().css('display','none');
						$(this).parent().css('display','block');
					}
				});
			}else{
				$("dl").css('display','block');
			}
		});
	}
}
