angular.module('app').controller('addCreditOfferFormController', function ($scope, $http, $localStorage, $location, $cookies) {
    const contextPath = 'http://localhost:9000';

    $scope.loadClients = function () {
        $http({
            url: contextPath + '/api/v1/clients',
            method: 'GET'
        }).then(function (response) {
            $scope.clientList = response.data;
        });
    };

    $scope.loadCredits = function () {
            $http({
                url: contextPath + '/api/v1/credits',
                method: 'GET'
            }).then(function (response) {
                $scope.creditList = response.data;
            });
    };

    $scope.tryToAddCreditOffer = function () {
        $http.post(contextPath + '/api/v1/credit_offers', $scope.addCreditOffer)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.addCreditOffer.clientUuid = null;
                    $scope.addCreditOffer.creditUuid = null;
                    $scope.addCreditOffer.creditSum = null;
                    $location.path('/credit_offers/');
                }
            }, function errorCallback(response) {
            });
    };


    $scope.loadClients();
    $scope.loadCredits();
});