(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('FotosDialogController', FotosDialogController);

    FotosDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Fotos', 'Local'];

    function FotosDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Fotos, Local) {
        var vm = this;

        vm.fotos = entity;
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
            if (vm.fotos.id !== null) {
                Fotos.update(vm.fotos, onSaveSuccess, onSaveError);
            } else {
                Fotos.save(vm.fotos, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:fotosUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
