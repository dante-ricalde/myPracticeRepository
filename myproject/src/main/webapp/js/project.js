angular.module('project', ['ngRoute','ngResource','ngDialog'])
	.constant('baseURL', '/')	
	
	.service('myAuthService', function ($q) {
		console.log('MyAuthService invoked');
		var that = this;
		return function () {
			console.log('MyAuthService return function invoked');
			that.$authAnonymously = function () {
				return $q.when({'user': 'dantito', 'password': '123'});
			};
			return that;
		}
	})

	.service('fbAuth', function ($q, myAuthService) {
		var auth;
		console.log('fbAuth invoked');
		return function () {
			console.log('fbAuth return function invoked');
			if (auth) return $q.when(auth);
			var authObj = myAuthService();
//			if (authObj) {
//				return $q.when(auth = authObj.$getAuth());
//			}
			var deferred = $q.defer();
			authObj.$authAnonymously().then(function (authData) {
				auth = authData;
				deferred.resolve(authData);
			});
			return deferred.promise;
		}
	})
    .service('projectFactory', function ($resource, baseURL) {
        this.getProjResource = function () {
            return $resource(baseURL+"project/:id", null, {'update':{method:'PUT' }});
        };
    }) 
	// I need to simulate the fbAuth to return a promise with an auth object created by me, to then return the list of projects that i am going to return from 
	// res service Example.java
    .service('Projects', ['$rootScope', '$q', 'fbAuth', '$http', 'ngDialog', 'projectFactory', function ($rootScope, $q, fbAuth, $http, ngDialog, projectFactory) {
    	var self = this;
    	console.log(this);
    	this.fetch = function () {
    		console.log("fetch:" + this);
    		//if (this.projects) return $q.when(this.projects);// we comment this to not save the results in locals
    		return fbAuth().then(function (auth) {
    			console.log(auth);
    			var deferred = $q.defer();
    			var ref = auth.user;
    			$http({
    				  method: 'GET',
    				  url: '/angular/projects'
    				}).then(function successCallback(response) {
    				    // this callback will be called asynchronously
    				    // when the response is available
    					self.projects = response.data;
    					deferred.resolve(self.projects);
    				  }, function errorCallback(response) {
    				    // called asynchronously if an error occurs
    				    // or server returns response with an error status.
    				  });
    			return deferred.promise;
    		});
    	};
        this.get = function (id) {
            var deferred = $q.defer();
            projectFactory.getProjResource().get({id:id}).$promise.then(function (project) {
                if (project.id == undefined && project.id == null) { $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'Project not found.'}); }
                deferred.resolve(project);
            }, function (err) {
                $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'There was an error getting project.'});
                deferred.resolve(err);
            });
            return deferred.promise;
        }
    }])   

    .config(function($routeProvider) {

        var filter = {};
        var filterF = function () { return filter; };

        var resolveProjects = {
            projects: function (Projects) {
                return Projects.fetch();
            }
        };

        var resolveProject = {
            project: function (Projects, $route) {
                return Projects.get($route.current.params.projectId);
            }
        };

        var resolve = {
            project: function () {
                return {};
            }
        };

        $routeProvider
            .when ('/', {
                controller:'ProjectListController as projectList',
                templateUrl:'list.html',
                resolve: $.extend(resolveProjects, {"filter": filterF})
            })
            .when('/edit/:projectId', {
                controller: 'EditProjectController as editProject',
                templateUrl:'detail.html',
                resolve: resolveProject
            })
            .when('/new', {
                controller: 'EditProjectController as editProject',
                templateUrl: 'detail.html',
                resolve: resolve
            })
    })

    .run(['$rootScope', 'ngDialog', '$timeout', function ($rootScope, ngDialog, $timeout) {
        // event handler for managing the display of messages
        $rootScope.$on('popupMessage', function (event, data) {
            $rootScope.message = data;
            ngDialog.open({template: 'template/message_popup.html', className: 'ngdialog-theme-default', scope: $rootScope});
        });
        // event handler for managing the display of the waiting block window
        $rootScope.$on('$routeChangeStart', function (event) {
            console.log('routeChangeStart fired');
            $.blockUI({ css: { 
                border: 'none', 
                padding: '15px',
                backgroundColor: '#000', 
                '-webkit-border-radius': '10px', 
                '-moz-border-radius': '10px', 
                opacity: .5, 
                color: '#fff',
                'font-size': '12px'
                },
                message:'<div class="text-center"><strong><i>Please wait</i></strong><br /><img id="displayBox" width="35px" height="35px" src="image/img_loading.gif" /></div>'
            });
        });
        $rootScope.$on('$routeChangeSuccess', function (event) {
            console.log('routeChangeSuccess fired');
            $timeout($.unblockUI, 200);
        });
    }])

    .controller('ProjectListController', ['$scope', 'projects', 'filter', function($scope, projects, filter) {
        var projectList = this;
        projectList.projects = projects;
        projectList.filter = filter;
        /*
        $('#projects_search').keyup(function () {
            console.log(projectList.filter.search);
            console.log(filter);
        });*/
    }])

    .controller('EditProjectController', ['$scope', '$rootScope', '$location', '$routeParams', 'project', 'projectFactory', 
        function($scope, $rootScope, $location, $routeParams, project, projectFactory) {
        var editProject = this;
        //var projectId = $routeParams.projectId, projectIndex;

        //editProject.projects=projects;
        // indexFor is a method of Firebase, it's replaced by jquery each
        //projectIndex=editProject.projects.$indexFor(projectId);
        /*$.each(editProject.projects, function (index, value) {
            if (value.id === parseInt(projectId)){
                projectIndex=index;
                return false;
            }
        });*/
        //editProject.project=editProject.projects[projectIndex];
        editProject.project=project;
        // We replace this firebase save method by using the angular resource 
        editProject.save = function () {
//        	editProject.projects.$save(editProject.project).then(function(data){
//        		$location.path('/');
//        	});
            // We update a project
            if (!(editProject.project.id == null || editProject.project.id == undefined)) {
                projectFactory.getProjResource().update({id:editProject.project.id},editProject.project).$promise.then(function (data) {
                    if (data.result) {
                        $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'There project has been updated.'});
                    }
                }, function (err) {
                    $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'There was an error updating the project.'});
                });
            } else { // We create a new project
                projectFactory.getProjResource().save(editProject.project).$promise.then(function (project) {
                    if (project.id) {
                        editProject.project.id = project.id;
                        $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'The Project has been created.'});
                        $location.path('/');
                    }
                }, function (err) {
                    $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'There was an error creating the project.'});
                });
            }
        };
    }])