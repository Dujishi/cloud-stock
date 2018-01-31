
function func(){
	alert("JS加载成功");
}
//alert("JS File ... bb");

function pageOnLoad(){
	document.writeln("<div onclick='pageOnLoad()'>点击我</div><br/>");
	if(getBridge()){
		document.write("AAA配置项 app.main.url : ");
		document.write(JSBridge.config("app.main.url"));
		document.writeln("<br/>---");
		document.writeln(bridge); 
	} else {
		document.write('Bridge 无法获得')
	}
	document.writeln("<br/>============================");
	
};

function getBridge(){
	try{
		return JSBridge;
	}catch(e){
		return null;
	}
}
//setTimeout(pageOnLoad, 1)