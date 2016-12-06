(function ($) {
	
	"use strict";
	
	if (!window.general) {
		window.general = {};
	}
	if (!general.util) {
		general.util = {
				
		}
	}
	
	if (!general.util.MessageResolver) {
		general.util.MessageResolver = function (messages) {
			this.messages = messages;
			
			/**  
			 * code, arg1, arg2, ..., argN
			 */  
			this.getMessage = function (code) {
				var message = null;
				var rawMessage = messages[code];
				if (rawMessage || rawMessage === "") {
					var args = arguments;
					message = rawMessage.replace(/\{(\d)\}/g, function (match, p1, offset, string) {
						var index = parseInt(p1, 10);
						return args[index + 1] || "";  
					});
				}
				return message;
			}
		}
	}

})(jQuery);