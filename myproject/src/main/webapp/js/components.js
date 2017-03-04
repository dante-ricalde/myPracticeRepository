angular.module('components', [ 'tmh.dynamicLocale' ])
	.config(function(tmhDynamicLocaleProvider) {
	    tmhDynamicLocaleProvider.localeLocationPattern('//code.angularjs.org/1.6.2/i18n/angular-locale_{{locale}}.js');
	})
	.directive('tabs', function () {
		return {
			restrict: 'E',
			transclude: true,
			scope: {},
			controller: function($scope, $element) {
				console.log('executing tabs controller ...');
				var panes = $scope.panes = [];

				$scope.select = function(pane) {
					console.log('Selecting pane');
					angular.forEach(panes, function(pane) {
						pane.selected = false;
					});
					pane.selected = true;
				};
				this.addPane = function(pane) {
					console.log('Adding pane ...');
					if (panes.length == 0) $scope.select(pane);
					panes.push(pane);
				};
			},
			template: `
				<div class="tabbable">
					<ul class="nav nav-tabs">
						<li ng-repeat="pane in panes" ng-class="{active:pane.selected}">
							<a href="" ng-click="select(pane)">{{pane.title}}</a>
						</li>
					</ul>
					<div class="tab-content" ng-transclude></div>
				</div>
			`,
			replace: true
		};
	}).
	directive('pane', function($locale, tmhDynamicLocale){
		return {
			require: '^tabs',
			restrict: 'E',
			transclude: true,
			scope: { title: '@', locale: '@' },
			link: function(scope, element, attrs, tabsController) {
				$locale.id=scope.locale;
				tmhDynamicLocale.set(scope.locale);
				console.log('locale param set in pane element: ' + scope.locale);
				tabsController.addPane(scope);
			},
			controller: function($scope, $element) {
				console.log('executing pane controller ...');
			},
			template: `
				<div class="tab-pane" ng-class="{active: selected}" ng-transclude>
				</div>
			`,
			replace: true
		};
	});