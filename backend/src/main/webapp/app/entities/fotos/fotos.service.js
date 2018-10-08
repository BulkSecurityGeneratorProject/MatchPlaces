(function() {
    'use strict';
    angular
        .module('projetoServiceApp')
        .factory('Fotos', Fotos);

    Fotos.$inject = ['$resource'];

    function Fotos ($resource) {
        var resourceUrl =  'api/fotos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
