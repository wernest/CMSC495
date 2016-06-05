var app = angular.module('swotapp.createswot', [
    'swotapp.shared'
]);

app.controller('CreateSwotController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {
    
    //Get the list of sample rows from the api end point
    //$http({method: "GET", url: '/api/swot', headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
    //    $scope.reports = data;
    //})
    //    .error(function(data, status, headers, config) {
    //
    //    });
}]);