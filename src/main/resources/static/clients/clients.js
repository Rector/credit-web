angular.module('app').controller('clientsController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:9000';

    $scope.loadClients = function () {
        $http({
            url: contextPath + '/api/v1/clients',
            method: 'GET'
        }).then(function (response) {
            $scope.clientList = response.data;
        });
    };

    $scope.goToAddClientForm = function () {
        $location.path('/add_client');
    };

    $scope.goToUpdateClientForm = function (clientUuid) {
        $location.path('/update_client/' + clientUuid);
    };

    $scope.tryToDeleteClient = function (clientUuid) {
        $http.delete(contextPath + '/api/v1/clients/' + clientUuid)
            .then(function successCallback(response) {
                $scope.loadClients();
            }, function errorCallback(response) {
            });
    };

    $scope.loadClients();
});