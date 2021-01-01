    var webSocket = new WebSocket("ws://192.168.1.40:8887");
                 
	webSocket.onopen = function(event){
		start();
		if(event.data === undefined) return;
	};
 
	webSocket.onmessage = function(event){
		handel(event.data);
	};
				
    webSocket.onclose = function(event){};
			
	function send(msg){
		webSocket.send(msg);
		console.log(msg);
    }