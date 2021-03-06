angular.module('project', ['ngRoute','ngResource','ngDialog','dndLists'])
	.constant('baseURL', '/')	
	/*.directive('myCustomer', function() {
  return {
    restrict: 'E',
    templateUrl: 'my-customer.html'
  };
})*/
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
                        //$.each(self.projects, function (index, value) { value.index = index; });
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

    .controller('ProjectListController', ['$scope', '$rootScope', '$location', '$route', 'ngDialog', 'projectFactory', 'projects', 'filter', 
        function($scope, $rootScope, $location, $route, ngDialog, projectFactory, projects, filter) {
        var projectList = this;
        projectList.projects = projects;

        projectList.filter = filter;
        /*
        $('#projects_search').keyup(function () {
            console.log(projectList.filter.search);
            console.log(filter);
        });*/
        
        $scope.models = [
          {listName: "A", items: [], dragging: false}//,
          //{listName: "B", items: [], dragging: false}
        ];

        /**
         * dnd-dragging determines what data gets serialized and send to the receiver
         * of the drop. While we usually just send a single object, we send the array
         * of all selected items here.
         */
        $scope.getSelectedItemsIncluding = function(list, item) {
          item.selected = true;
          return list.items.filter(function(item) { return item.selected; });
        };

        /**
         * We set the list into dragging state, meaning the items that are being
         * dragged are hidden. We also use the HTML5 API directly to set a custom
         * image, since otherwise only the one item that the user actually dragged
         * would be shown as drag image.
         */
        $scope.onDragstart = function(list, event) {
           list.dragging = true;
           if (event.dataTransfer.setDragImage) {
             var img = new Image();
             img.src = 'image/ic_content_copy_black_24dp_2x.png';
             event.dataTransfer.setDragImage(img, 0, 0);
           }
        };

        /**
         * In the dnd-drop callback, we now have to handle the data array that we
         * sent above. We handle the insertion into the list ourselves. By returning
         * true, the dnd-list directive won't do the insertion itself.
         */
        $scope.onDrop = function(list, items, index) {
            /*
          angular.forEach(items, function(item) { item.selected = false; });
          var itemsIdsToRemove = $.map(items, function(e) { return e.id; });
          var reducedItems = $.grep(list.items, function(e) { return $.inArray(e.id, itemsIdsToRemove) === -1; });
          list.items = list.items.slice(0, index)
                      .concat(items)
                      .concat(reducedItems.slice(index));
          $scope.projectList.projects = list.items;
          $scope.models[0].items = list.items;
          $.each($scope.models[0].items, function (index, value) { delete value.selected; });
          //console.log($scope.models[0].items);
          */
          // we comment the code above to deactive the drag and drop in the list, the drop would be only possible in
          // the trash icon. We return false to do nothing.
          return false;
        };

        $scope.onDropTrash = function(list, items, index) {
            console.log('onDropTrash invoked');
            projectList.projectsToDelete = items;
            $scope.itemsIdsToRemove = $.map(items, function(e) { return e.id; });
            $scope.models[0].items = $.grep(list.items, function(e) { return $.inArray(e.id, $scope.itemsIdsToRemove) === -1; });
            $scope.message = {title: 'Project Confirmation', message: 'do you want to delete the following projects?'};
            ngDialog.open({template: 'template/confirm_delete_projects.html', className: 'ngdialog-theme-default', scope: $scope});
        };

        $scope.cancel = function() {
            console.log('canceling the deletion of the projects: ' + $scope.itemsIdsToRemove);
            $scope.models[0].items=$scope.projectList.projects;
            ngDialog.close();
        };

        $scope.delete = function() {
            console.log('deleting projects...' + $scope.itemsIdsToRemove);
            projectFactory.getProjResource().delete({ids: $scope.itemsIdsToRemove}).$promise.then(function (data) {
                if (data.result) {
                    ngDialog.close();
                    $route.reload();
                    $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'The Projects has been deleted.'});                    
                } else {
                    ngDialog.close();
                    $route.reload();
                    $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'All or some Projects has not been deleted.'});                    
                }
            }, function (err) {
                $rootScope.$broadcast('popupMessage', {title: 'Project Confirmation', message: 'There was an error deleting the projects.'});
            });
        }

        /**
         * Last but not least, we have to remove the previously dragged items in the
         * dnd-moved callback.
         */
        $scope.onMoved = function(list) {
          list.items = list.items.filter(function(item) { return !item.selected; });
        };

        // Generate the initial model
        angular.forEach($scope.models, function(list) {
          for (var i = 0; i <= projectList.projects.length - 1; ++i) {
              list.items.push(projectList.projects[i]);
          }
        });

        // Model to JSON for demo purpose
        $scope.$watch('models', function(model) {
            $scope.modelAsJson = angular.toJson(model, true);
        }, true);

        $scope.onDragOverHeading = function(index, external, type, callback) {
            if (index === 0) {
                // We disable dragging over the heading
                console.log('dragging over the heading is not allowed');
                return false;    
            }
            return true;;
        }

        $(window).click(function (event) {
            //console.log('clicking on body, event: ', event);
            // When I click in any part other than the project list We deselect all the projects selected from the list,
            // except for the elements which has the class non-deselect-projects
            if (!$(event.target).hasClass('non-deselect-projects')) {
                $('li.ng-scope.selected', 'ul.projects-list').click();    
            }
        });
        
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