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

    $scope.saveSwot = function(){
        var swotReport = {
            iFactorScore: 0.0,
            eFactorScore: 0.0,
            stratsList: [],
            factorsList:
                $scope.factors.concat($scope.weaknesses.concat($scope.opportunities.concat($scope.threats)))
        };

        swotResource.save(swotReport);

    };
}]);