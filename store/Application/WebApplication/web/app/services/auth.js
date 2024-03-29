'use strict';

var link = 'http://127.0.0.1:49822/api/';

angular.module('myApp')
        .factory("Auth", function ($http, $log, $cookieStore) {
            var user = $cookieStore.get('user') || {};

    return {
        getCurrentUser: function () {
            return user;
        },
        register: function (newuser, success, error) {
            /*
                 $http.post('link + /Customers/', user)
                 $http.get('login.json', user)
             */
            $log.log("Antes register: ");
            $log.log(JSON.stringify(user));

            $http.post('http://localhost:8080/WebApplication/webresources/client/', newuser).success(function (res) {
                $log.log("res - > " + JSON.stringify(res));
                if (res.error)
                    error(res.error);
                else {
                    user = newuser;
                    user['id'] = res.id; //previous res only 
                    $cookieStore.put('user', user);

                    $log.log("Depois register: ");
                    $log.log(JSON.stringify(user));

                    success(res);
                }
            }).error(error);
        },
        edit: function (newuser, success, error) {
            /*
                 $http.put(link + '/Customers/', user)
                 $http.get('login.json', user)
             */
            $log.log("Antes edit: ");
            $log.log("user: " + JSON.stringify(user));
            $log.log("newuser: " + JSON.stringify(newuser));

            $http.put('http://localhost:8080/WebApplication/webresources/client/' + user.id, newuser).success(function (res) {
                if (res.error)
                    error(res.error);
                else {
                    user = newuser;
                    $cookieStore.put('user', user);

                    $log.log("Depois edit: ");
                    $log.log(user);

                    success(res);
                }
            }).error(error);
        },
        login: function (newuser, success, error) {
                    $log.log("->" + JSON.stringify(newuser));
            /*
                 $http.post(link + '/Customers/login', user)*/
            $http.post('http://localhost:8080/WebApplication/webresources/client/login', newuser)
                 
            /*$http.post(link + 'Customers/login', newuser)*/.success(function (res) {
                if (res.error)
                    error(res.error);
                else {
                    user = res;
                    $cookieStore.put('user', res);

                    $log.log("Depois login: ");
                    $log.log(user);

                    success(res);
                }
            }).error(error);
        },
        logout: function () {
            user = {};
            $cookieStore.remove('user');
            /*
                 $http.post('/Customers/logout', user).sucess(function(res) {
                 user = {};
                 $cookieStore.remove('user');
                 success();
                 }).error(error);
             */
        },
        hasUser: function () {
            return typeof user.email !== 'undefined';
        }
    };
});
