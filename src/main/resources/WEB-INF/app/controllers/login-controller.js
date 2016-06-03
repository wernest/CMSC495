var myApp = angular.module('swotapp.login', [
    'swotapp.shared'
]);

myApp.controller('LoginController', ["$scope", "$location", "$http", 'sharedProperties', function($scope, $location, $http, sharedProperties) {

    $scope.submitFunction = function() {

        var username = $scope.username;
        var password = $scope.password;

        //Try to log in to account
        $http({method: "POST", url: '/api/login',
            data: {'username': username, 'password': password}
        }).success(function(data, status, headers, config) {
            $location.path("/dashboard");
            sharedProperties.setOauthToken(data);

        }).
            error(function(data, status, headers, config) {

            });
    }
}]);