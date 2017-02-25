angular.module('app', ['components'])

.controller('BeerCounter', function ($scope, $locale, $rootScope) {
	$scope.beers = [0, 1, 2, 3, 4, 5, 6];

	$scope.init = function(locale) {
		$scope.setBeerForms(locale);
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