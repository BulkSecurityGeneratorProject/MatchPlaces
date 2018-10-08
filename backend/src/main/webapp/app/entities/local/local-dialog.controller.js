(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('LocalDialogController', LocalDialogController);

    LocalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Local', 'Profile', 'TipoLocal', 'Fotos', 'Comentarios'];

    function LocalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Local, Profile, TipoLocal, Fotos, Comentarios) {
        var vm = this;

        vm.local = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.profiles = Profile.query({filter: 'local-is-null'});
        $q.all([vm.local.$promise, vm.profiles.$promise]).then(function() {
            if (!vm.local.profile || !vm.local.profile.id) {
                return $q.reject();
            }
            return Profile.get({id : vm.local.profile.id}).$promise;
        }).then(function(profile) {
            vm.profiles.push(profile);
        });
        vm.tipolocals = TipoLocal.query();
        vm.fotos = Fotos.query();
        vm.comentarios = Comentarios.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.local.id !== null) {
                Local.update(vm.local, onSaveSuccess, onSaveError);
            } else {
                Local.save(vm.local, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:localUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
