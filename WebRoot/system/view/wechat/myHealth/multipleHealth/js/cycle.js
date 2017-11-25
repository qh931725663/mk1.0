window.onload = function() {
    window.requestAnimFrame = (function() {
        return window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            function(callback) {
                window.setTimeout(callback, 1000 / 60);
            };
    })();

    var canvas = document.getElementById('canvas'),
        ctx = canvas.getContext('2d'),
        cWidth = canvas.width,
        cHeight = canvas.height,
        score = canvas.attributes['data-score'].value,
        radius = 50, //圆的半径
        deg0 = (Math.PI) / 6, //每一格30度
        mum = 0.1, //数字步长
    /*
     * 要求：圆弧走完，数字得自加完，就得确定圆弧走的次数和数字走的次数相等！
     数字最大10，对应的度数是11*PI/9,那每个步长mum对应的度数如下：
     */
        deg1 = mum * Math.PI * 11.6/7 / 10; // 每mum对应的度数

    var angle = 0, //初始角度
        credit = 0; //数字默认值开始数

    var drawFrame = function() {
        ctx.save();
        ctx.clearRect(0, 0, cWidth, cHeight);
        ctx.translate(cWidth / 2, cHeight / 2);
        ctx.rotate(4 * deg0); //160度
        var aim = score * deg1 / mum; //数字对应的弧度数,先确定要走几次,除以mum,然后计算对应的弧度数
        if(angle < aim) {
            angle += deg1;
        }

/*        if(credit < score) {
            credit += mum; //默认数字间隔是mum
            console.log(credit);
        } else if(credit >= 10) {
            credit = 10;
        }*/
        credit=score;
        //信用额度
        ctx.save();
        ctx.rotate(10 * deg0);
        ctx.fillStyle = 'white';
        ctx.font = '28px Microsoft yahei';
        ctx.textAlign = 'center';
        ctx.restore();
        text(credit);

        ctx.save();
        ctx.beginPath();
        ctx.lineWidth = 5;
        ctx.strokeStyle = '#fba934';
        ctx.arc(0, 0, radius, 0, angle, false); //动画圆环
        ctx.stroke();
        ctx.restore();
        ctx.save();
        ctx.rotate(10 * deg0);
        ctx.restore();
        ctx.beginPath();
        ctx.strokeStyle = 'rgba(230, 230, 230, .5)';
        ctx.lineWidth = 5;
        ctx.arc(0, 0, radius, 0, 10 * deg0, false); //设置外圆环220度
        ctx.stroke();
        ctx.restore();

        window.requestAnimFrame(drawFrame);

    }

    function text(process) {
        ctx.save();
        ctx.rotate(8 * deg0);
        ctx.fillStyle = '#3D3D3D';
        ctx.font = '40px Microsoft yahei';
        ctx.textAlign = 'center';
        ctx.textBaseLine = 'top';
        ctx.fillText(process, 0, 10);
        ctx.restore();
    }

    setTimeout(function() {
        drawFrame();
    }, 10)

}