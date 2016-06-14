var app = angular.module('swotapp.createswot', [
    'swotapp.swotresource',
    'swotapp.createSwotTabs'
]);

app.controller('CreateSwotController', ["$scope", 'SwotResource', function($scope, swotResource) {

    $scope.factors = [{}];
    $scope.weaknesses = [{}];
    $scope.opportunities = [{}];
    $scope.threats = [{}];

    $scope.addRow = function(tabTitle){
        if(tabTitle == "S") {
            $scope.factors.push({});
        }else  if(tabTitle == "W"){
            $scope.weaknesses.push({});
        }else  if(tabTitle == "O"){
            $scope.opportunities.push({});
        }else{
            $scope.threats.push({});
        }
    };

    $scope.saveSwot = function(){
        var swotReport = {
            iFactorScore: 0.0,
            eFactorScore: 0.0,
            internalStrength: 'NULL',
            externalStrength: 'NULL',
            stratsList: [],
            factorsList:
                $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)))
        };

        swotResource.save(swotReport);

    };
}]);