'use strict';

angular.module('myApp.faq', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/faq', {
            templateUrl: 'partials/faq/faq.html'
        });
    }]);