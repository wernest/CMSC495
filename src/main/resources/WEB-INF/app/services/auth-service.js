var app = angular.module('swotapp.auth', []);

app.service('AuthService', function() {
    var oauthToken = "";

    return {
        getOauthToken: function() {
            return oauthToken;
        },
        setOauthToken: function(value) {
            oauthToken = value;
        }
    };
});
