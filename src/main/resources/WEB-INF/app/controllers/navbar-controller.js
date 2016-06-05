var myApp = angular.module('swotapp.navbar', ['ui.bootstrap', 'swotapp.shared']);


myApp.controller('NavBarCtrl', ['$scope', '$route', '$location', '$http', '$log', 'sharedProperties', function($scope, $route, $location, $http, $log, sharedProperties){

    $scope.createNew = function(){
        if($location.path() === "/dashboard/createSwot") {
            return;
        }else{
            $location.path("/dashboard/createSwot");
        }
    };
    $scope.home = function(){
        if($location.path() === "/dashboard") {
            return;
        }else{
            $location.path("/dashboard");
        }
    };

    $scope.isRouteActive = function (route) {
            if(route === $location.path())
                return true;
    };


  $scope.logout = function() {

    $http({method: "GET", url: '/api/logout'
    }).success(function(data, status, headers, config) {
        $location.path("/");
    }).
        error(function(data, status, headers, config) {
            $log.error(data);
        });
  }
}]);