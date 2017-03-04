angular.module('app', ['components'])

.controller('BeerCounter', function ($scope, $locale, $rootScope) {
	$scope.beers = [0, 1, 2, 3, 4, 5, 6];

	// This init method is not necessary when I use this angular-dynamic-module (https://github.com/lgalfaso/angular-dynamic-locale)
	// because When I set the locale to en-us in the pane the var $scope.beerForms sets properly to English and
	// when I set the locale to sk-sk in the pane the var $scope.beerForms sets propertly to sk
	// taking into account that for each <pane title="Pluralization" there is an instance of this BeerCounter and then 
	// each pane use its respective $scope.beerForms from its respective controller (one controller in en-us and another in sk-sk)
	$scope.init = function(locale) {
		//$scope.setBeerForms(locale);
	};

	$scope.setBeerForms = function (locale) {
		if (locale == 'en-us') {
			$scope.beerForms = {
				0: 'no beers',
				one: '{} beer',
				other: '{} beers'
			};
		} else {
			$scope.beerForms = {
				0: 'žiadne pivo',
				one: '{} pivo',
				few: '{} pivá',
				other: '{} pív'
			};
		}
	};

	$scope.setBeerForms($locale.id);
	
});