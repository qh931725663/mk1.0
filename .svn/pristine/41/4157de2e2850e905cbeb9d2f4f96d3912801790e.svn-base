$(function(){
	load.init();
});
var load = {
	init : function(){
		this.chart();
		this.boxWidth();
	},
	//获取canvasBox的宽
	boxWidth : function(){
		$(".canvasBox").css({"width":$(window).width()-30});
		$("#cvs").css({"width":$(window).width()-30});
	},
	//加载折线图
	chart : function(){
		var line = new RGraph.Line({
	        id: 'cvs',
	         data: [90,90,121,90,50,90,121,110,90,26,90,90,120,90,90,20,90,123,90,90,90,20],
            options: {
                tickmarks: '',
                shadow: false,
                filled: true,
                fillstyle: [
                    'Gradient(rgba(255,0,0,0):rgba(255,0,0,0))'
                ],
                noaxes: true,
                backgroundGridColor: '#333',
                textColor: 'white',
                gutterLeft: 5,
                gutterRight: 5,
                gutterTop: 10,
                gutterBottom: 10,
            }
        }).trace();
	}
}