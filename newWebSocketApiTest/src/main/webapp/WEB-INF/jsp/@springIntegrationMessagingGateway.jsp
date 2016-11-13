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
	<SCRIPT type="text/javascript">
	var socketDestination = new SockJS("http://localhost:8080/anyUrlPattern/chatRelay");	
	var stompClientDestination = Stomp.over(socketDestination);
	stompClientDestination.connect({}, function (frame) {
		stompClientDestination.subscribe('/topic/integrationGreetingResponse', function (frame) {
			$('#content').html($('#content').html() + '<br/>Returned Message: ' + frame.body);
		});
		var messageChat = {
        		"message": "Hello Integration STOMP",
        		"sender": "webapp",
        		"recipient": "app"
        	};
		stompClientDestination.send('/appChatRelay/integrationGreeting', {}, JSON.stringify(messageChat));
		setTimeout(function () {
			stompClientDestination.send('/appChatRelay/integrationGreeting1', {}, JSON.stringify(messageChat));
		}, 5000);
		
	});
</SCRIPT>
<DIV id="content">
</DIV>
</body>
</html>
</jsp:root>