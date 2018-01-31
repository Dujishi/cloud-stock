/**
 * Created by Administrator on 2017/5/23.
 */
define(function() {

    var appModule = angular.module('App', []);
    appModule.controller('mainCtrlInfo', function ($scope, $http) {
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
                url: globalConfig.ctx + "/addMenuInfo",
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
    })
})
