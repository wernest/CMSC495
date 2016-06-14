var swotresource = angular.module('swotapp.swotresource', ['swotapp.shared']);

swotresource.factory('SwotResource', [ '$resource', '$http', 'sharedProperties',
    function($resource, $http, sharedProperties) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + sharedProperties.getOauthToken();
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