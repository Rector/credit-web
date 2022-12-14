angular.module('app').controller('addClientFormController', function ($scope, $http, $localStorage, $location, $cookies) {
    const contextPath = 'http://localhost:9000';

    $scope.tryToAddClient = function () {
        $http.post(contextPath + '/api/v1/clients', $scope.addClient)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.addClient.fullName = null;
                    $scope.addClient.phoneNumber = null;
                    $scope.addClient.email = null;
                    $scope.addClient.passportNumber = null;
                    $location.path('/clients/');
                }
            }, function errorCallback(response) {
            });
    };

});