<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
Hola
<script src="js/sockjs-1.0.3.js" type="text/javascript" />
<script language="javascript" type="text/javascript">
           
// var ws = new WebSocket("ws://localhost:8080/webSocketApiTest/anyUrlPattern/myHandler");
// ws.onopen = function() {
//     console.log("Websocket Ready!!");
//     ws.send("Test");
// }
// ws.onmessage= function(data) {
//     console.log("Message received by web sockect server endpoint: " + data.data);
// }


// var myDomainWS = new WebSocket("ws://localhost:8080/webSocketApiTest/anyUrlPattern/myDomainHandler");
// myDomainWS.onopen = function() {
// 	console.log("My Domain Websocket Ready!!!");
// 	this.send("Test");
// }
// myDomainWS.onmessage = function(data) {
// 	console.log("Message received by my domain web socket server endpoint " + data.data);
// }

// myDomainWS.onerror = function (error) {
// 	console.log("Error received by my domain web socket server endpoint " + error);
// }

// var myDomainWS2 = new WebSocket("ws://mydomain.com:8080/webSocketApiTest/anyUrlPattern/myDomainHandler");
// myDomainWS2.onopen = function() {
// 	console.log("My Domain Websocket Ready!!!");
// 	this.send("Test");
// }
// myDomainWS2.onmessage = function(data) {
// 	console.log("Message received by my domain web socket server endpoint " + data.data);
// }

// myDomainWS2.onerror = function (error) {
// 	console.log("Error received by my domain web socket server endpoint " + error);
// }


// var allOrigingsWS = new WebSocket("ws://localhost:8080/webSocketApiTest/anyUrlPattern/allOriginsHandler");
// allOrigingsWS.onopen = function() {
// 	console.log("All Origins Websocket Ready!!!");
// 	this.send("Test");
// }
// allOrigingsWS.onmessage = function(data) {
// 	console.log("Message received by All Origins web socket server endpoint " + data.data);
// }

// allOrigingsWS.onerror = function (error) {
// 	console.log("Error received by All Origins web socket server endpoint " + error);
// }

</script>
<script>
// By default when the page is refreshed or closed, a frame of close is sent by the browser and the server web socket closes the connection, 
// avoiding refusing to create a new connection because the old is still connected.
// Read this note:
//		The thing of it is there are 2 main protocol versions of WebSockets in use today. The old version which uses the [0x00][message][0xFF] protocol, and then there's the new version using Hybi formatted packets.
//		The old protocol version is used by Opera and ipod/ipad/iphones so it's actually important that backwards compatibility is implemented in WebSockets servers. With these browsers using the old protocol, I discovered that refreshing the page, or navigating away from the page, or closing the browser, all result in the browser automatically closing the connection. Great!!
//		However with browsers using the new protocol version (eg. Firefox, Chrome and eventually IE10), only closing the browser will result in the browser automatically closing the connection. That is to say, if you refresh the page, or navigate away from the page, the browser does NOT automatically close the connection. However, what the browser does do, is send a hybi packet to the server with the first byte (the proto ident) being 0x88 (better known as the close dataframe). Once the server receives this packet it can forcefully close the connection itself, if you so choose.


// The global scope you mentioned is always related to the JavaScript Context, and a Context is created for each windows (and destroyed when the document is unloaded
// 		from the memory). Therefore, your effort are useless:You can't keep a connection opened if the user change page. Of course you can have your webapp as "single page" 
//		application, where all the data are loaded using XMLHttpRequest / ajax / WebSocket. So, leaving the page means leaving / shutdown the application, and makes sense 
// 		close the socket. Another old approach could be put your pages in a frame, where the user navigate only in the frame (even if it takes the whole size of the window). 
//		In that way, you can create your WebSocket in the top most window, that is never changed (that also means the URL showed in the location bar will be always the same).
// HEARTBEATS
// ==========
// THE SERVER IS SENDING HEARTBEAT MESSAGES AND ARE BEING RECEIVED BY THE SOCKJS CLIENT (I HAVE IT DEBUGGED IN THE SOCKJS CLIENT), BUT IN THIS CASE THE LOGS OF THE 
// HEARBEATS ARE SEEN IN THE CONSOLE OF STS BUT NOT IN THE BROWSER DESPITE BEING ENABLE THE DEBUG IN THE SOCKJS CLIENT (I WAS DEBUGGING AND I THINK THAT IS NOT 
// REGISTERED A LISTENER FOR THE HEARTBEAT MESSAGES IN THE CLIENT).
console.log("Testing SockJS");
var options = {
		transports : [ "websocket", "xhr-streaming", "xdr-streaming", "xhr-polling", "xdr-polling", "iframe-htmlfile", "iframe-eventsource", "iframe-xhr-polling" ],
        debug : true,
        //heartbeatTimeout: 65000
    };

var sockJsWS = new SockJS("http://localhost:5011/anyUrlPattern/myHandlerWithSockJs", undefined, options);

// method executed always before a refresh or loading or close of page
window.onbeforeunload = function() {
	sockJsWS.onclose = function () { 
			console.log("Closing the web socket before refreshing or loading the page"); 
	}; // disable onclose handler first
	sockJsWS.close = function () {};
	sockJsWS.close();
};

sockJsWS.onopen = function() {
	console.log("SockJs Websocket Ready!!!");
	this.send("Test");
}
sockJsWS.onmessage = function(data) {
	console.log("Message received by SockJs web socket server endpoint " + data.data);
}

sockJsWS.onerror = function (error) {
	console.log("Error received by SockJs web socket server endpoint " + error);
}

sockJsWS.onclose = function () {
	// executed when the app has restarted or stopped or the browser has been closed.
	console.log("SockJs closing");
}

</script>