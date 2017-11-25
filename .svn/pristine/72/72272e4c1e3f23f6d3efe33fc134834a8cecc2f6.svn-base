$(function(){
	Regex.init();
});
var Regex = {
	init : function(){
		this.verify('.user_phone', /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/,"手机号格式不正确!");
		this.verify('.repsd', /^[a-zA-z\d]{6,16}$/,"仅限字母及数字 !");
		//this.closeNews('.user_phone');
	},
	//验证手机号，密码是否输入正确
	verify : function(ele,regex,content){
		$(ele).on('blur',function(){
			$(this).css('width','44%');
			var html = $(this).val();
			var reg = regex;
			if(reg.test(html)){
				$(this).next('.error').html("");
			}else{
				$(this).next('.error').html(content);
			}
		});
		this.closeNews(ele);
	},
	//得到焦点后，错误信息提示消失
	closeNews : function(inputName){
		$(inputName).on('focus',function(){
			$(this).next('.error').html("");
			$(this).css('width','86%');
		})
	}
}
