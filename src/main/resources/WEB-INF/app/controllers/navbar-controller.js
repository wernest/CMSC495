var myApp = angular.module('swotapp.navbar', [
    'ui.bootstrap',
    'swotapp.shared',
    'swotapp.auth']);


myApp.controller('NavBarCtrl', ['$scope', '$route', '$location', '$http', 'AuthService',
    function($scope, $route, $location, $http, auth){

    $scope.createNew = function(){
        if($location.path() === "/dashboard/swot") {
            return;
        }else{
            $location.path("/dashboard/swot");
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
            auth.setOauthToken("");
        }).
            error(function(data, status, headers, config) {
                $location.path("/");
                auth.setOauthToken("");

            });
    }
}]);