/*function test(){
	$.ajax({
		type:'post',
		url:'../query/testjson.action',				
		data:{pageNo:1},
		datatype:'json',
		success:function(data){			
			alert(data.data[0].user_id);
		}
	});
}*/

function test(){	
	$.ajax({
		type:'post',
		url:'../user/test.action',
		data:{'user_id':'123456','user_name':'蒋永海','user_phone':'18626336287','create_date':new Date()},
		datatype:'json',
		success:function(data){			
			console.log(data);
		}
	});
}
