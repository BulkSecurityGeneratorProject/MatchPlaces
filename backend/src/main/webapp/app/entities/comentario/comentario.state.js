(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentario', {
            parent: 'entity',
            url: '/comentario?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comentarios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentario/comentarios.html',
                    controller: 'ComentarioController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('comentario-detail', {
            parent: 'entity',
            url: '/comentario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comentario'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentario/comentario-detail.html',
                    controller: 'ComentarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Comentario', function($stateParams, Comentario) {
                    return Comentario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comentario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comentario-detail.edit', {
            parent: 'comentario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-dialog.html',
                    controller: 'ComentarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comentario', function(Comentario) {
                            return Comentario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentario.new', {
            parent: 'comentario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-dialog.html',
                    controller: 'ComentarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                autor: null,
                                url: null,
                                rating: null,
                                text: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: 'comentario' });
                }, function() {
                    $state.go('comentario');
                });
            }]
        })
        .state('comentario.edit', {
            parent: 'comentario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-dialog.html',
                    controller: 'ComentarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comentario', function(Comentario) {
                            return Comentario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: 'comentario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentario.delete', {
            parent: 'comentario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentario/comentario-delete-dialog.html',
                    controller: 'ComentarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comentario', function(Comentario) {
                            return Comentario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentario', null, { reload: 'comentario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
