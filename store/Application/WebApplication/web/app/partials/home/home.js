'use strict';
//var link = 'http://127.0.0.1:49822/api/';

angular.module('myApp.home', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
      $routeProvider.when('/', {
        templateUrl: 'partials/home/home.html',
        controller: 'HomeCtrl'
      });
    }])

    .controller('HomeCtrl', ['$http', function($http) {
      var home = this;
      home.link = link;

      home.hots = [];
      home.monos = [];

      $http.get(link + 'products/hot/6').success(function(data) {
        console.log(JSON.stringify(data));
        home.hots = data.products;
      });

      $http.get(link + 'products/fast/6').success(function(data) {
        console.log(JSON.stringify(data));
        home.monos = data.products;
      });
    }]);