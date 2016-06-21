var swotresource = angular.module('swotapp.swotresource', ['swotapp.auth']);

swotresource.factory('SwotResource', [ '$resource', '$http', 'AuthService',
    function($resource, $http, auth) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + auth.getOauthToken();
        return $resource('/api/swot/:id',
            {
                id: '@id'
            }, {
                list: {
                    url: "/api/swot/",
                    method: 'GET',
                    isArray: 'true'
                }
            }
        );
    }
]);