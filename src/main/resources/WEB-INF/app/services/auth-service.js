var app = angular.module('swotapp.auth', []);

app.service('AuthService', ['$http', function($http) {
    var oauthToken = sessionStorage.getItem("cmsc495_token");

    return {
        getOauthToken: function() {
            return oauthToken;
        },
        setOauthToken: function(value) {
            oauthToken = value;
            sessionStorage.setItem("cmsc495_token", oauthToken);
            $http.defaults.headers.common.Authorization = 'Bearer ' + oauthToken;
        }
    };
}]);
