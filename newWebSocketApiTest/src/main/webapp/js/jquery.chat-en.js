(function ($) {
	"use strict";
	
	if (!$.chat) {
		$.chat = {};
	}
	if (!$.chat.language) {
		$.chat.language = [];
	}
	$.chat.language['es'] = {
			messages: {
				
			}
	};
	$.chat.language['en'] = {
			messages: {
				"chat.window.button.defaultText" : "Send"
			}
	};
	
})(jQuery);