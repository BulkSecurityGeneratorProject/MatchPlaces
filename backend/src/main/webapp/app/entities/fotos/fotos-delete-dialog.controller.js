(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('FotosDeleteController',FotosDeleteController);

    FotosDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fotos'];

    function FotosDeleteController($uibModalInstance, entity, Fotos) {
        var vm = this;

        vm.fotos = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fotos.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
