(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('PessoaDialogController', PessoaDialogController);

    PessoaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Pessoa', 'Profile'];

    function PessoaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Pessoa, Profile) {
        var vm = this;

        vm.pessoa = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profiles = Profile.query({filter: 'pessoa-is-null'});
        $q.all([vm.pessoa.$promise, vm.profiles.$promise]).then(function() {
            if (!vm.pessoa.profile || !vm.pessoa.profile.id) {
                return $q.reject();
            }
            return Profile.get({id : vm.pessoa.profile.id}).$promise;
        }).then(function(profile) {
            vm.profiles.push(profile);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pessoa.id !== null) {
                Pessoa.update(vm.pessoa, onSaveSuccess, onSaveError);
            } else {
                Pessoa.save(vm.pessoa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projetoServiceApp:pessoaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
