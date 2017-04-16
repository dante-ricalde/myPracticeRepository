"use strict";
require('rxjs/add/operator/toPromise');
var mock_heroes_1 = require('./mock-heroes');
var HeroService = (function () {
    function HeroService() {
        //private heroes: Hero[] = [];
        this.heroes = mock_heroes_1.HEROES;
    }
    HeroService.prototype.getHeroes = function () {
        return this.heroes;
    };
    return HeroService;
}());
exports.HeroService = HeroService;
//# sourceMappingURL=hero.theory.service.js.map