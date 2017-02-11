<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1" 
		pageEncoding="ISO-8859-1" session="false"/>
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Insert title here</title>
<script src="../js/sockjs-1.0.0.js" type="text/javascript"><!-- necessary to work --></script>
<script src="../js/stomp-1.2.js" type="text/javascript"><!-- necessary to work --></script>
</head>
<body>
	Hi Stomp.
	Sending a message to a topic.
	<SCRIPT type="text/javascript">
	// In this case the flow of the message is:
	// browser --> clientInboundChannel --> broker --> clientOutboundChannel --> subscriber (browser) 
	console.log("Sending to a destination. Then messages are delegated to the broker.");
	var socketDestination = new SockJS("http://localhost:8080/webSocketApiTest/anyUrlPattern/portfolioSubscriber");	
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.subscribe('/topic/greeting', function (frame) { 
			alert(frame.body); 
		});
		stompClientDestination.send('/topic/greeting', {}, "Hello, STOMP");
	});
</SCRIPT>
</body>
</html>
</jsp:root>