var app = angular.module('swotapp.createSwotTabs', []);

app.controller('TabsController', ["$scope", '$location', function($scope, $location) {
    if($location.url().split("strats").length > 1){
        $scope.tabs = [{
            title: 'SO',
            url: 'so.tpl.html'
        }, {
            title: 'WO',
            url: 'wo.tpl.html'
        }, {
            title: 'ST',
            url: 'st.tpl.html'
        }, {
            title: 'WT',
            url: 'wt.tpl.html'
        }];
    }else {
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
    }

    $scope.currentTab = $scope.tabs[0];

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab;
    };

    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab.url;
    };
}]);