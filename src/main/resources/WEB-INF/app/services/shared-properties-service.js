var app = angular.module('swotapp.shared', ['swotapp.swotresource']);

app.service('sharedProperties', ['SwotResource', '$routeParams', '$q', function(swotResource, $routeParams, $q) {
    var userName = "";
    var swot = "";

    return {
        getUsername: function() {
            return userName;
        },
        setUsername: function(value) {
            userName = value;
        },
        getSwot: function() {
            return $q(function(resolve, reject){
                if($routeParams.id != undefined){
                    if(swot == "") {
                        swotResource.get({id: $routeParams.id}, function (value) {
                            swot = value;
                            resolve(swot);
                        });
                    }else {
                        resolve(swot);
                    }
                }else {
                    reject();
                }
            });
        },
        setSwot: function(value){
            swot = value;
        }
    };
}]);
