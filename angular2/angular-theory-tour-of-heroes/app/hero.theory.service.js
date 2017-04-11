"use strict";
require('rxjs/add/operator/toPromise');
//import { HEROES } from './mock-heroes';
var HeroService = (function () {
    function HeroService() {
        this.heroes = [];
    }
    HeroService.prototype.getHeroes = function () {
        return this.heroes;
    };
    return HeroService;
}());
exports.HeroService = HeroService;
//# sourceMappingURL=hero.theory.service.js.map