var myApp = angular.module('loginForm', []);

myApp.service('sharedProperties', function() {
  var oauthToken = "";
  var userName = "";

  return {
    getOauthToken: function() {
      return oauthToken;
    },
    setOauthToken: function(value) {
      oauthToken = value;
    },
    getUsername: function() {
      return userName;
    },
    setUsername: function(value) {
      userName = value;
    }
  };
});

myApp.controller('loginController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

  $scope.submitFunction = function() {

    var username = $scope.username;
    var password = $scope.password;

    //Try to log in to account
    $http({method: "POST", url: '/api/login',
      data: {'username': username, 'password': password}
    }).success(function(data, status, headers, config) {
      $window.location.href = "/app/dashboard.html";
      sharedProperties.setOauthToken(data);

    }).
        error(function(data, status, headers, config) {
          $window.alert("Wrong username/password. Please try again");
        });
  }
}]);

myApp.controller('logoutController', ["$scope", "$window", "$http", function($scope, $window, $http) {

  $scope.makeLogoutCall = function() {

    $http({method: "GET", url: '/api/logout'
    }).success(function(data, status, headers, config) {
      $window.location.href = "/app/login.html";

    }).
        error(function(data, status, headers, config) {
          $window.alert("Log out error");
        });
  }
}]);

myApp.controller('makeAccountController', ["$scope", "$http", "$window", function($scope, $http, $window) {

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
      $window.alert("Account Created! Now, Sign In!");

    }).
        error(function(data, status, headers, config) {
          $window.alert("Error");
        });
  }

}]);

myApp.controller('ListSwotReports', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {


  //Get the list of sample rows from the api end point
  $http({method: "GET", url: '/api/swot', headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
    $scope.reports = data;

  })
      .error(function(data, status, headers, config) {
        var url = "http://" + $window.location.host + "/app/login.html";
        $window.location.href = url;
      });

}]);