'use strict';

function capitaliseFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

angular.module('myApp.viewProduct', ['ngRoute'])
        .config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/products/:productID', {
                    templateUrl: 'partials/viewProduct/single-product.html',
            controller: 'viewProductCtrl'
        });
        }])
        .controller('viewProductCtrl', ['$http', '$scope', 'ngCart' ,function($http, $scope, $ngCart) {
            var view = this;
            $http.get('partials/viewProduct/1.json').success(function(data) {
                //$http.get('http://127.0.0.1:49822/api/products/' + $routeParams['productID']).success(function(data) {
                console.log("SLB");
                var google = data.google.items[0];
                $scope.image = google.volumeInfo.imageLinks.thumbnail;
                view.isbn = google.volumeInfo.industryIdentifiers[0].identifier;    
                view.stock = data.bookshop.stock;
                view.title = google.volumeInfo.title;
                view.description = google.volumeInfo.description;
                view.price = google.saleInfo.retailPrice.amount;
                view.authors = "";
                
                for (var i = 0; i < google.volumeInfo.authors.length; i++) {
                    if(i > 0)
                        view.authors += ", ";
                    
                    view.authors += google.volumeInfo.authors[i];
                    console.log("author: " + google.volumeInfo.authors[i]);
                }
                
                view.publisher = google.volumeInfo.publisher;
                view.language = google.volumeInfo.language;
                view.publishedDate = google.volumeInfo.publishedDate;
                view.categories = google.volumeInfo.categories;
                view.rating = google.volumeInfo.ratingsCount;
                console.log(view.categories);
               
                /*$scope.product = data.product;
                $scope.current = {};
                $scope.current.mainId = $scope.product.id;*/



                /****set iva and shipping ***/
                $ngCart.setTax(0);
                $ngCart.setShipping(0);
                
            });
            
            



        /*$scope.buildStockString = function(stock_shops) {
            var string = "Online: " + $scope.current["stock"] + "<br>";
            for(var i = 0; i < stock_shops.length; i++) {
                string += stock_shops[i] + "<br>";
            }
            $scope.stockString =  string;
        };

        $scope.updateSelected = function(obj) {
            if(obj !=  $scope.master_select) {
                $scope.updateStock(obj);
                return;
            }

            $scope.filter_update(obj);
        };

        $scope.filter_update = function(obj) {
            var selected_object = null;


            for(var i = 0; i < $scope.keys.length; i++) {
                if($scope.keys[i] != $scope.master_select) {
                    $scope.filter[$scope.keys[i]] = [];
                }
            }

            //console.log($scope.filter);

            for(var i = 0; i < $scope.product.subproducts.length; i++) {
                if ($scope.current[obj] == $scope.product.subproducts[i][obj]) {
                    selected_object = $scope.product.subproducts[i];
                    //console.log(selected_object);
                    for (var key in selected_object) {
                        //console.log(key);
                        if(key != $scope.master_select && $scope.filter[key].indexOf(selected_object[key]) == -1)
                            $scope.filter[key].push(selected_object[key]);
                    }
                }
            }

            console.log("Filter:");
            console.log($scope.filter);

            for(key in $scope.select_options) {
                if(key != obj) {
                    $scope.select_options[key] = $scope.filter[key];
                    $scope.current[key] = $scope.filter[key][0];
                }
            }
            $scope.current["stock"] =  $scope.filter["stock"][0];
            $scope.current["id"] =  $scope.filter["id"][0];
            $scope.current["stock_shops"] =  $scope.filter["stock_shops"][0];
            console.log("Current:");
            console.log($scope.current);

            $scope.buildStockString($scope.current["stock_shops"]);
        };

        $scope.updateStock = function(obj) {
            for(var i = 0; i < $scope.product.subproducts.length; i++) {
                var tmp = jQuery.extend(true, {}, $scope.product.subproducts[i]);
                delete tmp['id'];
                delete tmp['stock'];
                delete tmp['mainId'];
                delete tmp['stock_shops'];

                var tmp2 = jQuery.extend(true, {}, $scope.current);
                delete tmp2['id'];
                delete tmp2['stock'];
                delete tmp2['mainId'];
                delete tmp2['stock_shops'];

                //console.log(tmp);
                //console.log($scope.product.subproducts[i]["stock"]);
                //console.log(tmp2);
                //console.log(tmp == tmp2);
                if(angular.equals(tmp, tmp2)) {
                    //console.log("FOUND - Stock = " + $scope.product.subproducts[i]["id"]);
                    $scope.current["stock"] = $scope.product.subproducts[i]["stock"];
                    $scope.current["id"] = $scope.product.subproducts[i]["id"];
                    $scope.current["stock_shops"] = $scope.product.subproducts[i]["stock_shops"];
                }
            }
            console.log("Current: ");
            console.log($scope.current);
        };

        $scope.updateImage = function(obj) {
            if(obj < $scope.product.image_links.length) {
                $scope.selected_image = $scope.product.image_links[obj];
                //$('body').scrollTo('.item_image');
            }
        };*/
    }]);
