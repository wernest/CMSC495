var myApp = angular.module('swotapp.login', [
    'swotapp.auth'
]);

myApp.controller('LoginController', ["$scope", "$location", "$http", 'AuthService', function($scope, $location, $http, auth) {

    $http.get("/api/login").then(function(response){
        auth.setOauthToken(response.data);
        $location.path("/dashboard");
    }, function(response){
        auth.setOauthToken("");
    });

    $scope.submitFunction = function() {

        var username = $scope.username;
        var password = $scope.password;

        //Try to log in to account
        $http({method: "POST", url: '/api/login',
            data: {'username': username, 'password': password}
        }).success(function(data, status, headers, config) {
            auth.setOauthToken(data);
            $location.path("/dashboard");
        }).
            error(function(data, status, headers, config) {
                auth.setOauthToken("");
            });
    }
}]);