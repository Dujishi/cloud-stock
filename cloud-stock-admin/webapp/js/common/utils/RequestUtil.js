/**
 * 远程请求工具类：js/common/utils/RequestUtil <br/>
 * 文档：https://www.kancloud.cn/yunye/axios/234845
 */
define(function(require, exports, module) {
	var C = RequestUtil = function(){};
	/**
	 * Axios通用请求工具类
	 */
	C.request = function(vue, request, then){
		var req = { url: null,  method: "get", data: null, params: null, config: {} };
		jQuery.extend(req, request);
		if(req.data != null){//如果有data，默认为post请求
			req.method = "post";
		} else if(req.formData != null){
			req.method = "post";
			config.transformRequest = [transformRequest];
			if(config.headers == null){
				config.headers = {};
			} 
			config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
		}
		if(!req.url){
			vue.$alert("请设置要请求的URL参数！", "错误提示", {type:"error"});
			return;
		}
		jQuery.extend(req.config, {url: globalConfig.ctx + req.url, method: req.method, data: req.data, params: req.params});
		axios(req.config).then(then);
	}

	/**
	 * Axios通用请求工具类，增加确认框逻辑
	 */
	C.requestWithConfirm = function(confirm, vue, request, then, cancel) {
		var defaultConfirm = {message: "确定要执行", title: "请确认", type: "warning"};
		jQuery.extend(defaultConfirm, confirm);
		vue.$confirm(defaultConfirm.message, defaultConfirm.title, { type: defaultConfirm.type }).then(() => {
			try{
				C.request(vue, request, then);
			}catch(e){
			}
        }).catch(() => {
        	if(typeof(cancel) == "function"){
        		cancel();
        	}
        });
		
		function transformRequest(data) {
		    let ret = ''
		    for (let it in data) {
		      ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
		    }
		    return ret
		  }
	}

	module.exports = C;
})
