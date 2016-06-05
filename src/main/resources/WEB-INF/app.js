var swotapp = angular.module('swotapp', [
    'ngRoute',
    'ngAnimate',
    'ngMessages',
    'ui.bootstrap',
    'swotapp.login',
    'swotapp.register',
    'swotapp.dashboard',
    'swotapp.navbar',
    'swotapp.createswot'
])
    .run(function($templateCache, $http) {
        //This bit of code here will pre-fetch all our templates
        $http.get('/app/views/login-view.html', {cache: $templateCache});
        $http.get('/app/views/dashboard-viewhtml', {cache: $templateCache});
    });

swotapp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: '/app/views/login-view.html',
                resolve: 'LoginController'
            }).
            when('/dashboard', {
                templateUrl: '/app/views/dashboard-view.html',
                controller: 'ListSwotReports'
            }).
            when('/register', {
                templateUrl: '/app/views/register-view.html',
                controller: 'RegisterController'
            }).
            when('/confirmation', {
                templateUrl: '/app/views/confirmation-view.html'
            }).
            when('/dashboard/createSwot', {
                templateUrl: '/app/views/create-swot-view.html',
                controller: 'CreateSwotController'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);