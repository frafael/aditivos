(function($){
  $.fn.fortesPhotoInput = function(settings){
    
	var config = {
      'hoverArea': false
	};
	
	if (settings){$.extend(config, settings);}
		    
    return this.each(function(){
      
      $(this).parent().siblings().on("click", function(){
        $("form input[type='file']").trigger("click");
      });
      
      $(config.hoverArea).on("hover", function(){
        $editFoto = $('.edit-foto');
        $editFoto.toggleClass("in");
      });	

    });
  };
})(jQuery);



