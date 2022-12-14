angular.module('app').controller('bankCreditsInfoController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.loadBank = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam,
            method: 'GET'
        }).then(function (response) {
            $scope.bankInform = response.data;
        });
    };

    $scope.tryToDeleteBankCredit = function (creditUuid) {
        $http.delete(contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/credit_bank/' + creditUuid)
            .then(function successCallback(response) {
                $scope.loadBank();
            }, function errorCallback(response) {
            });
    };

    $scope.goToAddBankCreditForm = function (bankUuid) {
        $location.path('/add_bank_credit/' + bankUuid);
    };

    $scope.loadBank();
});