var swotApp = angular.module('swotapp.viewreport', ['swotapp.shared']);

swotApp.controller('SwotController', ["$scope", 'sharedProperties', '$routeParams', '$location',
    function($scope, shared, $routeParams, $location){
        $scope.strengths = [];
        $scope.weaknesses = [];
        $scope.opportunities = [];
        $scope.threats = [];
        $scope.so = [];
        $scope.wo = [];
        $scope.st = [];
        $scope.wt = [];
        shared.getSwot().then(function(swotReport){
            $scope.report = swotReport;
            populateFactors(swotReport.factorsList);
            populateStrats(swotReport.stratsList);
            var tempDate = new Date(swotReport.creationDate);
            $scope.creationDate = "Created on " + tempDate.toLocaleDateString() + " at " + tempDate.toLocaleTimeString();
        }, function(error){
            console.log("error occured " + error);
        });

        function populateFactors(factors){
            for(var ndx = 0; ndx < factors.length; ndx++){
                if(factors[ndx].factorType === 'S'){
                    $scope.strengths.push(factors[ndx]);
                }else if(factors[ndx].factorType === 'W'){
                    $scope.weaknesses.push(factors[ndx]);
                }else if(factors[ndx].factorType === 'O'){
                    $scope.opportunities.push(factors[ndx]);
                }else { //T
                    $scope.threats.push(factors[ndx]);
                }
            }
        }

        function populateStrats(strats){
            for(var ndx = 0; ndx < strats.length; ndx++){
                if(strats[ndx].stratType === 'SO'){
                    $scope.so.push(strats[ndx]);
                }else if(strats[ndx].stratType === 'WO'){
                    $scope.wo.push(strats[ndx]);
                }else if(strats[ndx].stratType === 'ST'){
                    $scope.st.push(strats[ndx]);
                }else {
                    $scope.wt.push(strats[ndx]);
                }
            }
        }
    }]);