$(function() {
	calc_price();
	$(".size").on("change",function() {
		calc_price();
	});

	$(".checkbox").on("change",function() {
		calc_price();
	});

	$("#pizanum").on("change",function() {
		calc_price();
	});

	// 値段の計算をして変更する関数
	function calc_price() {
		var size = $(".size:checked").val();
		var topping_count = $("#topping input:checkbox:checked").length;
		var piza_num = $("#pizanum option:selected").val();
		if (size == "M") {
			var size_price = $("#sizeM").text();
			var topping_price = 200 * topping_count;
		} else {
			var size_price = $("#sizeL").text();
			var topping_price = 300 * topping_count;
		}
		var price = (Number(size_price) + topping_price) * piza_num;

		console.log("price");
		console.log(price);

		$("#totalprice").text(price);
	}
	;
});