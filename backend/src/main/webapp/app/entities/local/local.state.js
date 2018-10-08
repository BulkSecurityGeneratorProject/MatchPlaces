(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('local', {
            parent: 'entity',
            url: '/local?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Locals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/local/locals.html',
                    controller: 'LocalController',
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
        .state('local-detail', {
            parent: 'entity',
            url: '/local/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Local'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/local/local-detail.html',
                    controller: 'LocalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Local', function($stateParams, Local) {
                    return Local.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'local',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('local-detail.edit', {
            parent: 'local-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local/local-dialog.html',
                    controller: 'LocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Local', function(Local) {
                            return Local.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local.new', {
            parent: 'local',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local/local-dialog.html',
                    controller: 'LocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                latitude: null,
                                longitude: null,
                                photo_reference: null,
                                placeId: null,
                                comentario: null,
                                telefone: null,
                                endereco: null,
                                rate: null,
                                website: null,
                                bairro: null,
                                imagem: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('local', null, { reload: 'local' });
                }, function() {
                    $state.go('local');
                });
            }]
        })
        .state('local.edit', {
            parent: 'local',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local/local-dialog.html',
                    controller: 'LocalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Local', function(Local) {
                            return Local.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local', null, { reload: 'local' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local.delete', {
            parent: 'local',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local/local-delete-dialog.html',
                    controller: 'LocalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Local', function(Local) {
                            return Local.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local', null, { reload: 'local' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
