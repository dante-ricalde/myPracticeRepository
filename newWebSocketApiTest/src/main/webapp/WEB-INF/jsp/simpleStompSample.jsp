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
	Hi Stomp
	<SCRIPT type="text/javascript">
	// Connecting using stomp.js and the sockjs-client
// 	console.log("Connecting using stomp.js and the sockjs-client...");
// 	var socket = new SockJS("/webSocketApiTest/anyUrlPattern/portfolio");
// 	var stompClient = Stomp.over(socket);
// 	stompClient.connect({}, function (frame) {
		
// 	});
	// Connecting via WebSocket (without SockJS)
// 	console.log("Connecting via WebSocket (without SockJS)...")
// 	var socket1 = new WebSocket("ws://localhost:8080/webSocketApiTest/anyUrlPattern/portfolio");
// 	socket1.onopen = function() {
// 	 console.log("Websocket Ready!!");
// 	}
// 	var stompClient1 = Stomp.over(socket1);
// 	stompClient1.connect({}, function (frame) {
		
// 	});
	// 
	console.log("Sending to a destination. Then messages are delegated to @SubscribeMapping and @MessageMapping annotated methods.");
	var socketDestination = new SockJS("http://localhost:8080/webSocketApiTest/anyUrlPattern/portfolio");
	//var socketDestination = new WebSocket('ws://localhost:8080/webSocketApiTest/anyUrlPattern/app/greeting/hola');
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.send('/app/greeting', {}, "Hello, STOMP");
	});
</SCRIPT>
</body>
</html>
</jsp:root>