/**
 * VueJS实现 Created by winner.
 */
define(function(require, exports, module) {
	Vue.component('fx-item-group', {
		template : require("text!./ItemGroup.html"),
		props: {
			title: String,
			align:{
				type: String,
				validator:  function(val) {
					return ['left', 'center', 'right'].indexOf(val) > -1;
				},
				default: 'center'
			},
			titleAlign:{
				type: String,
				validator:  function(val) {
					return ['left', 'center', 'right'].indexOf(val) > -1;
				},
				default: 'left'
			}
		},
		data: function() {
			return {
			};
		},
		computed: {
			borderStyle: function(){
				return "text-align:" + this.align + ";" + (this.title? "margin-top:15px":"");
			},
			headerStyle: function(){
				return (this.titleAlign != 'center')? this.titleAlign + ":30px" : "";
			}
		},
	    created() {
	    }
	
	});
})
