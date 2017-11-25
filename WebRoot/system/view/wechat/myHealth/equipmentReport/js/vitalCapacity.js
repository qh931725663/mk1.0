$(function(){
	load.init();
});
var load = {
	init : function(){
		this.chart();
		this.boxWidth();
		this.getHeight();
	},
	//获取mid的高
	getHeight : function(){
		var h_height = $('.mid').height();
		$('.mid').css('line-height',h_height+'px');
	},
	//获取canvasBox的宽
	boxWidth : function(){
		$(".canvasBox").css({"width":$(window).width()-30});
		$(".cvsBox").css({"width":$(window).width()-71});
	},
	//加载折线图
	chart : function(){
		var basicdata = [3.6,3.9,2.0,4.3,2.6,1.9,3.4];
		var basicdata2 = [2.3,3.6,3.0,2.1,1.5,6.4,5.1];
		var basicdata3 = [2.6,4.3,2.6,6.1,4.2,5.1,1.9];
		var line = new RGraph.Line({
	        id: 'cvs',
	        data: [basicdata,basicdata2,basicdata3],
	        options: {
	            labels: ['2017-08-15 16:22','2017-08-16 16:22','2017-08-17 16:22','2017-08-18 16:22','2017-08-19 16:22','2017-08-20 16:22','2017-08-21 16:22'],
	            tooltips : ['82','80.5','82.5','81','83','84','82'],
	            tooltipsCoordsPage: true,
				noxaxis: true,
				noyaxis: true,
				ylabels: false,
				tickmarks: 'endcircle',
	            backgroundHbars : [
	            	[10,8,'rgba(255,255,255,0.5'],
	            	[8,6,'rgba(198,227,255,0.5)'],
	            	[6,5,'rgba(255,255,255,0.5'],
	            	[5,3,'rgba(252,182,163,0.5)']
	            ],
	            textColor: '#808080',
	            ymin : 1.00,
	            ymax : 10.00,
	            axisColor: '#E6E6E6',
	            ylabels:false,
	            textFont:12,
	            gutterLeft: 0,
	            gutterRight: 0,
	            gutterBottom: 35,
	            gutterTop: 35,
	            textAccessible: true,
	            backgroundGridDashed: true,
	            colors : ['RGB(248,94,52)','RGB(87,195,238)','#4AB560']
	        }
	    }).trace();
	
	    var yaxis = new RGraph.Drawing.YAxis({
            id: 'axes',
            x: 40,
            options: {
                max: line.max,
                min: line.min,
                colors: ['#808080'],
                textAccessible: true
            }
        }).draw();
	}
}