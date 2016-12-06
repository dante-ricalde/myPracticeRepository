<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:sec="http://www.springframework.org/security/tags">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1" 
		pageEncoding="ISO-8859-1" session="false"/>
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<jsp:include page="/WEB-INF/views/includes/taglibs.jsp" />
	<c:url value='../../js/jquery-2.1.4.js' var="jQueryUrl" />
	<c:url var="welcomeUrl" value="/anyUrlPattern/chat/welcome" />
<head>
<title>My Chat</title>
<sec:csrfMetaTags />
<link rel="stylesheet" type="text/css" href="../../css/jquery/jquery-ui-1.11.4.css"><!-- necessary to work --></link>
<link rel="stylesheet" type="text/css" href="../../css/jquery/jquery-ui.structure-1.11.4.css"><!-- necessary to work --></link>
<link rel="stylesheet" type="text/css" href="../../css/jquery/jquery-ui.theme-1.11.4.css"><!-- necessary to work --></link>
<link rel="stylesheet" type="text/css" href="../../css/jquery.chat.css"><!-- necessary to work --></link>
<script src="../../js/sockjs-1.0.3.js" type="text/javascript"><!-- necessary to work --></script>
<script src="../../js/stomp-1.2.js" type="text/javascript"><!-- necessary to work --></script>
<script src="${jQueryUrl}"><!-- necessary to work --></script>
<script src="../../js/jquery/jquery-ui-1.11.4.js"><!-- necessary to work --></script>

<SCRIPT src="../../js/lib/moment-timezone.min.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
<SCRIPT src="../../js/general-util.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
<SCRIPT src="../../js/chat-user-view.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
<SCRIPT src="../../js/jquery.chat.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
<SCRIPT src="../../js/jquery.chat-en.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
<SCRIPT src="../../js/myChat.js" type="text/javascript"><!-- necessary to work --></SCRIPT>
</head>
<body>
	<h1>Welcome to this chat!!!</h1>
	<h2>Users connected:</h2>
	<sec:authentication property="principal.username" var="principalUserName" />
	<DIV id="chatContent" data-params='{"welcomeUrl": "${welcomeUrl}", "userName": "${principalUserName}"}'>
	
	</DIV>
</body>
</html>
</jsp:root>