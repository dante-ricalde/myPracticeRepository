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
	I am sending a message which path (a single thread) is the folowing: browser-> clientInboundChannel --> GreetingController (/greetings) --> broker (/topic/greetings) --> clientOutboundChannel --> subscriber (/topic/greetings) (browser).
	I make a post call, that it's received directly by the controller and then it's forwarded to the broker to be consumed by the client (subscriber), the path is the following:
	browser (post call) -> GreetingController (/greetingsMVC) --> broker (/topic/greetings)	--> clientOutboundChannel --> subscriber (/topic/greetings) (browser)
	<SCRIPT type="text/javascript">
	console.log("I am sending a message which path (a single thread) is the folowing: browser-> clientInboundChannel --> GreetingController (/greetings) --> broker (/topic/greetings) --> clientOutboundChannel --> subscriber (/topic/greetings) (browser).\
			I make a post call, that it's received directly by the controller and then it's forwarded to the broker to be consumed by the client (subscriber), the path is the following:\
			browser (post call) -> GreetingController (/greetingsMVC) --> broker (/topic/greetings)	--> clientOutboundChannel --> subscriber (/topic/greetings) (browser)");
	var socketDestination = new SockJS("http://localhost:8080/webSocketApiTest/anyUrlPattern/portfolioSubscriber");	
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.subscribe('/topic/greetings', function (frame) {
			alert("Data received from the broker (topic): \n" + frame.body);
		});
		stompClientDestination.send('/appSubscriber/greetings', {}, "Hello, STOMP");
		// In this case I am going to send an HTTP post request to be handled by the controller /greetings	
		$.post("<c:url value='/anyUrlPattern/greetingsMVC' />", { greeting: "Hi Family!!!" }, function( data ) {
			$('#content').html(data);
		});
	});
</SCRIPT>
<DIV id="content">
</DIV>
</body>
</html>
</jsp:root>