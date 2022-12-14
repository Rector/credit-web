angular.module('app').controller('updateCreditOfferFormController', function ($scope, $http, $localStorage, $location, $routeParams) {
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

    $scope.tryToUpdateCreditOffer = function () {
        $http.put(contextPath + '/api/v1/credit_offers/' + $routeParams.creditOfferUuidParam, $scope.updateCreditOffer)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.updateCreditOffer.clientUuid = null;
                    $scope.updateCreditOffer.creditUuid = null;
                    $scope.updateCreditOffer.creditSum = null;
                    $location.path('/credit_offers/');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.loadClients();
    $scope.loadCredits();
});






