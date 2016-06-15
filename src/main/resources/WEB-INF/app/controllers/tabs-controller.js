var app = angular.module('swotapp.createSwotTabs', []);

app.controller('TabsController', ["$scope", function($scope) {
    $scope.tabs = [{
        title: 'S',
        url: 'strength.tpl.html'
    }, {
        title: 'W',
        url: 'weakness.tpl.html'
    }, {
        title: 'O',
        url: 'opportunities.tpl.html'
    }, {
        title: 'T',
        url: 'threats.tpl.html'
    }];

    $scope.currentTab = $scope.tabs[0];

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab;
    };

    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab.url;
    };
}]);