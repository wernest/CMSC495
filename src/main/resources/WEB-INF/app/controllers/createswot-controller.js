var app = angular.module('swotapp.createswot', [
    'swotapp.swotresource',
    'swotapp.createSwotTabs'
]);

app.controller('CreateSwotController', ["$scope", 'SwotResource', '$routeParams', '$location',
    function($scope, swotResource, $routeParams, $location) {
        $scope.id = null;
        $scope.factors = [];
        $scope.weaknesses = [];
        $scope.opportunities = [];
        $scope.threats = [];

        if ($routeParams.id != undefined){
            $scope.id = $routeParams.id;
            swotResource.get({id: $scope.id}, function(swot){
                for(var ndx = 0; ndx < swot.factorsList.length; ndx++){
                    if(swot.factorsList[ndx].factorType === 'S'){
                        $scope.factors.push(swot.factorsList[ndx]);
                    }else if(swot.factorsList[ndx].factorType === 'W'){
                        $scope.weaknesses.push(swot.factorsList[ndx]);
                    } else if(swot.factorsList[ndx].factorType === 'O'){
                        $scope.opportunities.push(swot.factorsList[ndx]);
                    } else { // 'T'
                        $scope.threats.push(swot.factorsList[ndx]);
                    }
                }
                $scope.swot = swot;
            });


        } else {
            if ($scope.factors.length === 0) {
                $scope.factors.push({factorType: 'S'});
            }
            if ($scope.weaknesses.length === 0) {
                $scope.weaknesses.push({factorType: 'W'});
            }
            if ($scope.opportunities.length === 0) {
                $scope.opportunities.push({factorType: 'O'});
            }
            if ($scope.threats.length === 0) {
                $scope.threats.push({factorType: 'T'});
            }
        }


        $scope.addRow = function(tabTitle){
            var tempArray = $scope.threats;
            if(tabTitle == "S") {
                tempArray = $scope.factors;
            }else  if(tabTitle == "W"){
                tempArray = $scope.weaknesses
            }else  if(tabTitle == "O"){
                tempArray = $scope.opportunities;
            }
            tempArray.push({factorType: tabTitle});
        };
        
        $scope.close = function() {
        	$location.url("/dashboard");
        };
       
        $scope.saveSwot = function(){
            if($scope.id == undefined) {
                var swotReport = {
                    iFactorScore: 0.0,
                    eFactorScore: 0.0,
                    stratsList: [],
                    factorsList: $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)))
                };

                swotResource.save(swotReport, function(resp, headers){
                    $location.path("/dashboard/strats/" + resp.id);
                }, function(error){
                    if(error.status === 401){
                        $location.path("/");
                    }
                });
            } else{
                $scope.swot.factorsList = $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)));
                $scope.swot.$save(function(resp, header){
                    $location.path("/dashboard/strats/" + $scope.swot.id);
                });
            }

        };
    }]);