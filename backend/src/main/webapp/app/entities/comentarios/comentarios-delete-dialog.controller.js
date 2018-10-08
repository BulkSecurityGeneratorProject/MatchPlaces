(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('ComentariosDeleteController',ComentariosDeleteController);

    ComentariosDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comentarios'];

    function ComentariosDeleteController($uibModalInstance, entity, Comentarios) {
        var vm = this;

        vm.comentarios = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comentarios.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
