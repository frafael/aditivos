(function($){
  $.fn.fortesMenu = function(settings){
    
    return this.each(function(){
      var $menusTitle = $(this).find(".menu-title"),
          $subMenus = $(this).find(".submenu");
    	
      $subMenus.hide();

      $menusTitle.on("click", function(event){
    	  var $currentMenu = $(this);
        
        //Pega todos os 'li' que não sejam os do menu clicado
        $(".menu > ul > li").not($currentMenu.parent("li")).each(function(idx, li){
          // Fechando outros submenus
          $(li).find(".submenu").slideUp(150, "linear");
          // Mundando icone '-' para '+'
          $(li).find("i.icon-minus").attr("class", "icon-plus");
          // Remove as setas
          $(li).find(".menu-title").removeClass("arrow_box");
        })
        // Verifica se o menu possui submenus. Deve haver uma classe nos 'li' chamada 'no-submenu'
        if(!$($currentMenu).parent('li').hasClass('no-submenu')) $currentMenu.toggleClass("arrow_box");

        // Toggle submenu clicado
      	$(this).siblings(".submenu").slideToggle(150, "linear", function(){
        	// _switchClasses($currentMenu.find("i"), "icon-minus", "icon-plus") ;
          $currentMenu.find("i").toggleClass("icon-minus").toggleClass("icon-plus");
        });

      });

    });
  };
})(jQuery);