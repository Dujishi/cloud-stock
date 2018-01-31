/**
 * VueJS实现 Created by winner.
 */
define(function(require, exports, module) {
	var Request = require("js/common/utils/RequestUtil");
	Vue.component('fx-user-autocomplete', {
		template : require("text!./AutoComplete.html"),
		props: {
			value: String,
			disabled: {//DB账户对象
				type: Boolean,
				default: function(){
					return false;
				}
			},
		},
		data: function(){
			return {
				modelValue: this.value,
				userList: [],
			}
		},
		mounted:function(){
			this.$watch("modelValue", function(val){//当前组件值变更时，发布 input 事件给父组件
				this.$emit("input", val);
			});
			this.$watch("value", function(val){//监听父亲组件值的变更
				this.modelValue = val;
			});
		},
		methods:{
	        queryUserListByName(userName) {// 查找用户列表
	        	if(!userName)
	        		return;
	        	clearTimeout(this.queryUserListTimeout);
	        	var vue = this;
	        	this.queryUserListTimeout = setTimeout(() => {
	        		Request.request(vue, {url: '/core/permission/users', params:{name: userName}}, function (resp) {
	        			var rt = resp.data; // List<UserVO>
	        			if(rt.success){
	        				rt.data.forEach(user => {
	        					if(!user.nickName){
	        						user.nickName = user.name;
	        					}else if(user.name != user.nickName){
	        						user.nickName = user.name + " (" + user.nickName + ")";
	        					}
	        				});
	        				vue.userList = rt.data;
	            		} else { //设置错误信息
	            			vue.$message({message: "无法获取用户数据：" + resp.data.message, type: 'error', duration: 5000, showClose: true});
	            		}
	            	});
	        	}, 500);
	        }
		}
	
	});
})
