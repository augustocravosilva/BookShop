'use strict';

var link = 'new_list.json';

angular.module('myApp.viewProducts', ['ngRoute'])

        .config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/viewProducts', {
                    templateUrl: 'partials/viewProducts/viewProducts.html',
            controller: 'viewCategoryCtrl'
        });
    }])

        .filter('productsFilter', function() {
            return function (items, params) {
                //var filteredBySize = filterBySize(items, params.size);//filter("material", params.material, items);
        
        //return filterByColor(filteredBySize, params.color);
        return filterByPrice(items, params.price);
        //var material = filter("material", params.material, items);
        //return filter("colors", params.color, material);
        //return filterByColor(selectedMaterial, params.selectedColor);
    };
})

        .controller('viewCategoryCtrl', ['$http', '$route', function($http, $route) {
            var view = this;
            view.link = link;
            view.books = [ ];
            view.selectedPriceRange = '*';
            view.categoryHasResults = true;


            view.setPriceRange = function (size) {
                view.selectedPriceRange = size;
            };

            view.getPriceRange = function () {
                return view.selectedPriceRange;
            };
        
            $http.get('http://localhost:8080/WebApplication/webresources/books').success(function(data) { //TODO: Change this
                view.categoryHasResults = true;
                view.books = data.books;

                console.log("...");

                if (data.books.length === 0) {
                    view.categoryHasResults = false;
                }
            });
        
        }]);


function filterByPrice(items, price) {
    if (price === "*")
        return items;
    
    var newItems = [];
    
    
    items.forEach(function(item) {
        switch (price) {
            case 1:
            {
                if(item.price < 20)
                    newItems.push(item);
                break;
            }
            case 2:
            {
                if (item.price < 50)
                    newItems.push(item);
                break;
            }
            case 3:
            {
                if (item.price < 75)
                    newItems.push(item);
                break;
            }
            case 4:
            {
                if (item.price < 100)
                    newItems.push(item);
                break;
            }
    } 
});

return newItems;
}