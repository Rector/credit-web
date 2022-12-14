angular.module('app').controller('addBankCreditController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.loadBank = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam,
            method: 'GET'
        }).then(function (response) {
            $scope.bankInform = response.data;
        });
    };

    $scope.loadCurrentCreditList = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/credits',
            method: 'GET'
        }).then(function (response) {
            $scope.currentCreditList = response.data;
        });
    };

    $scope.goToBankCreditsInfo = function (bankUuid) {
        $location.path('/bank_credits_info/' + bankUuid);
    };

    $scope.tryToAddBankCredit = function (creditUuid) {
        $http.post(contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/credit_bank', creditUuid)
            .then(function successCallback(response) {
                $scope.loadCurrentCreditList();
            }, function errorCallback(response) {
            });
    };

    $scope.loadBank();
    $scope.loadCurrentCreditList();
});