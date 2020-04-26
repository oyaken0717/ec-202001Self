/*<![CDATA[*/
//■ 変数の展開: いつもの ${}に /*[[ と ]]*/ をつける。
$(function(){
	$('#year').on("change", function() {
		var hostUrl = 'http://localhost:8080/chart/graph';
		var year = $("#year").val();

		$.ajax({
			url : hostUrl,
			type : 'POST',
			dataType : 'json',
			data : {
				year : year
			},
			async : true
		}).done(function(data){
			console.log(data);
			console.dir(JSON.stringify(data));

			var yokos = [];
			var tates = [];
			for (var sale of data) {
				yokos.push(sale.month);
				tates.push(sale.totalPrice);
			}
			
			var ct = document.getElementById("graph");
			var chart = new Chart(ct, {
				type : 'line',
				data : {
					labels :[
						yokos[0],
						yokos[1],
						yokos[2],
						yokos[3],
						yokos[4],
						yokos[5],
						yokos[6],
						yokos[7],
						yokos[8],
						yokos[9],
						yokos[10],
						yokos[11],						
					],
					datasets : [ 
						{
							data :[
								tates[0],
								tates[1],
								tates[2],
								tates[3],
								tates[4],
								tates[5],
								tates[6],
								tates[7],
								tates[8],
								tates[9],
								tates[10],
								tates[11],
							],
						}
					],
				},
				options : {
					title : {
                        display: true,
						text : '売上'
					},
					scales : {
						yAxes : [ {
							ticks : {
								suggestedMax : 15000,
								suggestedMin : 1000,
								stepSize : 1000,
								callback : function(value) {
									return value + '円'
								}
							}
						} ]
					},
				}
			});
						
		}).fail(function(){
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		//■ ajaxの終わり
		});
	//■ onの終わり
	});
//■ 1番上のready関数の最後
});
/*]]>*/