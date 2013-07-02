(function($){

	$.fn.keepScrolling = function() {
		var self = this;
		this.siblings(".botao-top").click(function(){
			self.data("keepScrollingTop", true);
			GoTop(self);
		});

		this.siblings(".botao-top").click(function(){
			self.data("keepScrollingTop", false);
			GoTop(self);
		});

		this.siblings(".botao-bottom").click(function(){
			self.data("keepScrollingDown", true);
			GoDown(self);
		});

		this.siblings(".botao-bottom").click(function(){
			self.data("keepScrollingDown", false);
			GoDown(self);
		});
	};

	function GoTop(elemento) {
	    if(elemento.data("keepScrollingTop"))
	        setTimeout(function() {
	        	elemento.scrollTop(elemento.scrollTop() - 150);
	            GoTop(elemento);
	        },100);
	};

	function GoDown(elemento) {
	    if(elemento.data("keepScrollingDown"))
	        setTimeout(function() {
	        	elemento.scrollTop(elemento.scrollTop() + 150);
	        	GoDown(elemento);
	        },100);
	};
})(jQuery);