"use strict";
var HeroListComponent = (function () {
    function HeroListComponent(service) {
        this.service = service;
    }
    HeroListComponent.prototype.ngOnInit = function () {
        this.heroes = this.service.getHeroes();
    };
    HeroListComponent.prototype.selectHero = function (hero) { this.selectedHero = hero; };
    return HeroListComponent;
}());
exports.HeroListComponent = HeroListComponent;
//# sourceMappingURL=hero-list.component.js.map