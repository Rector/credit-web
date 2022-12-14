angular.module('app').controller('addCreditFormController', function ($scope, $http, $localStorage, $location, $cookies) {
    const contextPath = 'http://localhost:9000';

    $scope.tryToAddCredit = function () {
        $http.post(contextPath + '/api/v1/credits', $scope.addCredit)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.addCredit.creditLimit = null;
                    $scope.addCredit.interestRate = null;
                    $location.path('/credits/');
                }
            }, function errorCallback(response) {
            });
    };

});