var app = angular.module('swotapp.auth', []);

app.service('AuthService', function() {
    var oauthToken = sessionStorage.getItem("cmsc495_token");

    return {
        getOauthToken: function() {
            return oauthToken;
        },
        setOauthToken: function(value) {
            oauthToken = value;
            sessionStorage.setItem("cmsc495_token", value);
        }
    };
});
