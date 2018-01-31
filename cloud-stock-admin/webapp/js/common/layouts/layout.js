define(function() {

	var appModule = angular.module('App', []);
	appModule.controller('mainCtrl', function($scope, $http) {
		//$scope.detailUrl = globalConfig.isIndex? "/" : globalConfig.path;
		var breadcrumb = $scope.breadcrumb;
		$scope.isExistBreadcrumb = false;
		if(globalConfig.breadcrumb){
			$scope.isExistBreadcrumb = true;
			var breadcrumb = $scope.breadcrumb =  globalConfig.breadcrumb;
		}

		window.setBreadcrumb = function (b) {
			$scope.$apply(function(){
				if(b){
					$scope.isExistBreadcrumb = true;
					var breadcrumb = $scope.breadcrumb = b;
				}
			})
		}
		$http({
			method : 'GET',
			url : globalConfig.ctx + '/core/permission/menu'
		}).then(function successCallback(response) {
			var menus = $scope.menus = response.data.data.children;
			$scope.systemName = response.data.data.systemName;
			$scope.nickName = response.data.data.user.nickName;
			$scope.systems = response.data.data.systemDtos;
			// 如果是首页，加载根路径
			//var targetPage = globalConfig.isIndex? "/" : globalConfig.path;
			var targetPage = "";
			if (globalConfig.isIndex) {
				targetPage = "/";
			} else {
				//targetPage = location.href.replace(new RegExp("^http://" + location.host + globalConfig.ctx + "|#$", "g"), "");
				targetPage = globalConfig.path;
				var param = '';
				if(location.search.length > 0){
					param = location.search;
				}
			}
			if (targetPage) {
				var url = globalConfig.ctx+targetPage;
				var opened = openMenusUrl(menus, url, null,param);
				if(!opened){ // 根据 URL 在菜单中找不到对应的模块时，直接打开目标地址
					$scope.openIframe(url);
				}
			}
			// $('.scrollable-sidebar').slimScroll({
			// 	height : '100%',
			// 	size : '0px'
			// });
		}, function errorCallback(response) {
			// 请求失败执行代码
		});

		// $("#content_iframe").attr("src",globalConfig.ctx+'/main?url=web/menu/menuList');
		// 调整大小
		var getResize = function() {
			var headerHeight = $("#top-nav").height();
			var winHeight = $(window).height();
			var pageNavHeight = winHeight - headerHeight;

			return {
				pageNavHeight : pageNavHeight
			}
		}
		$scope.heightStyle = getResize().pageNavHeight;
		// scrollable sidebar

		// Collapsible Sidebar Menu
		$('.sidebar-menu .openable > a').click(function() {

			if (!$('aside').hasClass('sidebar-mini') || Modernizr.mq('(max-width: 991px)')) {
				if ($(this).parent().children('.submenu').is(':hidden')) {
					$(this).parent().siblings().removeClass('open').children('.submenu').slideUp(200);
					$(this).parent().addClass('open').children('.submenu').slideDown(200);
				} else {
					$(this).parent().removeClass('open').children('.submenu').slideUp(200);
				}
			}

			return false;
		});

		$('#sidebarToggleLG').click(function() {
			if ($('.wrapper').hasClass('display-right')) {
				$('.wrapper').removeClass('display-right');
				$('.sidebar-right').removeClass('active');
			} else {
				// $('.nav-header').toggleClass('hide');
				$('.top-nav').toggleClass('sidebar-mini');
				$('aside').toggleClass('sidebar-mini');
				$('footer').toggleClass('sidebar-mini');
				$('.main-container').toggleClass('sidebar-mini');

				$('.main-menu').find('.openable').removeClass('open');
				$('.main-menu').find('.submenu').removeAttr('style');
			}
		});

		var openMenusUrl = $scope.openMenusUrl = function(menus, url, paren,param) {
			for (var i = 0; i < menus.length; i++) {
				if(menus[i].type == 3 && menus[i].isVirtual == 0 && menus[i].path == url){
					openChildrenUrl($scope.menus,paren.path,null,param);
					$scope.open(menus[i],param);
					return true;
				}
				if (menus[i].path == url) {
					if (paren != null) {
						$scope.open(paren,param);
					}
					$scope.open(menus[i],param);
					return true;
				}
				if (menus[i].children && menus[i].children.length > 0) {
					if(openMenusUrl(menus[i].children, url, menus[i],param)){
						return true;
					}
				}
			}
			return false;
		}

		var openChildrenUrl = $scope.openMenusUrl = function(menus, url, paren,param) {
			for (var i = 0; i < menus.length; i++) {
				if (menus[i].path == url) {
					if (paren != null) {
						if (paren.selected == true) {
							paren.selected = false;
						} else {
							setMenuSelected($scope.menus);
							if (paren.children != null) {
								paren.selected = true;
							}
						}
					}
					if ($scope.lastMenu != null) {
						setMenuSelectedLast($scope.menus, $scope.lastMenu);
					}
					menus[i].selected = true;
					return true;
				}
				if (menus[i].children && menus[i].children.length > 0) {
					if(openChildrenUrl(menus[i].children, url, menus[i],param)){
						return true;
					}
				}
			}
			return false;
		}

		var setMenuSelected = function(menus) {
			for (var i = 0; i < menus.length; i++) {
				if(menus[i].selected && menus[i].path != null){
					menus[i].selected = true;
				}else{
					menus[i].selected = false;
				}
				if (menus[i].children && menus[i].children.length > 0) {
					setMenuSelected(menus[i].children);
				}
			}
		}
		$scope.lastMenu = null;

		var setMenuSelectedLast = function(menus, lastMenu) {
			for (var i = 0; i < menus.length; i++) {
				if (menus[i].id == lastMenu.id) {
					menus[i].selected = false;
				}
				if (menus[i].children && menus[i].children.length > 0) {
					setMenuSelectedLast(menus[i].children,lastMenu);
				}
			}
		}

		$scope.open = function(menu,param) {
			if (menu.path == null) {
				if (menu.selected == true) {
					menu.selected = false;
				} else {
					setMenuSelected($scope.menus);
					if (menu.children != null) {
						menu.selected = true;
					}
				}
			} else {
				if ($scope.lastMenu != null) {
					setMenuSelectedLast($scope.menus, $scope.lastMenu);
				}
				menu.selected = true;
				$scope.lastMenu = menu;
				
				var url = menu.path+param;
				$scope.openIframe(url);
			}
		}
		$scope.openIframe = function(url) {
			 if(window.parent.history.replaceState){
		    	window.parent.history.replaceState(null, null, url);
		    } else {
		    	window.location.hash = url;
		    }
		    url += (url.indexOf("?") > 0? "&" : "?") + "s=" + new Date().getTime();
			$("#content_iframe").attr("src", url);
		}

		window.rootOpenUrl = function (path,param) {
			$scope.$apply(function(){
				var url = path + param;
				if(window.parent.history.replaceState){
					window.parent.history.replaceState(null, null, url);
				} else {
					window.location.hash = url;
				}
				url += (url.indexOf("?") > 0? "&" : "?") + "s=" + new Date().getTime();
				$("#content_iframe").attr("src", url);
			})
		}
		
		$scope.logout = function () {
			window.location.href = "/ups/logout";
		}

		$scope.openSystem = function (path) {
			window.location.href = path;
		}

		$scope.breadcrumbJump = function (b) {
			window.location.href = b.path;
		}
	});
})