/**
 * jQuery chat plugin
 * Require:   
 *  - jquery
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
						title: undefined,
						buttonText: ""    
					} 
				};
				$.extend(true, settings, options || {});
				return this.each(function () {
					console.log("each invoked for this: " + this + " with id: " + this.id);
					var that = this;
					var language = $.extend({}, $.chat.language["en"], $.chat.language[settings.language]);
					var messageResolver = new general.util.MessageResolver(language.messages);
					var $ul = $('<ul class="users">');
					var defer = $.Deferred();
					var socketDestination = new SockJS("/anyUrlPattern/chatRelay");
					that.stompClientDestination = Stomp.over(socketDestination);
					defer.promise(that.stompClientDestination);
					that.stompClientDestination.connect({}, function (frame) {
						defer.resolve();
						that.stompClientDestination.subscribe('/exchange/chatDirectExchange/messages-' + settings.params.userName + '-notification', function (frame) {
							var $body = JSON.parse(frame.body);
							// If the receiver (client - user destination) notifies me that He has received the message that I sent
							if ($body.receivedByClient) {
								var $li = $("li:contains('" + $body.recipient + "')", $ul);
								var $userView = $li.data('userView');
								$userView.addMessage($body);
							} else if ($("li:contains('" + $body.sender + "')", $ul).length < 1) {
								// Add the contact user to the list of contact users connected
								addLitoUl($ul, $body.sender, settings, messageResolver, that);
							}
						});
					});
					$.post(settings.params.welcomeUrl, {}, function( data ) {
						console.log("data received for " + settings.params.welcomeUrl  + ": " + data);
						$.each(data, function (index, value) {
							addLitoUl($ul, value, settings, messageResolver, that);
						});
						// We connect this user to receive notification
//  					var socketDestination = new SockJS("https://newwebsocketapitest.cfapps.io:4443/anyUrlPattern/chatRelay");
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
