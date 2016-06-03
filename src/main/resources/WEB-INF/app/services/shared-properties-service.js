var app = angular.module('swotapp.shared', []);

app.service('sharedProperties', function() {
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
