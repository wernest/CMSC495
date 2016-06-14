var app = angular.module('swotapp.dashboard', [
    'swotapp.swotresource'
]);
//


app.controller('ListSwotReports', ["$scope", "$location", "SwotResource", function($scope, $location, swotResource) {


  //Get the list of sample rows from the api end point
  $scope.reports = swotResource.list(function(response){
      return response;
    }, function(error){
      if(status === 401) {
          $location.path("/");
      }
  });
}]);