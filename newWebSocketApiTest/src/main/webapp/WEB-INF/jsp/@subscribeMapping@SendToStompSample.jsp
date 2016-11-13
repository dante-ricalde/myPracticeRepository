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
	In this case I am connecting to the websocket endpoint at /portfolioSubscriber.
	Inmediately I am subscribing to a controller method /subscribeAndSendTo. (In this case I am using the @SendTo annotation where the resulting message is sent to the "brokerChannel" to the specified target destination /topic/subscribeAndSendToResponse)
	to fetch application data when the app UI is being initialized, but to receive the response from the destination /topic/subscribeAndSendToResponse I need to make another subscribe to the broker to that destination. 
	Inmediately I am sending a message which path is the folowing: browser-> clientInboundChannel --> broker --> clientOutboundChannel --> subscriber (/topic/greeting) (browser) 
	<SCRIPT type="text/javascript">	 
	console.log("In this case I am connecting to the websocket endpoint at /portfolioSubscriber. \
			Inmediately I am subscribing to a controller method /subscribeAndSendTo. (In this case I am using the @SendTo annotation where the resulting message is sent to the 'brokerChannel' to the specified target destination /topic/subscribeAndSendToResponse)\
			to fetch application data when the app UI is being initialized, but to receive the response from the destination /topic/subscribeAndSendToResponse I need to make another subscribe to the broker to that destination. \
			The single thread is the following:  browser -> clientInboundChannel -> Controller (/appSubscriber/subscribeAndSendTo) -> broker (@SendTo - /topic/subscribeAndSendToResponse) --> subscriber (/topic/subscribeAndSendToResponse - browser)\
			For this happens ok you need to make the subscribe calls first so that they are registered and then used to received responses \
			Inmediately I am sending a message which path (a single thread) is the folowing: browser-> clientInboundChannel --> broker (/topic/greeting) --> clientOutboundChannel --> subscriber (/topic/greeting) (browser)");
	var socketDestination = new SockJS("http://localhost:8080/webSocketApiTest/anyUrlPattern/portfolioSubscriber");	
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.subscribe('/topic/subscribeAndSendToResponse', function (frame) {
			$('#content').html(frame.body);
		});
		stompClientDestination.subscribe('/appSubscriber/subscribeAndSendTo', function (frame) {
			$('#content').html(frame.body);
		});
		stompClientDestination.subscribe('/topic/greeting', function (frame) {
			alert("Data received from the broker (topic): \n" + frame.body);
		});
		stompClientDestination.send('/topic/greeting', {}, "Hello, STOMP");
	});
</SCRIPT>
<DIV id="content">
</DIV>
</body>
</html>
</jsp:root>