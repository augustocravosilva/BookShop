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
            var filteredBySize = filterBySize(items, params.size);//filter("material", params.material, items);

            return filterByColor(filteredBySize, params.color);
            //var material = filter("material", params.material, items);
            //return filter("colors", params.color, material);
            //return filterByColor(selectedMaterial, params.selectedColor);
        }
    })

    .controller('viewCategoryCtrl', ['$http', '$route', function($http, $route) {
        var view = this;
        view.link = link;
        view.products = [ ];
        view.selectedMaterial = '*';
        view.selectedColor = '*';
        view.selectedSize = '*';
        view.materials = [ ];
        view.sizes = [ ];
        view.categoryHasResults = true;

        view.setMaterial = function(material) {
            view.selectedMaterial = material;
            console.log("New material: " + material);
        };

        view.setColor = function(color) {
            view.selectedColor = color;
        };

        view.getColor = function() {
            return view.selectedColor;
        }

        view.getMaterial = function() {
            return view.selectedMaterial;
        }

        view.setSize = function(size) {
            view.selectedSize = size;
        };

        view.getSize = function() {
            return view.selectedSize;
        };
        




        $http.get('partials/viewProducts/new_list.json').success(function(data) { //TODO: Change this
                view.categoryHasResults = true;
                view.products = data.products;
                view.materials = getMaterials(data.products);
                view.colors = getColors(data.products);
                view.sizes = getSizes(data.products).sort();

                console.log("...");

                if (data.products.length === 0) {
                    view.categoryHasResults = false;
                }
            }
        );

    }]);

function showCategory(category) {
    $('.portfolio-item').hide();
    $(category).slideToggle();
}
/**
 *
 * @param field product field to filter
 * @param filter value of the filter
 * @param items items to filter
 * @returns {Array}
 */
function filter(field, filter, items) {
    var newItems = [];

    for (var i = 0; i < items.length; i++) {
        if (filter == '*') {
            newItems.push(items[i]);
            continue;
        }

        var value = items[i][field];
        if (value.toUpperCase() == filter.toUpperCase())
            newItems.push(items[i]);
    };

    return newItems;
}

function filterByColor(items, color) {

    if (color === "*")
        return items;

    var newItems = [];

    for(var i = 0; i < items.length; i++) {
        var subproducts = items[i].subproducts;
        for(var j = 0; j < subproducts.length; j++) {
            if(subproducts[j].color.toUpperCase() === color.toUpperCase()) {
                newItems.push(items[i]);
                break;
            }
        }
    }
    return newItems;
}

function filterBySize(items, size) {

    if (size === "*")
        return items;

    var newItems = [];

    for(var i = 0; i < items.length; i++) {
        var subproducts = items[i].subproducts;
        for(var j = 0; j < subproducts.length; j++) {
            if(subproducts[j].size === size) {
                newItems.push(items[i]);
                break;
            }
        }
    }
    return newItems;
}

function getMaterials(products) {
    var materials = [];


    for(var i in products) {
        // console.log(products);
        if (materials.indexOf(products[i].material) == -1)
            materials.push(products[i].material);
    }

    return materials;
}

function getColors(items) {
    var colors = [];

    for(var i = 0; i < items.length; i++) {
        var subproducts = items[i].subproducts;
        for(var j = 0; j < subproducts.length; j++) {
            if(colors.indexOf(subproducts[j].color) == -1) {
                colors.push(subproducts[j].color);
            }
        }
    }
    return colors;
}

function getSizes(items) {
    console.log(items);
    var sizes = [];

    for(var i = 0; i < items.length; i++) {
        var subproducts = items[i].subproducts;
        for(var j = 0; j < subproducts.length; j++) {
            if(sizes.indexOf(subproducts[j].size) == -1) {
                sizes.push(subproducts[j].size);
            }
        }
    }
    return sizes;
}