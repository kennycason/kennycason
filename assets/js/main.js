var Main = {};

Main.init = function() {
    // Main.search();
}

Main.search = function() {
	$("#search").focus(function() {
		if($(this).val() == 'search') {
			$(this).val('');
			$(".post-list").show();
		}
	});
	$("#search").focusout(function() {
		if($(this).val() == '') {
			$(this).val('search');
			$(".post-list").show();
		}
	});
	$("#search").keyup(function(e) {
		var q = $(this).val().toLowerCase();
		if(q == '') {
			$(".post-list").show();
		} else {
			$(".post-list").each(function() {
				if($(this).text().toLowerCase().indexOf(q) == -1) {
					$(this).hide();
				} else {
					$(this).show();
				}
			});
		}
	});
}
