var app = angular.module('swotapp.dashboard', [
    'swotapp.shared'
]);
//


app.controller('ListSwotReports', ["$scope", "$location", "$http", 'sharedProperties', function($scope, $location, $http, sharedProperties) {


  //Get the list of sample rows from the api end point
  $http({method: "GET", url: '/api/swot', headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
    $scope.reports = data;
  })
      .error(function(data, status, headers, config) {
          if(status === 401){
              $location.path("/");
          }
      });
}]);