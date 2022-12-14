(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage', 'ngCookies'])
        .config(config)
        .run(run);
    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/clients', {
                templateUrl: 'clients/clients.html',
                controller: 'clientsController'
            })
            .when('/add_client', {
                templateUrl: 'add_client/add_client_form.html',
                controller: 'addClientFormController'
            })
            .when('/update_client/:clientUuidParam', {
                templateUrl: 'update_client/update_client_form.html',
                controller: 'updateClientFormController'
            })
            .when('/credits', {
                templateUrl: 'credits/credits.html',
                controller: 'creditsController'
            })
            .when('/add_credit', {
                templateUrl: 'add_credit/add_credit_form.html',
                controller: 'addCreditFormController'
            })
            .when('/update_credit/:creditUuidParam', {
                templateUrl: 'update_credit/update_credit_form.html',
                controller: 'updateCreditFormController'
            })
            .when('/banks', {
                templateUrl: 'banks/banks.html',
                controller: 'banksController'
            })
            .when('/bank_clients_info/:bankUuidParam', {
                templateUrl: 'bank_clients_info/bank_clients_info.html',
                controller: 'bankClientsInfoController'
            })
            .when('/add_bank_client/:bankUuidParam', {
                templateUrl: 'add_bank_client/add_bank_client.html',
                controller: 'addBankClientController'
            })
            .when('/bank_credits_info/:bankUuidParam', {
                templateUrl: 'bank_credits_info/bank_credits_info.html',
                controller: 'bankCreditsInfoController'
            })
            .when('/add_bank_credit/:bankUuidParam', {
                templateUrl: 'add_bank_credit/add_bank_credit.html',
                controller: 'addBankCreditController'
            })
            .when('/credit_offers', {
                templateUrl: 'credit_offers/credit_offers.html',
                controller: 'creditOffersController'
            })
            .when('/add_credit_offer', {
                templateUrl: 'add_credit_offer/add_credit_offer_form.html',
                controller: 'addCreditOfferFormController'
            })
            .when('/credit_offer_payment_schedules_info/:creditOfferUuidParam', {
                templateUrl: 'credit_offer_payment_schedules_info/credit_offer_payment_schedules_info.html',
                controller: 'creditOfferPaymentSchedulesInfoController'
            })
            .when('/update_credit_offer/:creditOfferUuidParam', {
                templateUrl: 'update_credit_offer/update_credit_offer_form.html',
                controller: 'updateCreditOfferFormController'
            })
            .otherwise({
                redirectTo: '/'
            });
    };

    function run($rootScope, $http, $localStorage) {
      };
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location, $cookies) {
});