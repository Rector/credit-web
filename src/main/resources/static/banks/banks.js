angular.module('app').controller('banksController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:9000';

    $scope.loadBanks = function () {
        $http({
            url: contextPath + '/api/v1/banks',
            method: 'GET'
        }).then(function (response) {
            $scope.bankList = response.data;
        });
    };

    $scope.tryToAddBank = function () {
        $http.post(contextPath + '/api/v1/banks')
            .then(function successCallback(response) {
                $scope.loadBanks();
            }, function errorCallback(response) {
            });
    };

    $scope.goToBankClientsInfo = function (bankUuid) {
        $location.path('/bank_clients_info/' + bankUuid);
    };

    $scope.goToBankCreditsInfo = function (bankUuid) {
        $location.path('/bank_credits_info/' + bankUuid);
    };

    $scope.tryToDeleteBank = function (bankUuid) {
        $http.delete(contextPath + '/api/v1/banks/' + bankUuid)
            .then(function successCallback(response) {
                $scope.loadBanks();
            }, function errorCallback(response) {
            });
    };

    $scope.loadBanks();
});