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
		$(".cvsBox").css({"width":$(window).width()-71});
	},
	//加载折线图
	chart : function(){
		var basicdata = [82,80.5,82.5,81,83,84,82];
		var line = new RGraph.Line({
	        id: 'cvs',
	        data: basicdata,
	        options: {
	            labels: ['2017-08-15 16:22','2017-08-16 16:22','2017-08-17 16:22','2017-08-18 16:22','2017-08-19 16:22','2017-08-20 16:22','2017-08-21 16:22'],
	            tooltips : ['82','80.5','82.5','81','83','84','82'],
	            tooltipsCoordsPage: true,
                noxaxis: true,
                noyaxis: true,
                ylabels: false,
                tickmarks: 'endcircle',
	            backgroundHbars : [
	            	[95,100,'rgba(198,227,255,0.8)'],
	                [0,95,'rgba(255,255,255,0.2)']
	            ],
	            textColor: '#808080',
	            numyticks:4,
	            ymax : 100,
	            ymin : 80,
	            axisColor: '#E6E6E6',
	            textFont:12,
	            gutterLeft: 0,
	            gutterRight: 0,
	            gutterBottom: 35,
	            gutterTop: 35,
	            textAccessible: true,
	            scaleZerostart: true,
	            labelsOffsety: 0,
	            backgroundGridDashed: true,
	            shadow: null,
	            highlightStyle: 'halo'
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