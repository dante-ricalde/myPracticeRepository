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
			var that = this; 
			this.$containerSettings = containerSettings;
			$(window).blur(function() {
				console.log('you have lost the focus.');
				that.$containerSettings.hasFocus = false;
			});
			$(window).focus(function() {
				console.log('you have recovered the focus.');
				that.$containerSettings.hasFocus = true;
				if (that.isUserWindowOpen()) {
					that.lookForConfirmingReadMessages();
				}
			});
			this.$userWindow = $("<div id = 'window" + $(container).text() + "' class='chat-window'>");
			this.$userLi = userLi;
			this.options = $.extend({
				language: "en",
				display: true,
				messageResolver: function () {},
				width: 500,
				height: 400,
				position: {x: 'center', y: 'center'},
				title: this.$userLi.html(),
				buttonText : ""
			}, options || {});
			this.$messages = $("<div class='chat-text-messages'>");
			this.queueMessages = [];
			this.$userWindow.append(this.$messages);
			this.$messageInput = $("<input>");
			this.$userWindow.append(this.$messageInput);
			this.$buttonSubmit = $("<button>");
			var buttonText = (that.options.buttonText ? that.options.buttonText : messageResolver.getMessage("chat.window.button.defaultText"));
			this.$buttonSubmit.text(buttonText);
			this.$userWindow.append(this.$buttonSubmit);
			this.stompClientDestination = container.stompClientDestination;

			if (this.options.display) {
				// stompClientDestination.subscribe('/appChatRelay/chat/receiveMessage', function (frame) {
				that.stompClientDestination.done(function () {
					// We make a subscribe to listen for messages sent from a contact (or from a sender)
					that.stompClientDestination.subscribe('/exchange/chatDirectExchange/messages-' + that.$containerSettings.params.userName + '-' + that.$userLi.text(), function (frame) {
						var $p = $("<p class='chat-received-message'>");
						var message = JSON.parse(frame.body);
						that.addMessage(message, message.sender !== that.$containerSettings.params.userName);
						that._init(); 
						// if (!that.$userWindow.dialog("isOpen")) {
						//   that.show(that.options.position.x, that.options.position.y);
						// } // If the message is received from the sender and it's not a confirmation that it has been received by the server
						// and also it's not a confirmation that the client has received the message
						if (!message.receivedByServer && !message.receivedByClient) {
							that.confirmReceivedMessage(message);
						}    
						//frame.ack({'id':message.id});
					}, /*{ack: 'client-individual'}*/ {});
					that._connect();
					console.log('loading the messages .');
					that._loadMessages();  
					that.$userLi.click(function (event) {
						//that._loadMessages();
						that._connect(); 
						that.show(that.options.position.x, that.options.position.y);
					});
				});
			}
		};

		chat.view.UserView.prototype = {
				
				_loadMessages: function () {
					var that = this;
					// We load the messages stored in our queue. Remember that in that queue are stored the messages sent from us to our contact, and the  
					// messages sent by our contact to us
					this.stompClientDestination.subscribe('/appChatRelay/chat/getMessages/' + this.$containerSettings.params.userName + '-' + this.$userLi.text(), function (frame) {
						var messages = JSON.parse(frame.body);
						$.each(messages, function (index, message) {
							that.addMessage(message, message.sender !== that.$containerSettings.params.userName, messages);
						});
					});
				},
				
				_connect: function () {
					var that = this;
					that.$buttonSubmit.click(function () {
						console.log('clicking send with message: ' + that.$messageInput.val());
						if (that.$messageInput.val()) {
							var messageChat = {
								"message": that.$messageInput.val(),
								"sender": that.$containerSettings.params.userName,
								"recipient": that.$userLi.text(),
								// "time": new Date().toString(),
								"userWindowTimeZone": new Date().toString().match(/GMT\+[0-9][0-9][0-9][0-9]/)[0],
								"id": new Date().getTime(),
								"state": "PENDING_TO_SEND"
							};
  
							that.addMessage(messageChat, false);
							that.$messageInput.val('');
							that.$messageInput.focus();
							that.stompClientDestination.send('/appChatRelay/chat/sendMessage/' + messageChat.sender + '/' + messageChat.recipient, {}, JSON.stringify(messageChat));
							// stompClientDestination.send('/appChatRelay/chat/sendMessage', {'simpUser': "keluchis"}, JSON.stringify(messageChat));
						}
					});
				},
				
				_disconnect: function () {
				
				},
				
				_init: function () {
					var that = this;
					if (!that.options.hasLoaded) {
						that.$userWindow.dialog({
							dialogClass: 'chat-window-dialog',
							title: that.options.title,
							autoOpen: false,
							width: that.options.width,
							height: that.options.height,
							open: function (event, ui) {
								that.$messageInput.focus();
								if (that.$containerSettings.hasFocus != false) {
									that.lookForConfirmingReadMessages();
								}
							},
							close: function (event, ui) {
								that.options.hasLoaded = false;
								that.$userWindow.dialog('destroy');
							}
						});
						that.options.hasLoaded = true;
					}
					if (!that.isUserWindowOpen()) {
						that.$userWindow.dialog('open');
					}
					that.$messages.prop('scrollTop', this.$messages.prop('scrollHeight'));
				}, 
				show: function (x, y) {
					var that = this;
					that._init();
					// that.$userWindow.dialog("open");
				},
				addMessage: function (message, received, messages) {
					this.queueMessages.push(message);
					var $p = $('span:data(messageId)', this.$userWindow).filter(function () { return $(this).data('messageId') == message.id });
					if (message.userTimeZone) {
						//
					} else if (message.time && message.userWindowTimeZone) {
						// I convert the message using the time zone of the window of the user
						message.time = moment(message.time).utcOffset(message.userWindowTimeZone).format('YYYY-MM-DD HH:mm Z');
					}   
					var timeText = message.time ? "<span class='chat-message-time'>" + message.time.match(/(2[0-3]|[01]?[0-9]):([0-5]?[0-9])/)[0] + "</span>" : '';
					var tick = '<span class="chat-message-tick default">\u2713</span>';
					// If the message that I sent has been received by the server or I have received the notification by the client (user destination)
					// that He has received the message
					var tickText = (message.receivedByServer || message.receivedByClient) && !received ? tick : '';
					// If the message is in the window  
					if ($p && $p.length > 0) {
						// If the message text hasn't double ckeck I can add a check    
						if ($('.chat-message-tick', $p).length < 2) {
							// If the messages text hasn't the time added I add it
							$p.html($p.html() + (!$p.html().match(/(2[0-3]|[01]?[0-9]):([0-5]?[0-9])/) ? timeText : '') + tickText);
						}
					} else { // If the message is not in the window is going to be added to the window
						var $msgChatDiv = (received ? $("<div class='message received-message'></div>") : $("<div class='message sent-message'></div>"));
						var $p = (received ? $("<span class='chat-message chat-received-message'>") : $("<span class='chat-message chat-sent-message'>"));
						$p.data('message-id', message.id);
						if (message.state === 'DELIVERED') { tickText += tickText; }
						$p.html(message.message + timeText + tickText);
						this.$messages.append($msgChatDiv.append('<span class="tail-container highlight"></span>').append($p));
					}
					if (message.state === 'PENDING_TO_SEND' && messages && messages.length > 1) {
						// We look for a message RECEIVED by the same id in the messages received
						var receivedMessage = $.grep(messages, function (a) { return a.id === message.id && a.state === 'RECEIVED'; });
						if (!(receivedMessage && receivedMessage.length > 0)) {
							this.confirmReceivedMessage(message);
						}
					} else if (message.state === 'READ' && !received) {
						$('.chat-message-tick', $p).removeClass('default').addClass('read');
					}
					$p.data('state', message.state);
					this.$messages.prop('scrollTop', this.$messages.prop('scrollHeight'));
				},
				getChatWindow: function () {
					return this.$userWindow;
				},   
				cleanMessage: function (message) {
					message.message=null;
					message.time=null;
					return message;
				},
				confirmReceivedMessage: function (message) {
					var that = this;
					message.receivedByClient=true;
					// We sent a message to the sender indicating that we have received its message (so that the sender can mark double check in its user chat window)
					message.state = "DELIVERED";
					//that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-notification', {}, deliveredMessage);
					// We send the message again to the sender so that its queue to store the new state (DELIVERED) of this message
					that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-' + message.recipient, {}, JSON.stringify(that.cleanMessage(message)));
					// We send a message again to our queue to store the new state (RECEIVED) of this message
					message.state="RECEIVED";
					that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.recipient + '-' + message.sender, {}, JSON.stringify(that.cleanMessage(message)));
					// If the window is open and the browser has the focus
					// window.alert(that.isUserWindowOpen() + 'and' + that.$containerSettings.hasFocus);
					if (that.isUserWindowOpen() && that.$containerSettings.hasFocus != false) {
						message.state = "READ";
						// We send the message again to the sender so that its queue to store the new state (READ) of this message
						that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-' + message.recipient, {}, JSON.stringify(that.cleanMessage(message)));
					}
				},
				confirmReadMessage: function (message) {
					var that = this;
					message.receivedByClient = true;
					// We sent a message to the sender indicating that we have received its message (so that the sender can mark double check in its user chat window)
					message.state = "READ";
					//that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-notification', {}, deliveredMessage);
					// We send the message again to the sender so that its queue to store the new state (DELIVERED) of this message
					that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.sender + '-' + message.recipient, {}, JSON.stringify(that.cleanMessage(message)));
					// We send a message again to our queue to store the new state (RECEIVED) of this message
					that.stompClientDestination.send('/exchange/chatDirectExchange/messages-' + message.recipient + '-' + message.sender, {}, JSON.stringify(that.cleanMessage(message)));
				},
				lookForConfirmingReadMessages: function () {
					var that = this;
					//var $receivedMsgs = $('.message.received-message span:data(messageId)', that.$userWindow).filter(function () { return $(this).data('state') === 'RECEIVED'});
					// We retrieved the RECEIVED messages from our queue
					var receivedMessages = $.grep(that.queueMessages, function (a) { return a.state === 'RECEIVED'; });
					//$deliveredP.index("[data-state='RECEIVED']") this doesn't work, I need to tried increasing the version of jquery
					//var $receivedP = $receivedMsgs.filter(function () {return $(this).data('state') === 'RECEIVED'});
					$.each(receivedMessages, function (index, message) {
						// We look for a message READ by the same id in our browser stored messages
						// replace grep by findOne or find from underscore to get only one element and not looping all the messages else stopping
						// when the first message is found
						var readMessage = _.find(that.queueMessages, function (a) { return a.id === message.id && a.state === 'READ'; });
						if (!readMessage) {
							that.confirmReadMessage(message);
						}
					});
				},
				isUserWindowOpen: function () {
					return this.options.hasLoaded && this.$userWindow.dialog('isOpen');
				}
			}
		}
	}
)(jQuery);
