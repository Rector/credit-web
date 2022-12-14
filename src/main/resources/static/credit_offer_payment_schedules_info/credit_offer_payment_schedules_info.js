angular.module('app').controller('creditOfferPaymentSchedulesInfoController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.loadCreditOffer = function () {
        $http({
            url: contextPath + '/api/v1/credit_offers/' + $routeParams.creditOfferUuidParam,
            method: 'GET'
        }).then(function (response) {
            $scope.creditOfferInform = response.data;
        });
    };

    $scope.loadCreditOffer();
});