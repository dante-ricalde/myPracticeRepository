/**
 * jQuery chat plugin
 * Require: 
 * 	- jquery
 */
$(function () {
	
	"use strict";
	
	if (!$.chat) {
		$.chat = {};
	}
	
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
    });   
    
    var methods = {
    		_init: function (options) {
    			console.log("_init invoked with options: " + options);
    			var settings = {
    				language: "en",
    				chatView: {
    					width: 500,
    					height: 400,
    					position: {x: 'center', y: 'center'},
    					title: "",
    					buttonText: ""
    				}	
    			};
    			$.extend(true, settings, options || {});
    			return this.each(function () {
    				console.log("each invoked for this: " + this + " with id: " + this.id);
    				var that = this;
    				var language = $.extend({}, $.chat.language["en"], $.chat.language[settings.language]);
    				var messageResolver = new general.util.MessageResolver(language.messages);
    				$.post(settings.params.welcomeUrl, {}, function( data ) {
    					console.log("data received for " + settings.params.welcomeUrl  + ": " + data);
    					var $ul = $('<ul>');
    					$.each(data, function (index, value) {
    						addLitoUl($ul, value, settings, messageResolver, that);   						
    					});
    					// We connect this user to receive notification
//    					var socketDestination = new SockJS("https://newwebsocketapitest.cfapps.io:4443/anyUrlPattern/chatRelay");
    					var socketDestination = new SockJS("/anyUrlPattern/chatRelay");
						var stompClientDestination = Stomp.over(socketDestination);
						stompClientDestination.connect({}, function (frame) {
							stompClientDestination.subscribe('/exchange/chatDirectExchange/messages-' + settings.params.userName + '-notification', function (frame) {
								var $body = JSON.parse(frame.body);
								if ($body.receivedByClient) {
									var $li = $("li:contains('" + $body.recipient + "')", $ul);
									var $userView = $li.data('userView');
									var $p = $('p:data(messageId)', $userView.getChatWindow()).filter(function () {return $(this).data('messageId') === $body.id});
									if ($p){
										var tick = '\u2713';
										$p.html($p.html()+tick);
									}
									//alert('message has arrived');
								} else if ($("li:contains('" + $body.sender + "')", $ul).length < 1) {
									addLitoUl($ul, $body.sender, settings, messageResolver, that);
								}
							});
						});
    					$(that).prepend($ul);
    				});    				
    			});
    		}
    };
    
    /**
     * The main method of this plugin, it does this plugin to start
     * @param {Object} method The object containing all the configuration of this plugin (the options)
     */
    $.fn.chat = function (method) {
    	if (methods[method]) {
    		return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
    	} else if (typeof method === 'object' || !method) {
    		return methods._init.apply(this, arguments);
    	} else {
    		return $.error("Method " + method + " not found in jquery.chat");
    	}
    };
    
    function addLitoUl(ul, value, settings, messageResolver, container) {
    	var $li = $('<li>');
		$li.html(value);
		var userViewOptions = $.extend(true, {}, settings.chatView, {
			messageResolver: messageResolver
		});
		ul.append($li);
		var userView = new chat.view.UserView(userViewOptions, container, settings, $li, messageResolver);
		$li.data('userView', userView);
    }
 });
