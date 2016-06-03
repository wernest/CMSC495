var app = angular.module('swotapp.dashboard', [
    'swotapp.shared'
]);
//
//myApp.controller('logoutController', ["$scope", "$window", "$http", function($scope, $window, $http) {
//
//  $scope.makeLogoutCall = function() {
//
//    $http({method: "GET", url: '/api/logout'
//    }).success(function(data, status, headers, config) {
//      $window.location.href = "/app/login-view.html";
//
//    }).
//        error(function(data, status, headers, config) {
//          $window.alert("Log out error");
//        });
//  }
//}]);

app.controller('ListSwotReports', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {


  //Get the list of sample rows from the api end point
  $http({method: "GET", url: '/api/swot', headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
    $scope.reports = data;

  })
      .error(function(data, status, headers, config) {
      });
}]);