angular.module('project', ['ngRoute','ngResource'])
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
	// I need to simulate the fbAuth to return a promise with an auth object created by me, to then return the list of projects that i am going to return from 
	// res service Example.java
    .service('Projects', function ($q, fbAuth, $http) {
    	var self = this;
    	console.log(this);
    	this.fetch = function () {
    		console.log("fetch:" + this);
    		if (this.projects) return $q.when(this.projects);
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
    	}
    })
    
    .service('projectFactory', function ($resource, baseURL) {
    	this.getProjResource = function () {
    		return $resource(baseURL+"edit/:id", null, {'update':{method:'PUT' }});
    	};
    })
    

    .config(function($routeProvider) {
        var resolveProjects = {
            projects: function (Projects) {
                return Projects.fetch();
            }
        }

        $routeProvider
            .when ('/', {
                controller:'ProjectListController as projectList',
                templateUrl:'list.html',
                resolve:resolveProjects
            })
            .when('/edit/:projectId', {
                controller: 'EditProjectController as editProject',
                templateUrl:'detail.html',
                resolve:resolveProjects
            })
    })

    .controller('ProjectListController', function(projects) {
        var projectList = this;
        projectList.projects = projects;
    })

    .controller('EditProjectController', function($location, $routeParams, projects, projectFactory) {
        var editProject = this;
        var projectId = $routeParams.projectId, projectIndex;

        editProject.projects=projects;
        // indexFor is a method of Firebase, it's replaced by jquery each
        //projectIndex=editProject.projects.$indexFor(projectId);
        $.each(editProject.projects, function (index, value) {
            if (value.id === parseInt(projectId)){
                projectIndex=index;
                return false;
            }
        });
        editProject.project=editProject.projects[projectIndex];
        // We replace this firebase save method by using the angular resource 
        editProject.save = function () {
//        	editProject.projects.$save(editProject.project).then(function(data){
//        		$location.path('/');
//        	});
        	projectFactory.getProjResource().update({id:editProject.project.id},editProject.project);
        };
    })