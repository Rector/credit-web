angular.module('app').controller('updateCreditFormController', function ($scope, $http, $localStorage, $location, $routeParams) {
    const contextPath = 'http://localhost:9000';

    $scope.tryToUpdateCredit = function () {
        $http.put(contextPath + '/api/v1/credits/' + $routeParams.creditUuidParam, $scope.updateCredit)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.updateCredit.creditLimit = null;
                    $scope.updateCredit.interestRate = null;
                    $location.path('/credits/');
                }
            }, function errorCallback(response) {
            });
    };

});