var app = angular.module('swotapp.createswot', [
    'swotapp.shared',
    'swotapp.createSwotTabs'
]);

app.controller('CreateSwotController', ["$scope", 'sharedProperties', '$location', 'SwotResource',
    function($scope, sharedProperties, $location, swotResource) {
        $scope.id = null;
        $scope.factors = [];
        $scope.weaknesses = [];
        $scope.opportunities = [];
        $scope.threats = [];

        sharedProperties.getSwot().then(function(swot) {
            for (var ndx = 0; ndx < swot.factorsList.length; ndx++) {
                if (swot.factorsList[ndx].factorType === 'S') {
                    $scope.factors.push(swot.factorsList[ndx]);
                } else if (swot.factorsList[ndx].factorType === 'W') {
                    $scope.weaknesses.push(swot.factorsList[ndx]);
                } else if (swot.factorsList[ndx].factorType === 'O') {
                    $scope.opportunities.push(swot.factorsList[ndx]);
                } else { // 'T'
                    $scope.threats.push(swot.factorsList[ndx]);
                }
            }
            $scope.swot = swot;
            $scope.id = swot.id;
        }).finally(function(){
        addRowIfEmpty();
        });



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
                    stratsList: [],
                    factorsList: $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)))
                };

                swotReport.factorsList = cleanEmptyRows(swotReport.factorsList);

                swotResource.save(swotReport, function(resp, headers){
                    sharedProperties.setSwot(resp);
                    $location.path("/dashboard/strats/" + resp.id);
                }, function(error){
                    if(error.status === 401){
                        $location.path("/");
                    }
                });
            } else{
                $scope.swot.factorsList = $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)));
                $scope.swot.factorsList = cleanEmptyRows($scope.swot.factorsList);
                $scope.swot.$save(function(resp, header){
                    $location.path("/dashboard/strats/" + $scope.swot.id);
                });
            }

        };

        function cleanEmptyRows(factors){
            for(var ndx = 0; ndx < factors.length; ndx++){
                if(!factors[ndx].description ||
                    factors[ndx].description === "" ||
                    factors[ndx].description === null){
                    factors.splice(ndx--, 1);
                }
            }
            return factors;
        }

        function addRowIfEmpty(){
            if ($scope.factors.length === 0) {
                $scope.factors.push({factorType: 'S', weight: '0.0', rating: '0.0'});
            }
            if ($scope.weaknesses.length === 0) {
                $scope.weaknesses.push({factorType: 'W', weight: '0.0', rating: '0.0'});
            }
            if ($scope.opportunities.length === 0) {
                $scope.opportunities.push({factorType: 'O', weight: '0.0', rating: '0.0'});
            }
            if ($scope.threats.length === 0) {
                $scope.threats.push({factorType: 'T', weight: '0.0', rating: '0.0'});
            }
        }

        function checkTotals(){
            var ndx = 0;
            var tempStrengths = 0;
            for(ndx = 0; ndx < $scope.strengths.length; ndx++){
                tempStrengths += $scope.strengths.weight;
            }

            var tempWeak = 0;
            for(ndx = 0; ndx < $scope.weaknesses.length; ndx++){
                tempStrengths += $scope.weaknesses.weight;
            }

            var tempOpp = 0;
            for(ndx = 0; ndx < $scope.opportunities.length; ndx++){
                tempStrengths += $scope.opportunities.weight;
            }
            var tempThreats = 0;
            for(ndx = 0; ndx < $scope.threats.length; ndx++){
                tempStrengths += $scope.threats.weight;
            }

            if(tempStrengths + tempThreats == 1 && tempOpp + tempThreats == 1){
                return true;
            }else{
                return false;
            }
        }
    }]);