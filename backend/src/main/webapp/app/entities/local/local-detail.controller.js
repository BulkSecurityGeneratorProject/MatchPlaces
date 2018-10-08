(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('LocalDetailController', LocalDetailController);

    LocalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Local', 'Profile', 'TipoLocal', 'Fotos', 'Comentarios'];

    function LocalDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Local, Profile, TipoLocal, Fotos, Comentarios) {
        var vm = this;

        vm.local = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('projetoServiceApp:localUpdate', function(event, result) {
            vm.local = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
