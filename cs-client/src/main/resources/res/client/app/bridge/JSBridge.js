
JSBridge = function() {};
JSBridge.config = function(key) {
	return JSON.parse($JSBridge$.config(key));
}
JSBridge.call = function(service, params) {
	var args = [];
	for (var i = 1; i < arguments.length; i++) {
		args.push(arguments[i]);
	}
	return JSBridge.jsonCall(service, JSON.stringify(args));
}
JSBridge.jsonCall = function(service, params) {
	return JSON.parse($JSBridge$.call(service, params));
}
