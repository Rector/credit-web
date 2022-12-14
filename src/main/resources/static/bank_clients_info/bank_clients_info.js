angular.module('app').controller('bankClientsInfoController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.loadBank = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam,
            method: 'GET'
        }).then(function (response) {
            $scope.bankInform = response.data;
        });
    };

    $scope.tryToDeleteBankClient = function (clientUuid) {
        $http.delete(contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/client_bank/' + clientUuid)
            .then(function successCallback(response) {
                $scope.loadBank();
            }, function errorCallback(response) {
            });
    };

    $scope.goToAddBankClientForm = function (bankUuid) {
        $location.path('/add_bank_client/' + bankUuid);
    };

    $scope.loadBank();
});