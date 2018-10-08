(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('FotosDetailController', FotosDetailController);

    FotosDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Fotos', 'Local'];

    function FotosDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Fotos, Local) {
        var vm = this;

        vm.fotos = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('projetoServiceApp:fotosUpdate', function(event, result) {
            vm.fotos = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
