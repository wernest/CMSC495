var myApp = angular.module('loginForm', []);

myApp.service('sharedProperties', function() {
  var apiKey = "";
  var apiSecret = "";
  var encodedAuth = "";
  var oauthToken = "";
  var userName = "";

  return {
    getApiKey: function() {
      return apiKey;
    },
    setApiKey: function(value) {
      apiKey = value;
    },
    getApiSecret: function() {
      return apiSecret;
    },
    setApiSecret: function(value) {
      apiSecret = value;
    },
    getEncodedAuth: function() {
      return encodedAuth;
    },
    setEncodedAuth: function(value) {
      encodedAuth = value;
    },
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

    $http({method: "GET", url: '/api/logout',
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

myApp.controller('RestBasicController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

  $scope.myCity = "______";

  $scope.makeRestCall = function() {

    //Get the temperature of specified city from REST endpoint, using Basic Auth
    $http({method: "GET", url: '/api/api/weather/' + $scope.city, headers: {'Authorization': 'Basic ' + sharedProperties.getEncodedAuth()}}).success(function(data, status, headers, config) {
      $scope.temp = data;
      $scope.myCity = $scope.city;

    })
        .error(function(data, status, headers, config) {
          $window.alert("Error");
        });

  }
}]);

myApp.controller('OauthTokenController', ["$scope", "$http", "$window",'sharedProperties', function($scope, $http, $window, sharedProperties) {

  $scope.providers = [{provider:{Id:'London', ScopeName: 'London'}},{provider:{Id:'Berlin', ScopeName: 'Berlin'}},
    {provider:{Id:'San Mateo', ScopeName: 'SanMateo'}}, {provider:{Id:'San Francisco', ScopeName: 'SanFrancisco'}}];
  $scope.ids = {};


  $scope.getOauthToken = function() {

    var myData = $.param({grant_type: "client_credentials"});
    var scopeData = "";

    var first = 0;
    for(var i in $scope.ids) {
      console.log(i + "=" + $scope.ids[i]);
      if($scope.ids[i] == true){
        if(first == 0) {
          scopeData = scopeData + i;
          first = 1;
        }
        else{
          scopeData = scopeData + " " + i;
        }
      }
    }

    myData = $.param({grant_type: "client_credentials", scope: scopeData});

    console.log(myData);

    //Try and get an Oauth Token
    $http({method: "POST", url: '/api/oauthToken',
      headers: {'Authorization': 'Basic ' + sharedProperties.getEncodedAuth(), 'Content-Type': 'application/x-www-form-urlencoded'},
      data : myData})

        .success(function(data, status, headers, config) {
          console.log(data.access_token);
          var oauthToken = data.access_token;
          sharedProperties.setOauthToken(oauthToken);
          $scope.oauthToken = oauthToken;


        }).
        error(function(data, status, headers, config) {
          $window.alert("Error");
        });
  }


}]);


myApp.controller('RestOauthController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

  $scope.myCity = "______";

  $scope.makeRestCall = function() {

    //Get the temperature of specified city from REST endpoint, using Oauth
    $http({method: "GET", url: '/api/api/weather/' + $scope.city, headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
      $scope.temp = data;
      $scope.myCity = $scope.city;

    })
        .error(function(data, status, headers, config) {
          $window.alert("Permission Denied!");
        });

  }
}]);


myApp.controller('ListSampleRows', ["$scope", "$window", "$http", function($scope, $window, $http) {


  //Get the list of sample rows from the api end point
  $http({method: "GET", url: '/api/list'}).success(function(data, status, headers, config) {
    $scope.sampleRows = data;

  })
      .error(function(data, status, headers, config) {
        $window.alert("Permission Denied!");
      });

}]);