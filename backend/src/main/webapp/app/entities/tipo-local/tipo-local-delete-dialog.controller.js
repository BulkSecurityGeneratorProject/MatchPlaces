(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('TipoLocalDeleteController',TipoLocalDeleteController);

    TipoLocalDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoLocal'];

    function TipoLocalDeleteController($uibModalInstance, entity, TipoLocal) {
        var vm = this;

        vm.tipoLocal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoLocal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
