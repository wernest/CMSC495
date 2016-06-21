var app = angular.module('swotapp.register', [
  'ngMessages',
    'swotapp.shared'
]);

app.controller('RegisterController', ["$scope", "$http", "$location", "sharedProperties", function($scope, $http, $location, sharedProperties) {

  $scope.signUp = function() {

    //Get data from Create New account form
    var companyName = $scope.companyName;
    var userName = $scope.username;
    var email = $scope.email;
    var password = $scope.password;
    var password2 = $scope.password2;

    //Try to create the account
    $http({method: "POST", url: '/api/register',
      data: {'companyName': companyName, 'username': userName, 'email': email, 'password': password}
    }).success(function(data, status, headers, config) {
      sharedProperties.setUsername(userName);
      $location.path("/confirmation");
    }).
        error(function(data, status, headers, config) {
        });
  }

}]);