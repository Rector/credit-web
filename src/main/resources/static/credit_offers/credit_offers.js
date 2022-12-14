angular.module('app').controller('creditOffersController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:9000';

    $scope.loadCreditOffers = function () {
        $http({
            url: contextPath + '/api/v1/credit_offers',
            method: 'GET'
        }).then(function (response) {
            $scope.creditOfferList = response.data;
        });
    };

    $scope.goToCreditOfferPaymentSchedulesInfo = function (creditOfferUuid) {
        $location.path('/credit_offer_payment_schedules_info/' + creditOfferUuid);
    };

    $scope.goToAddCreditOfferForm = function () {
        $location.path('/add_credit_offer');
    };

    $scope.goToUpdateUpdateCreditOfferForm = function (creditOfferUuid) {
        $location.path('/update_credit_offer/' + creditOfferUuid);
    };

    $scope.tryToDeleteCreditOffer = function (creditOfferUuid) {
        $http.delete(contextPath + '/api/v1/credit_offers/' + creditOfferUuid)
            .then(function successCallback(response) {
                $scope.loadCreditOffers();
            }, function errorCallback(response) {
            });
    };

    $scope.loadCreditOffers();
});