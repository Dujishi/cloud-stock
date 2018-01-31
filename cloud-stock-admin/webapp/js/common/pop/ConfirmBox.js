/**
 * VueJS实现 Created by winner.
 */
define(function(require, exports, module) {

	Vue.component('fx-confirm-box', {
		template : require("text!./ConfirmBox.html"),
		props: {
			show: {
				type: Boolean, default: false
			},
			modal: {
				type: Boolean, default: true
			},
			title: String,
			width: { // 尺寸
				type: String,
				default: "400"
			},
			align: { // 位置
				type: String,
				validator:  function(val) {
					return ['center', 'left', 'right', 'justify'].indexOf(val) > -1;
				},
				default: "center"
			},
			data: Object
		},
		watch:{
			show: function(val){
				this.$emit('update:show', val);
			}
		},
		methods:{
			onConfirm(){
				this.$emit('confirm', this.data);
			},
			onCancel(){
				this.$emit('cancel', this.data);
			}
		}
	});

})
