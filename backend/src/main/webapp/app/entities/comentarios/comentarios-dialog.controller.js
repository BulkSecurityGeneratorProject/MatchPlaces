(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('ComentariosDialogController', ComentariosDialogController);

    ComentariosDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Comentarios', 'Local'];

    function ComentariosDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Comentarios, Local) {
        var vm = this;

        vm.comentarios = entity;
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
            if (vm.comentarios.id !== null) {
                Comentarios.update(vm.comentarios, onSaveSuccess, onSaveError);
            } else {
                Comentarios.save(vm.comentarios, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:comentariosUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
