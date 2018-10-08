(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentarios', {
            parent: 'entity',
            url: '/comentarios?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comentarios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentarios/comentarios.html',
                    controller: 'ComentariosController',
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
        .state('comentarios-detail', {
            parent: 'entity',
            url: '/comentarios/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comentarios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentarios/comentarios-detail.html',
                    controller: 'ComentariosDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Comentarios', function($stateParams, Comentarios) {
                    return Comentarios.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comentarios',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comentarios-detail.edit', {
            parent: 'comentarios-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentarios/comentarios-dialog.html',
                    controller: 'ComentariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comentarios', function(Comentarios) {
                            return Comentarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentarios.new', {
            parent: 'comentarios',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentarios/comentarios-dialog.html',
                    controller: 'ComentariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                autor: null,
                                url: null,
                                texto: null,
                                rate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comentarios', null, { reload: 'comentarios' });
                }, function() {
                    $state.go('comentarios');
                });
            }]
        })
        .state('comentarios.edit', {
            parent: 'comentarios',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentarios/comentarios-dialog.html',
                    controller: 'ComentariosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comentarios', function(Comentarios) {
                            return Comentarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentarios', null, { reload: 'comentarios' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentarios.delete', {
            parent: 'comentarios',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentarios/comentarios-delete-dialog.html',
                    controller: 'ComentariosDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comentarios', function(Comentarios) {
                            return Comentarios.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentarios', null, { reload: 'comentarios' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
