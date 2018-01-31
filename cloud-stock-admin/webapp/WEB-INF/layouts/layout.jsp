<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>伏羲平台管理系统</title>
<script src="<%=request.getContextPath()%>/sea.js"></script>
<script src="<%=request.getContextPath()%>/res/js/jquery-1.12.4.min.js"></script>
</head>
<body id="root">
</body>
</html>

<script type="text/javascript">
seajs.config({
  preload: ['plugin-text']
});
	// 全局环境配置
	var C = window.globalConfig = {
	    ctx:"${pageContext.request.contextPath}", //应用上下文路径
	    user: {name: "${user.name}", nickName: "${user.nickName}"},
	    url: "${pageContext.request.contextPath}${defaultPath}${bodyPage}".replace(/\.html$/g, ""), // 当前页面URL地址，包含context路径
	    path: "${bodyPage}".replace(/\.html$/g, ""), // 当前路径的Path
	    extension: ".html",
	    isIndex: ${isIndex},
	    loginUrl: '/ups/login?target=${pageContext.request.contextPath}',
	    logoutUrl: '/ups/logout'
	}

	// 如果当前页面在顶端页面
    if(self == top){// 加载iFrame的布局页
    	loadAngular(loadLayoutPage);
    }else{//加载 Frame 内页
		window.parent.setBreadcrumb(${breadcrumb});
		loadVue(loadFramePage);
    }
	
function loadAngular(loadedFunc){
	seajs.use([
		// CSS
			"<%=request.getContextPath()%>/res/bootstrap/css/bootstrap.min.css"
			, "<%=request.getContextPath()%>/res/css/font-awesome.min.css"
			, "<%=request.getContextPath()%>/res/css/layout.css"
			, "<%=request.getContextPath()%>/res/css/less/panels.css"
			, "<%=request.getContextPath()%>/res/css/table.css"
			, "<%=request.getContextPath()%>/res/css/simplify.min.css"
			// JS
			,"<%=request.getContextPath()%>/res/js/angular/angular/angular.min.js"
			,"<%=request.getContextPath()%>/res/bootstrap/js/bootstrap.min.js"
		],  function(){
			seajs.use(["https://store.ddyc.com/res/xkcdn/angular-ui-bootstrap/v2.0.0/dist/ui-bootstrap-tpls.js"]
				, loadedFunc);
	});
}

function loadVue(loadedFunc){
	seajs.use([
			// CSS
			"<%=request.getContextPath()%>/res/js/element/theme-2.0.7.css"
			, "<%=request.getContextPath()%>/res/base/base.css"
			// JS
			, jsUrlWrapper("/res/js/vue/vue-2.5.9.min")
			, jsUrlWrapper("/res/js/axios/axios-0.16.2")
		], function(){
			seajs.use(["<%=request.getContextPath()%>/res/js/element/element-2.0.7.js"]
				, loadedFunc);
		});
}

function jsUrlWrapper(url){
	var env = "${applicationScope.conf['container.app.env']}";
	return "<%=request.getContextPath()%>" + url + ((env == 'dev' || env =='int')? '':'.min') + ".js";
}

function loadLayoutPage(){
	jQuery("#root").load(globalConfig.ctx + "/web/common/layouts/layout.html");
}
function loadFramePage(){
	var url = globalConfig.url + globalConfig.extension + "?" + (new Date()).getTime();
	jQuery("#root").load(url);
}

</script>