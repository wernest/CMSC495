var app = angular.module('swotapp.factors', [
    'swotapp.shared'
]);

app.controller('FactorsController', ["$scope", 'sharedProperties',
    function($scope, sharedProperties) {
        $scope.id = null;
        $scope.strengths = [];
        $scope.weaknesses = [];
        $scope.opportunities = [];
        $scope.threats = [];
        $scope.internalStatus = "";
        $scope.internalScore = 0.0;
        $scope.externalScore = 0.0;
        $scope.externalStatus = "";

        sharedProperties.getSwot().then(function(swot){
            for (var ndx = 0; ndx < swot.factorsList.length; ndx++) {
                if (swot.factorsList[ndx].factorType === 'S') {
                    $scope.strengths.push(swot.factorsList[ndx]);
                } else if (swot.factorsList[ndx].factorType === 'W') {
                    $scope.weaknesses.push(swot.factorsList[ndx]);
                } else if (swot.factorsList[ndx].factorType === 'O') {
                    $scope.opportunities.push(swot.factorsList[ndx]);
                } else { // 'T'
                    $scope.threats.push(swot.factorsList[ndx]);
                }
            }
            $scope.swot = swot;

            setScores();
        });

        function setScores(){
            var ndx = 0;
            for(ndx = 0; ndx < $scope.strengths.length; ndx++){
                $scope.internalScore += ($scope.strengths[ndx].weight * $scope.strengths[ndx].rating)
            }
            for(ndx = 0; ndx < $scope.weaknesses.length; ndx++){
                $scope.internalScore += ($scope.weaknesses[ndx].weight * $scope.weaknesses[ndx].rating)
            }
            for(ndx = 0; ndx < $scope.opportunities.length; ndx++){
                $scope.externalScore += ($scope.opportunities[ndx].weight * $scope.opportunities[ndx].rating)
            }
            for(ndx = 0; ndx < $scope.threats.length; ndx++){
                $scope.externalScore += ($scope.threats[ndx].weight * $scope.threats[ndx].rating)
            }

            if($scope.externalScore > 2.5){
                $scope.externalStatus = "STRONG";
            }else{
                $scope.externalStatus = "WEAK";
            }

            if($scope.internalScore > 2.5){
                $scope.internalStatus = "STRONG";
            }else{
                $scope.internalStatus = "WEAK";
            }
        }

    }]);