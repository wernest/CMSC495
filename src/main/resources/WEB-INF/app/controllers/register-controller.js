var app = angular.module('swotapp.register', [
  'ngMessages',
    'swotapp.shared'
]);

app.controller('RegisterController', ["$scope", "$http", "$location", "sharedProperties", function($scope, $http, $location, sharedProperties) {

  $scope.signUp = function() {

    //Get data from Create New account form
    var firstName = $scope.first_name;
    var lastName = $scope.last_name;
    var userName = $scope.username;
    var email = $scope.email;
    var password = $scope.password;

    //Try to create the account
    $http({method: "POST", url: '/api/register',
      data: {'first_name': firstName, 'last_name': lastName, 'username': userName, 'email': email, 'password': password}
    }).success(function(data, status, headers, config) {

      //Clear Create Account fields
      $scope.first_name = "";
      $scope.last_name = "";
      $scope.username = "";
      $scope.email = "";
      $scope.password = "";
      sharedProperties.setUsername(userName);
      $location.path("/confirmation");
    }).
        error(function(data, status, headers, config) {
          $window.alert("Error");
        });
  }

}]);