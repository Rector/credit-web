angular.module('app').controller('updateClientFormController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.tryToUpdateClient = function () {
        $http.put(contextPath + '/api/v1/clients/' + $routeParams.clientUuidParam, $scope.updateClient)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.updateClient.fullName = null;
                    $scope.updateClient.phoneNumber = null;
                    $scope.updateClient.email = null;
                    $scope.updateClient.passportNumber = null;
                    $location.path('/clients/');
                }
            }, function errorCallback(response) {
            });
    };

});