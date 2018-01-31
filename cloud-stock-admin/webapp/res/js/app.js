angular.module('App',['ui.bootstrap','toastr']);

var mouduleApp = angular.module('App');

/**
 * 面包屑指令
 */
mouduleApp.directive('breadcrumb', function(){

    var template = '<ol class="breadcrumb"><li ng-repeat="item in breads" ng-class="item.current?\'active\':\'\'" ng-bind="item.name"></li></ol>';

    return {
        restrict:'AE',
        template:template,
        scope:{},
        link:function(scope){
            var breads = window.parent.parent.getBreadcrumb();
            var tBreads = [];
            for(var i = breads.length-1 ; i >= 0 ; i--){
                if(i==0){
                    breads[i].current = true;
                }
                tBreads.push(breads[i]);
            }
            scope.breads =  tBreads || [];
        }
    }
})

/**
 * http interceptor
 */
var progressTimeBarHandler;
var progressBarHandler = (function(){
    return {
        number:99,
        counter:0,
        progressBar:function(){
            if(progressBarHandler.counter>=progressBarHandler.number){
                window.clearTimeout(progressTimeBarHandler);
                return ;
            }else{
                progressTimeBarHandler = window.setTimeout(progressBarHandler.progressBar,50);
            }
            progressBarHandler.counter++;
            $('#g_progress_inner_bar').css({width:progressBarHandler.counter+"%"});
        },
        show:function(){
            this.hide();
            $(".g-progress-container").removeClass('hidden');
            this.progressBar();
        },
        hide:function(){
            $('#g_progress_inner_bar').css({width:'100%'});

            window.clearTimeout(progressTimeBarHandler);
            $(".g-progress-container").addClass('hidden');
            this.counter = 0;
        }
    }
})();

mouduleApp.factory('httpInterceptor', [ '$q', '$injector',function($q, $injector) {
    var httpRes = {};
    var httpInterceptor = {
        'request' : function(config) {
            var deferred = $q.defer();

            if(config.url.indexOf('.')==-1){
                var key = config.method + '_' + config.url + '_' + config.paramSerializer();
                if(httpRes[key]){
                    return $q.reject('requestRejector');
                }
                progressBarHandler.show();
                httpRes[key] = true;
            }

            return config;
        },
        'response' : function(response) {
            //去掉重复请求标志
            var config = response.config;
            if(config.url.indexOf('.')==-1){
                var key = config.method + '_' + config.url + '_' + config.paramSerializer();
                httpRes[key] = false;
                progressBarHandler.hide();
            }

            if (response && response.errCode == "USER_NO_LOGIN") {
                //这里怎么处理在你，这里跳转的登录页面
                top.location.href =  window.globalConfig.loginPage + top.location.hash;
            }



            return response;
        }
    }
    return httpInterceptor;
}
])
mouduleApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}])

/**
 * 日历配置
 */
mouduleApp.config(['uibDatepickerConfig','uibDatepickerPopupConfig',function(uibDatepickerConfig,uibDatepickerPopupConfig){

    uibDatepickerPopupConfig.datepickerPopup = 'yyyy-MM-dd';
    uibDatepickerPopupConfig.closeText = '关闭';
    uibDatepickerPopupConfig.currentText = '今天';
    uibDatepickerPopupConfig.clearText = '清除';

    uibDatepickerConfig.formatDayTitle = 'yyyy MMMM';

}]);

/**
 * 分页配置
 */
mouduleApp.config(['uibPaginationConfig',function(uibPaginationConfig){

    uibPaginationConfig.firstText = '首页';
    uibPaginationConfig.previousText = '<';
    uibPaginationConfig.nextText = '>';
    uibPaginationConfig.lastText = '尾页';
    uibPaginationConfig.forceEllipses = true;
    uibPaginationConfig.boundaryLinks = true;

}]);

/**
 * toastr 配置
 */
mouduleApp.config(['toastrConfig',function(toastrConfig){
    angular.extend(toastrConfig, {
        positionClass: 'toast-top-center'
    });
}]);

/**
 * angular 语言
 */
angular.module("ngLocale", [], ["$provide", function($provide) {
    var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
    $provide.value("$locale", {
        "DATETIME_FORMATS": {
            "AMPMS": [
                "\u4e0a\u5348",
                "\u4e0b\u5348"
            ],
            "DAY": [
                "\u661f\u671f\u65e5",
                "\u661f\u671f\u4e00",
                "\u661f\u671f\u4e8c",
                "\u661f\u671f\u4e09",
                "\u661f\u671f\u56db",
                "\u661f\u671f\u4e94",
                "\u661f\u671f\u516d"
            ],
            "ERANAMES": [
                "\u516c\u5143\u524d",
                "\u516c\u5143"
            ],
            "ERAS": [
                "\u516c\u5143\u524d",
                "\u516c\u5143"
            ],
            "FIRSTDAYOFWEEK": 6,
            "MONTH": [
                "\u4e00\u6708",
                "\u4e8c\u6708",
                "\u4e09\u6708",
                "\u56db\u6708",
                "\u4e94\u6708",
                "\u516d\u6708",
                "\u4e03\u6708",
                "\u516b\u6708",
                "\u4e5d\u6708",
                "\u5341\u6708",
                "\u5341\u4e00\u6708",
                "\u5341\u4e8c\u6708"
            ],
            "SHORTDAY": [
                "\u5468\u65e5",
                "\u5468\u4e00",
                "\u5468\u4e8c",
                "\u5468\u4e09",
                "\u5468\u56db",
                "\u5468\u4e94",
                "\u5468\u516d"
            ],
            "SHORTMONTH": [
                "1\u6708",
                "2\u6708",
                "3\u6708",
                "4\u6708",
                "5\u6708",
                "6\u6708",
                "7\u6708",
                "8\u6708",
                "9\u6708",
                "10\u6708",
                "11\u6708",
                "12\u6708"
            ],
            "STANDALONEMONTH": [
                "\u4e00\u6708",
                "\u4e8c\u6708",
                "\u4e09\u6708",
                "\u56db\u6708",
                "\u4e94\u6708",
                "\u516d\u6708",
                "\u4e03\u6708",
                "\u516b\u6708",
                "\u4e5d\u6708",
                "\u5341\u6708",
                "\u5341\u4e00\u6708",
                "\u5341\u4e8c\u6708"
            ],
            "WEEKENDRANGE": [
                5,
                6
            ],
            "fullDate": "y\u5e74M\u6708d\u65e5EEEE",
            "longDate": "y\u5e74M\u6708d\u65e5",
            "medium": "y\u5e74M\u6708d\u65e5 ah:mm:ss",
            "mediumDate": "y\u5e74M\u6708d\u65e5",
            "mediumTime": "ah:mm:ss",
            "short": "yy/M/d ah:mm",
            "shortDate": "yy/M/d",
            "shortTime": "ah:mm"
        },
        "NUMBER_FORMATS": {
            "CURRENCY_SYM": "\u00a5",
            "DECIMAL_SEP": ".",
            "GROUP_SEP": ",",
            "PATTERNS": [
                {
                    "gSize": 3,
                    "lgSize": 3,
                    "maxFrac": 3,
                    "minFrac": 0,
                    "minInt": 1,
                    "negPre": "-",
                    "negSuf": "",
                    "posPre": "",
                    "posSuf": ""
                },
                {
                    "gSize": 3,
                    "lgSize": 3,
                    "maxFrac": 2,
                    "minFrac": 2,
                    "minInt": 1,
                    "negPre": "-\u00a4\u00a0",
                    "negSuf": "",
                    "posPre": "\u00a4\u00a0",
                    "posSuf": ""
                }
            ]
        },
        "id": "zh",
        "localeID": "zh",
        "pluralCat": function(n, opt_precision) {  return PLURAL_CATEGORY.OTHER;}
    });
}]);