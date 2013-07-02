var login = {
	validacao : function() {
		$("#formLogin").validate({
			rules : {
				"nome" : { required : true },
				"senha" : { required : true }
			},
			messages : {
				"nome" : { required : "Digite o login." },
				"senha" : { required : "Digite a senha." }
			}
		});
	},
	logar : function(contexto) {
		if( $("#formLogin").valid() ) {
			this.criptografarSenha();
			this.salvarCookie(contexto);
			$("#formLogin").submit();
		}
	},
	salvarCookie : function(contexto) {
		var usuarioNome = $("#usuarioLogin").val();
		var isLembrarSeDeMim = $("#lembreSeDeMim").is(":checked");
		if( isLembrarSeDeMim ) {
			var cookie = usuarioNome+"-"+hex_md5($("#usuarioPassword").val());
			$.cookie("nome", cookie , {path: "/"+contexto, expires : 30 });
		} else {
			$.cookie("nome", null);
		}
	},
	criptografarSenha : function() {
		var usuarioPassword = $("#usuarioPassword").val();
		$("#senhaCriptografada").val(hex_md5(usuarioPassword));
	}
};
$(function() {
	login.validacao();
	if( $.cookie("nome") && $("#erro").html() == "") {
		var cookie = $.cookie("nome");
		var nome = cookie.replace(/(.+)-(.+)/, "$1");
		var senhaCriptografada = cookie.replace(/(.+)-(.+)/, "$2")
		$("#usuarioLogin").val(nome);
		$("#usuarioPassword").val(senhaCriptografada);
		$("#senhaCriptografada").val(senhaCriptografada);
		$("#formLogin").submit();
		/*$("#lembreSeDeMim").attr("checked", "checked");*/
	}
});