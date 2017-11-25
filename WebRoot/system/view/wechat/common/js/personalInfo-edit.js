$(function(){
	personalInfo.init();
});
var personalInfo = {
	init : function(){
		this.hide();
		this.getHeight();
	},
	//value为空时，隐藏单位
	hide : function(){
		$('.cont').each(function(){
			$(this).val()==''?$(this).next('.mond').css('display','none'):$(this).next('.mond').css('display','inline-block');
		});
		
		$(".cont").on('blur',function(){
			var content = $(this).val();
			if(content==''){
				$(this).next('.mond').css('display','none');
			}else{
				$(this).next('.mond').css('display','inline-block');
			}
		});
	},
	//获取头像的高
	getHeight : function(){
		var hgt = $(".picture").height();
		$('.picture').css('line-height',hgt+'px');
	}
}
