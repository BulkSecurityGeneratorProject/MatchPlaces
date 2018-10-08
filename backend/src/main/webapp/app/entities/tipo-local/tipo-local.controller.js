(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('TipoLocalController', TipoLocalController);

    TipoLocalController.$inject = ['$scope', '$state', 'TipoLocal'];

    function TipoLocalController ($scope, $state, TipoLocal) {
        var vm = this;
        
        vm.tipoLocals = [];

        loadAll();

        function loadAll() {
            TipoLocal.query(function(result) {
                vm.tipoLocals = result;
            });
        }
    }
})();
