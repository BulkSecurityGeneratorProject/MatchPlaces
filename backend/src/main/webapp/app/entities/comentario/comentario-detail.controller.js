(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .controller('ComentarioDetailController', ComentarioDetailController);

    ComentarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Comentario', 'Local'];

    function ComentarioDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Comentario, Local) {
        var vm = this;

        vm.comentario = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('projetoServiceApp:comentarioUpdate', function(event, result) {
            vm.comentario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
