(function ($) {
	$.fn.extend($.fn, {
		version : "0.0.0.2",
		wrapLabel : function() {
		    $("body :input[label]").each(function() {
		    	var $item = $(this),
					label = $item.attr('label'),
					id =  $item.attr('id'),
					forLabel = ( id && ' for="'+ id +'"' || "" );
		    
		    	if( $item.is(":checkbox") ) {
		    		$item.wrap('<label/>')
			    		.parent().append(label).attr("for", id).addClass('checkbox')
		    			.wrap('<div class="control-group"/>');
		    	} else {
		    		label && $item.wrap('<div class="control-group"/>')
		    			.before('<label'+ forLabel +' class="control-label">'+ label +': </label>')
		    			.wrap('<div class="controls"/>');
		    	}
		    });
		    
		} 
	}); 
})(jQuery);