'use strict';

angular.module('myApp.editUser', ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/edituser', {
                controller: 'editUserCtrl',
                templateUrl: 'partials/edituser/edituser.html'
            })
    }])
    .controller('editUserCtrl', function ($http, $scope, Auth, $log, $location) {
        $scope.errorMessage = "";
        $scope.newuser = {};

        $scope.differentPasswords = function () {
            return $scope.newuser.password !== $scope.newuser.password2;
        };

        $scope.editUser = function () {
            $("#editFormSubmitButton").blur();

            Auth.edit($scope.newuser,
                function (data) {
                    $scope.newuser = {};
                    $location.path('/history');
                },
                function (error) {
                    $scope.errorMessage = error.message;
                });
        }
    });