/**
 * VueJS实现 Created by winner.
 */
define(function(require, exports, module) {

	Vue.component('fx-props-editor', {
		template : require("text!./PropsEditor.html"),
		props: {
			type: { // 编译类型
				type: String,
				validator:  function(val) {
					return ['select', 'input', 'password', 'date'].indexOf(val) > -1;
				},
				default: 'select'
			}, 
			initValue: { //select - Option对象选中的key或value值；input - 输入框中的原始值；
				 required: true
			},
			params:{
				type: Object,
				default: function(){
					return {
						datePickerOptions: null, // 日期选项约束函数
					}
				}
			},
			selectItems: { // 当类型为 select 时使用
				type: Object,
				validator: function(val){
					return this.type == 'select'? val != null : true;
				}
			},
			align: { // 编辑按钮位置
				type: String,
				validator:  function(val) {
					return ['follow', 'right', 'left'].indexOf(val) > -1;
				},
				default: 'follow'
			}, 
			width:{
				type: [String, Number],
				default: 250
			}
		},
		data : function() {
			return {
				inputValue: this.initValue,
				iconAlign: "",
				contentAlign: "",
				popShow: false
			};
		},
		computed:{
//			inputValue: {
//				get: function(){
//					return this.initValue;
//				},
//				set: function(val){
//					this.$emit('update:initValue', val);
//				}
//			},
		},
		created: function(){
			if(this.align == 'right'){
				this.iconAlign = "position: absolute; right:10px; top:8px;";
			} else if(this.align == 'left'){
				this.iconAlign = "position: absolute; left:-5px; top:8px";
				this.contentAlign = "padding-left: 5px;";
			}
		},
		watch:{
			popShow: function(val) {
				if(val == true){// 当窗体显示时，输入框的值还原成原始值
					this.inputValue = this.initValue;
				}
			},
			initValue: function(val){
				this.inputValue = val;
			}
		},
		methods:{
			submit: function(){
				//提交数据，远程请求，成功
				var data = {};
				if(this.type == 'select'){
					data = {key: this.inputValue, value: this.selectItems[this.inputValue]};
				} else if(this.type == 'input' || this.type == 'password' || this.type == 'date'){
					data = this.inputValue;
				}
				this.$emit('submit', data);
				this.popShow=false;
			},
			onPopShow: function(){
				this.$emit('popShow');
			}
		}
	});

})
