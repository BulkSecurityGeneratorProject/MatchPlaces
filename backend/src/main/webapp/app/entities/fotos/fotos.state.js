(function() {
    'use strict';

    angular
        .module('projetoServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fotos', {
            parent: 'entity',
            url: '/fotos?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fotos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fotos/fotos.html',
                    controller: 'FotosController',
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
        .state('fotos-detail', {
            parent: 'entity',
            url: '/fotos/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fotos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fotos/fotos-detail.html',
                    controller: 'FotosDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fotos', function($stateParams, Fotos) {
                    return Fotos.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fotos',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fotos-detail.edit', {
            parent: 'fotos-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fotos/fotos-dialog.html',
                    controller: 'FotosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fotos', function(Fotos) {
                            return Fotos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fotos.new', {
            parent: 'fotos',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fotos/fotos-dialog.html',
                    controller: 'FotosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                foto: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fotos', null, { reload: 'fotos' });
                }, function() {
                    $state.go('fotos');
                });
            }]
        })
        .state('fotos.edit', {
            parent: 'fotos',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fotos/fotos-dialog.html',
                    controller: 'FotosDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fotos', function(Fotos) {
                            return Fotos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fotos', null, { reload: 'fotos' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fotos.delete', {
            parent: 'fotos',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fotos/fotos-delete-dialog.html',
                    controller: 'FotosDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fotos', function(Fotos) {
                            return Fotos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fotos', null, { reload: 'fotos' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
