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
<script src="../js/sockjs-1.0.0.js" type="text/javascript"><!-- necessary to work --></script>
<script src="../js/stomp-1.2.js" type="text/javascript"><!-- necessary to work --></script>
<script src="${jQueryUrl}"><!-- necessary to work --></script>
</head>
<body>
	Hi Stomp.
	In this case I have made a simple test of a Full-Featured broker using RabbitMQ. I have a text input where the user writes a message, when the user clicks the button Send, the message is received by the method 'chat' (/chat) in
	the Greeting controller, then that method forwards the message to the topic '/topic/greetings' to RabbitMQ. When a browser is open a stomp client is subscribed to that previously mentioned topic and receives the message which is written 
	in the textarea 'messgaContentId'.
	Note: When I open 3 browsers and I write a message in the input text and then press the button Send, the message is received by all 3 browsers.
	<SCRIPT type="text/javascript">
	console.log("");
	var socketDestination = new SockJS("http://localhost:8080/webSocketApiTest/anyUrlPattern/portfolioBrokerRelay");	
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.subscribe('/topic/greetings', function (frame) {
			//alert("Data received from the broker (topic): \n" + frame.body);
			$("#messgaContentId").text($("#messgaContentId").text() + '\n' + frame.body);
		});
		stompClientDestination.send('/appBrokerRelay/chat', {}, "Hello, STOMP");
		// In this case I am going to send an HTTP post request to be handled by the controller /greetings	
		$.post("<c:url value='/anyUrlPattern/greetingsMVC' />", { greeting: "Hi Family!!!" }, function( data ) {
			$('#content').html(data);
		});
	});
	
	function sendMessage() {
		var text = $("#messageId").val();
		stompClientDestination.send('/appBrokerRelay/chat', {}, text);
	}
</SCRIPT>
<div style="display: block;">
	<TEXTAREA id="messgaContentId" rows="16">texto</TEXTAREA>
	<DIV>
		<INPUT id="messageId" />
		<BUTTON id="sendButtonId" onclick="javascript: sendMessage()">Send</BUTTON>	
	</DIV>
	<DIV id="content">
	</DIV>
</div>
</body>
</html>
</jsp:root>