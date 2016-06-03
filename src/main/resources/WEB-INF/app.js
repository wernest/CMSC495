var swotapp = angular.module('swotapp', [
    'ngRoute',
    'ngAnimate',
    'ngMessages',
    'ui.bootstrap',
    'swotapp.login',
    'swotapp.register',
    'swotapp.dashboard',
    'swotapp.navbar'
])
    .run(function($templateCache, $http) {
        //This bit of code here will pre-fetch all our templates
        $http.get('/app/views/login-view.html', {cache: $templateCache});
        $http.get('/app/views/register-view.html', {cache: $templateCache});
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
            otherwise({
                redirectTo: '/'
            });
    }]);
//swotapp.config(function($mdThemingProvider, $mdIconProvider) {
//    $mdThemingProvider.theme('default')
//        .accentPalette('green', {
//            'default': '500',
//            'hue-1': '200',
//            'hue-2': '600',
//            'hue-3': '900'
//        });

    //$mdIconProvider.icon('menu', '/assets/material-design-icons/navigation/svg/production/ic_menu_24px.svg', 24);
//});