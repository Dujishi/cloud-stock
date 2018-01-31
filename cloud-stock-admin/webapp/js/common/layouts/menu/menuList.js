/**
 * Created by Administrator on 2017/5/19.
 */
define(function() {

    var appModule = angular.module('App', ['ui.bootstrap']);
    appModule.controller('mainCtrlInfo', function ($scope, $http,$modal,$rootScope) {
        var scope = $rootScope.$new();
        scope.breadcrumb = globalConfig.breadcrumb;
        $http({
            method: 'GET',
            url: globalConfig.ctx+"/listMenu"
        }).then(function successCallback(response) {
            $scope.list = response;
        }, function errorCallback(response) {
            // 请求失败执行代码
        });
        //新增非车险订单
        $scope.addMenuInfo = function () {
            var modal = $modal.open({
                templateUrl:globalConfig.ctx+ '/web/common/layouts/menu/addMenuModal.html?data='+new Date(),
                controller: 'addMenuModel',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    scope : function(){
                        return $scope;
                    }
                }
            });
        };
    }).controller('addMenuModel',function ($scope, $http,scope,$modalInstance) {
        var menu = $scope.menu = {
            icon:null,
            code:null,
            title:null,
            url:null
        }
        $scope.submitted = false;
        $scope.submitMenuInfo = function () {
            $scope.submitted = true;
            if ($scope.myForm.$invalid) {
                return;
            }
            $http({
                method: "post",
                url: globalConfig.ctx + "/js/layouts/menu/addMenuInfo",
                contentType : 'application/json',
                data: $scope.menu
            }).success(function (resp) {
                if (resp.success == false) {
                    alert(resp.message);
                } else {
                    alert(resp.data);
                }
            });
        }

        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
    })
})
