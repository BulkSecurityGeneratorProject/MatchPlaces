(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('TipoLocalDialogController', TipoLocalDialogController);

    TipoLocalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoLocal', 'Local'];

    function TipoLocalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoLocal, Local) {
        var vm = this;

        vm.tipoLocal = entity;
        vm.clear = clear;
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
            if (vm.tipoLocal.id !== null) {
                TipoLocal.update(vm.tipoLocal, onSaveSuccess, onSaveError);
            } else {
                TipoLocal.save(vm.tipoLocal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:tipoLocalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
