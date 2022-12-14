angular.module('app').controller('creditsController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:9000';

    $scope.loadCredits = function () {
            $http({
                url: contextPath + '/api/v1/credits',
                method: 'GET'
            }).then(function (response) {
                $scope.creditList = response.data;
            });
    };

    $scope.goToAddCreditForm = function () {
        $location.path('/add_credit');
    };

    $scope.goToUpdateCreditForm = function (creditUuid) {
        $location.path('/update_credit/' + creditUuid);
    };

    $scope.tryToDeleteCredit = function (creditUuid) {
        $http.delete(contextPath + '/api/v1/credits/' + creditUuid)
            .then(function successCallback(response) {
                $scope.loadCredits();
            }, function errorCallback(response) {
            });
    };

    $scope.loadCredits();
});