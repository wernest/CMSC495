var app = angular.module('swotapp.register', [
  'ngMessages',
    'swotapp.shared'
]);

app.controller('RegisterController', ["$scope", "$http", "$location", "sharedProperties", function($scope, $http, $location, sharedProperties) {

  $scope.signUp = function() {

    $scope.registerForm.$setValidity('server', true);

    if(!$scope.registerForm.$valid){
      return;
    }
    //Get data from Create New account form
    var companyName = $scope.companyName;
    var userName = $scope.username;
    var email = $scope.email;
    var password = $scope.password;

    //Try to create the account
    $http({method: "POST", url: '/api/register',
      data: {'companyName': companyName, 'username': userName, 'email': email, 'password': password}
    }).success(function(data, status, headers, config) {
      sharedProperties.setUsername(userName);
      $scope.registerForm.$setValidity('server', true);
      $location.path("/confirmation");
    }).
        error(function(data, status, headers, config) {
          if(status === 400){
            $scope.serverErrors = data.errors;
            $scope.registerForm.$setValidity('server', false);
          }
        });
  }

}]);