(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('TipoLocalDetailController', TipoLocalDetailController);

    TipoLocalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoLocal', 'Local'];

    function TipoLocalDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoLocal, Local) {
        var vm = this;

        vm.tipoLocal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projetoServiceApp:tipoLocalUpdate', function(event, result) {
            vm.tipoLocal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
