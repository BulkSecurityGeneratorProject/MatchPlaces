(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('ComentarioDialogController', ComentarioDialogController);

    ComentarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Comentario', 'Local'];

    function ComentarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Comentario, Local) {
        var vm = this;

        vm.comentario = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.locals = Local.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comentario.id !== null) {
                Comentario.update(vm.comentario, onSaveSuccess, onSaveError);
            } else {
                Comentario.save(vm.comentario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:comentarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
