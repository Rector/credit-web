angular.module('app').controller('addBankClientController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.loadBank = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam,
            method: 'GET'
        }).then(function (response) {
            $scope.bankInform = response.data;
        });
    };

    $scope.goToBankClientsInfo = function (bankUuid) {
        $location.path('/bank_clients_info/' + bankUuid);
    };

    $scope.loadCurrentClientList = function () {
        $http({
            url: contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/clients',
            method: 'GET'
        }).then(function (response) {
            $scope.currentClientList = response.data;
        });
    };

    $scope.tryToAddBankClient = function (clientUuid) {
        $http.post(contextPath + '/api/v1/banks/' + $routeParams.bankUuidParam + '/client_bank', clientUuid)
            .then(function successCallback(response) {
                $scope.loadCurrentClientList();
            }, function errorCallback(response) {
            });
    };

    $scope.loadBank();
    $scope.loadCurrentClientList();
});