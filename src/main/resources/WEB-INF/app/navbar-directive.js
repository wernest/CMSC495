var app = angular.module('swotapp');

app.directive('navBar', function(){
    return {
        restrict: 'E',
        templateUrl: '/app/views/navbar-view.html',
        controller: 'NavBarCtrl'
    }
});