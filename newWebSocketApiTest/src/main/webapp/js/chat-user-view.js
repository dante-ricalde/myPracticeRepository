/**
* 
*/
var _canLog = true;

(function ($) {
	
	"use strict";
	
	if (!window.chat) {
		window.chat = {};
	}
	
	if (!chat.view) {
		chat.view = {};
	}
	
	if (!chat.view.UserView) {
		chat.view.UserView = function (options, container, containerSettings, userLi, messageResolver) {
			console.log("User View creation invoked");
	    	this.$userWindow = $("<div id = 'window" + $(container).text() + "' class='chat-window'>");
	    	this.$userLi = userLi;
	    	var that = this;
	    	this.options = $.extend({
	    		language: "en",
	    		display: true,
	    		messageResolver: function () {},
	    		width: 500,
				height: 400,
				position: {x: 'center', y: 'center'},
				title: "",
				buttonText: ""
	    	}, options || {});
	    	this.$messages = $("<div class='chat-text-messages'>");
	    	this.$userWindow.append(this.$messages);
	    	this.$messageInput = $("<input>");
	    	this.$userWindow.append(this.$messageInput);
	    	this.$buttonSubmit = $("<button>");
	    	var buttonText = (that.options.buttonText ? that.options.buttonText : messageResolver.getMessage("chat.window.button.defaultText"));
	    	this.$buttonSubmit.text(buttonText);
	    	this.$userWindow.append(this.$buttonSubmit);
	    	this.$containerSettings = containerSettings;
	    	this.$stompClientDestination;
	        
	        if (this.options.display) {
	        	var socketDestination = new SockJS("/anyUrlPattern/chatRelay");
//		    	var socketDestination = new SockJS("https://newwebsocketapitest.cfapps.io:4443/anyUrlPattern/chatRelay");
				this.stompClientDestination = Stomp.over(socketDestination);
				this.stompClientDestination.connect({}, function (frame) {
		//			stompClientDestination.subscribe('/appChatRelay/chat/receiveMessage', function (frame) {
					that.stompClientDestination.subscribe('/exchange/chatDirectExchange/messages-' + that.$containerSettings.params.userName + '-' + that.$userLi.text(), function (frame) {					
						var $p = $("<p class='chat-received-message'>");
						var message = JSON.parse(frame.body);
						that.addMessage(message, message.sender !== that.$containerSettings.params.userName);
						that._init();
//						if (!that.$userWindow.dialog("isOpen")) {
//							that.show(that.options.position.x, that.options.position.y);
//						}// If the message is received from the sender and not a confirmation that it has been received by the server.
						if (!message.receivedByServer) {
							//frame.ack({'id':message.id});
							message.receivedByClient=true;
							that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-notification', {}, JSON.stringify(message));
						}
					}, {ack: 'client-individual'});
				});
				that._connect();
	        	that.$userLi.click(function (event) {
	        		that._connect();
	        		that.show(that.options.position.x, that.options.position.y);
	        	});
	        }
		};
		
		chat.view.UserView.prototype = {
				
				_connect: function () {
					var that = this;			    	
					that.$buttonSubmit.click(function () {
			            console.log('clicking send with message: ' + that.$messageInput.val());
			            if (that.$messageInput.val()) {
			            	var messageChat = {
			            		"message": that.$messageInput.val(),
			            		"sender": that.$containerSettings.params.userName,
			            		"recipient": that.$userLi.text(),
			            		"id": new Date().getTime()
			            	};
			            	//that.addMessage(that.$messageInput.val(), false);
							that.$messageInput.val('');
							that.$messageInput.focus();
							that.stompClientDestination.send('/appChatRelay/chat/sendMessage/' + messageChat.sender + '/' + messageChat.recipient, {}, JSON.stringify(messageChat));
//			            	stompClientDestination.send('/appChatRelay/chat/sendMessage', {'simpUser': "keluchis"}, JSON.stringify(messageChat));
			            }
			        });
				},
				_disconnect: function () {
					
				},
				_init: function () {
					var that = this; 
					that.$userWindow.dialog({
						autoOpen: false,
						width: that.options.width,
						height: that.options.height,
						open: function (event, ui) {
							that.$messageInput.focus();    			    			
						},
						close: function (event, ui) {
							that.$userWindow.dialog('destroy');
						}
					});
					if (!that.$userWindow.dialog('isOpen')) {
						that.$userWindow.dialog('open');
					}
				},
				show: function (x, y) {
					var that = this;
					that._init();
					that.$userWindow.dialog("open");
				},
				addMessage: function (message, received) {
					var $p = (received ? $("<p class='chat-received-message'>") : $("<p class='chat-sent-message'>"));
					$p.data('message-id', message.id);
					var timeText = "<span class='chat-message-time'>"+ message.time.match(/(2[0-3]|[01]?[0-9]):([0-5]?[0-9])/)[0] + "</span>"
					var tick = '\u2713';
					var tickText = message.receivedByServer ? tick : '';
					$p.html(message.message + timeText + tickText);
					this.$messages.append($p);
					this.$messages.prop('scrollTop', this.$messages.prop('scrollHeight'));
				},
				getChatWindow: function () {
					return this.$userWindow;
				}
		}
	}
	
})(jQuery);