(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('ComentariosDetailController', ComentariosDetailController);

    ComentariosDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Comentarios', 'Local'];

    function ComentariosDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Comentarios, Local) {
        var vm = this;

        vm.comentarios = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('projetoServiceApp:comentariosUpdate', function(event, result) {
            vm.comentarios = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
