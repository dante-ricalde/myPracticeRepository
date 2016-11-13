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
    
 });
