(function($){
  $.fn.fortesPhotoPreview = function(settings){
    
  var config = {
    'inputFile': false
  };
  
  if (settings){$.extend(config, settings);}
  
  if(!config.inputFile) throw("You sould define a input file selector.");

    return this.each(function(){
      var elem = $(this);
      
      $(config.inputFile).on("change", function(e){

        if(typeof FileReader == "undefined") return true;
       
        var file = e.target.files[0];
        
        if (file.type.match('image.*')) {
          var reader = new FileReader();
          
          reader.onload = (function(theFile) {
            
            return function(e) {
              var image = e.target.result;
              elem.parent().css("background-image", "url("+image+")");
            };

          })(file);

          reader.readAsDataURL(file);
        }
        
      
      });//Fim do change

    });
  };
})(jQuery);



