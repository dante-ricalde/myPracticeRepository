$(function () {
	
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
	});
   
	var options = {   
			params: $('#chatContent').data('params'),   
			chatView: {   
				width: 450,  
				height: 350 
			}
	};
	
	$('#chatContent').chat(options);
	
	// This didn't work to make an ajax call with basic authorization. It always response 405 NOT ALLOWED, and always makes first a preflight call OPTIONS  
	// before send the POST request 
	// $.post('http://localhost:15672/api/queues/vhost/messages-dante-keluchis/get',{username: 'guest',password:'guest'}, function(data ) { 
	// console.log("datareceived for " + data);
	// });    
	// This didn't work to make an ajax call with basic authorization. It always response 405 NOT ALLOWED, and alwaysmakes first a preflight call OPTIONS
	// before send the POST request  
	/* var req = $.ajax({ 
		 	type: 'POST', 
		 	url: "htt p://localhost:15672/api/queues/% 2F/messages-dant e-keluchis/get", 
		 	headers : { "Authorization": "Basic Z3Vlc3Q6Z 3Vlc3Q=" }, 
		 	data: {"count":5,"requeue":true,"en coding":"auto"," truncate":50000}, 
		 	contentType: "applicat ion/json", 
		 	username: 'guest', password: 'guest', 
		 	xhrFields: { with Credentials: true }, 
		 	beforeSend: function (xhr) { 
		 					xhr.setRequestHeader("Authorization ", "Basic Z3Vlc3Q6Z3Vlc3Q="); 
		 				} 
	 	}).done(function( msg ) { 
	 		alert( "Data Saved: " + msg ); 
	 	}).fail(function () { 
	 		console.log('failed');
		}).always(function () { 
			console.log('always' ); 
		});*/ 
});

