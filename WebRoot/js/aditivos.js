var filtroIdMensagens;
var intranet = {
	init : function() {
		this.utils.boxDialog.init();
		this.utils.accordion.init();
		this.utils.maxlengthTextArea.init();
		this.utils.paginacao.init(10);
		this.formulario.focusAndReset();
		this.formulario.validacao.mascaras.init();
	},
	validacao : {},
	views : {},
	formulario : {},
	utils : {}
};
$(function() {
	intranet.init();
	
	//Config necessária para eleminar o conflito dos buttons entre o bootstrap e o Jquery UI
	var btn = $.fn.button.noConflict(); // reverts $.fn.button to jqueryui btn
	$.fn.btn = btn; // assigns bootstrap button functionality to $.fn.btn
});