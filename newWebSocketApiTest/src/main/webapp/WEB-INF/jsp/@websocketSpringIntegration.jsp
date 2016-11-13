<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1" 
		pageEncoding="ISO-8859-1" session="false"/>
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<jsp:include page="/WEB-INF/views/includes/taglibs.jsp" />
	<c:url value='../js/jquery-1.6.1.min.js' var="jQueryUrl" />	
<head>
<title>Insert title here</title>
<script src="../js/sockjs-1.0.3.js" type="text/javascript"><!-- necessary to work --></script>
<script src="../js/stomp-1.2.js" type="text/javascript"><!-- necessary to work --></script>
<script src="${jQueryUrl}"><!-- necessary to work --></script>
</head>
<body>
	In this case I am testing the int-websocket-outbound-channel-adapter and int-websocket-inbound-channel-adapter, to test this adapters I am only using sockJs, the inbound adapter receives the
	message from the client and the outbound sends the reply message to the client. In this case the inbound channel adapter is configured to use a broker but the only configured broker is a stomp
	broker and that is not appropriate for the messages received by this adapter because in this case I am only using sockjs and not stomp, hence this broker is not used by this adapter because 
	the received message is of type Message and it doesn't have a destination. (However you could use a broker but that has to be another broker, not a stomp broker. I think that the broker is necessary only
	if you are going to use stomp sending messages to specified destinations, In this case the messages don't have destinations hence the broker is not necessary, this case is only used to test the adapters. If
	you want to use stomp there are proper adapters for stomp).
	
	The second case in this example is using stomp, the new hear is that I am using receipts. When I receive the receipt frames from the server in response to the subscribes operations, I send the message to the
	destination '/appChatRelay/integrationGreeting'. I do it this way because there were cases when the sent message was processed first by the server than the subcribe messages sent from the client and the response messages were not
	consumed by the client because the subscribe happened after (and it's necessary that the subscribe operations been processed first by the severs so that the client to be ready to receive the messages sent by the server
	to the destinations which the client is subscribed).
	<SCRIPT type="text/javascript">
	// ******************************************************* USING STOMP
	var socketDestination = new SockJS("https://localhost:4443/anyUrlPattern/chatRelay");	
	var stompClientDestination = Stomp.over(socketDestination);
	// handling the receipts
	var ready = false;
	var partialReady = false;
	function processReceipt(frame, receiptId) {
		if (frame.headers['receipt-id'] === receiptId) {
			if (partialReady) {
				ready = true;
			} else {
				partialReady = true;
			}
		}
	}
	stompClientDestination.onreceipt = function (frame) {
		processReceipt(frame, 'subscription-1');
		processReceipt(frame, 'subscription-2');
		if (ready) {
			var messageChat = {
		    		"message": "Hello Integration STOMP",
		    		"sender": "dante",
		    		"recipient": "kelly"
		    	};
			stompClientDestination.send('/appChatRelay/integrationGreeting', {}, JSON.stringify(messageChat));
		}
	}
	stompClientDestination.connect({}, function (frame) {
			stompClientDestination.subscribe('/exchange/amq.direct/messages-dante-kelly', function (frame) {
				$('#content').html($('#content').html() + '<br/>Returned Message: ' + frame.body + ', StompOutboundChannelAdapter:' + frame.headers['StompOutboundChannelAdapter']);
			}, {receipt:'subscription-1'});
			stompClientDestination.subscribe('/topic/integrationGreetingResponse', function (frame) {
				$('#content').html($('#content').html() + '<br/>Returned Message: ' + frame.body);
			}, {receipt:'subscription-2'});
	});	
	stompClientDestination.heartbeat.outgoing = 30000; // client will send heartbeats every 20000ms
	stompClientDestination.heartbeat.incoming = 30000;     // client does not want to receive heartbeats from the server
	
	// **************************************** USING ONLY SOCK JS
	var sockJsConn = new SockJS("https://localhost:4443/anyUrlPattern/sockJsChatRelay");
	sockJsConn.onopen = function() {
		console.log("SockJs Websocket Ready!!!");
		this.send("Test SockJs Message");
	}
	sockJsConn.onmessage = function(data) {
		$('#content').html($('#content').html() + '<br/>Returned Message: ' + data.data);
	}
</SCRIPT>
<DIV id="content">
</DIV>
</body>
</html>
</jsp:root>