(function() {
    'use strict';
    angular
        .module('projetoServiceApp')
        .factory('Comentarios', Comentarios);

    Comentarios.$inject = ['$resource'];

    function Comentarios ($resource) {
        var resourceUrl =  'api/comentarios/:id';

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
