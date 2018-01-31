/**
 */
define(function(require) {
  require("js/core/user/AutoComplete");

  var Main = {
      data() {
          return {
              showPage: false,
              activeIndex: '1',
              userName: "",
              formInline: {
                  user: '',
                  region: ''
              },

              tableData: [{
                  date: '2016-05-02',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1518 弄'
              }, {
                  date: '2016-05-04',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1517 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-03',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1516 弄'
              }],
          };
      },
      methods: {
          onSubmit() {
              console.log('submit!');
          },
          userNameChange(val) {
              console.log("事件值：" + val + "  ---  本地绑定变量值：" + this.userName);
            },
      },
      created() {
          this.showPage = true;
      }
  }
    var App = Vue.extend(Main);
    var app = new App();
    app.$mount('#app');
    
    // 自定义当前作用域内的全局函数
    function funcA(){
    	
    }
    function funcB(){
      
    }
    // 执行 funcA
    funcA();
})
