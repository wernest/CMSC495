var app = angular.module('swotapp.strats', [
    'swotapp.swotresource',
    'swotapp.shared'
]);

app.controller('StratsController', ["$scope", 'SwotResource', '$routeParams', '$location', 'sharedProperties',
    function($scope, swotResource, $routeParams, $location, sharedProperties) {
        $scope.id = null;
        $scope.so = [];
        $scope.wo = [];
        $scope.st = [];
        $scope.wt = [];


        sharedProperties.getSwot().then(function(swot){

            for (var ndx = 0; ndx < swot.stratsList.length; ndx++) {
                if (swot.stratsList[ndx].stratType === 'SO') {
                    $scope.so.push(swot.stratsList[ndx]);
                } else if (swot.stratsList[ndx].stratType === 'WO') {
                    $scope.wo.push(swot.stratsList[ndx]);
                } else if (swot.stratsList[ndx].stratType === 'ST') {
                    $scope.st.push(swot.stratsList[ndx]);
                } else { // 'WT'
                    $scope.wt.push(swot.stratsList[ndx]);
                }
            }
            $scope.swot = swot;
            $scope.id = swot.id;
        }).finally(function(){
            addRowIfEmpty();
        });




        $scope.addRow = function (tabTitle) {
            var tempArray = $scope.wt;
            if (tabTitle == "SO") {
                tempArray = $scope.so;
            } else if (tabTitle == "WO") {
                tempArray = $scope.wo
            } else if (tabTitle == "ST") {
                tempArray = $scope.st;
            }
            tempArray.push({stratType: tabTitle});
        };

        $scope.close = function () {
            $location.url("/dashboard");
        };

        $scope.saveSwot = function () {
            if ($scope.id == undefined) {
                $location.url("/dashboard");
            } else {
                $scope.swot.stratsList = $scope.so.concat($scope.wo.concat($scope.st.concat($scope.wt)));
                $scope.swot.$save(function (resp, header) {
                    $location.path("/dashboard/view/" + $scope.swot.id);
                });
            }

        };

        function addRowIfEmpty(){
            if ($scope.so.length === 0) {
                $scope.so.push({stratType: 'SO'});
            }
            if ($scope.wo.length === 0) {
                $scope.wo.push({stratType: 'WO'});
            }
            if ($scope.st.length === 0) {
                $scope.st.push({stratType: 'ST'});
            }
            if ($scope.wt.length === 0) {
                $scope.wt.push({stratType: 'WT'});
            }
        };
    }]);