(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-local', {
            parent: 'entity',
            url: '/tipo-local',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoLocals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-local/tipo-locals.html',
                    controller: 'TipoLocalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tipo-local-detail', {
            parent: 'entity',
            url: '/tipo-local/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoLocal'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-local/tipo-local-detail.html',
                    controller: 'TipoLocalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TipoLocal', function($stateParams, TipoLocal) {
                    return TipoLocal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-local',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-local-detail.edit', {
            parent: 'tipo-local-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-local/tipo-local-dialog.html',
                    controller: 'TipoLocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoLocal', function(TipoLocal) {
                            return TipoLocal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-local.new', {
            parent: 'tipo-local',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-local/tipo-local-dialog.html',
                    controller: 'TipoLocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-local', null, { reload: 'tipo-local' });
                }, function() {
                    $state.go('tipo-local');
                });
            }]
        })
        .state('tipo-local.edit', {
            parent: 'tipo-local',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-local/tipo-local-dialog.html',
                    controller: 'TipoLocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoLocal', function(TipoLocal) {
                            return TipoLocal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-local', null, { reload: 'tipo-local' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-local.delete', {
            parent: 'tipo-local',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-local/tipo-local-delete-dialog.html',
                    controller: 'TipoLocalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoLocal', function(TipoLocal) {
                            return TipoLocal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-local', null, { reload: 'tipo-local' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
