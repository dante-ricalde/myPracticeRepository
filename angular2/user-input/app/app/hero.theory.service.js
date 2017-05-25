"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
require('rxjs/add/operator/toPromise');
var hero_1 = require('./hero');
var logger_service_1 = require('./logger.service');
var backend_service_1 = require('./backend.service');
var HeroService = (function () {
    //private heroes: Hero[] = HEROES;
    function HeroService(backend, logger) {
        this.backend = backend;
        this.logger = logger;
        this.heroes = [];
    }
    HeroService.prototype.getHeroes = function () {
        //this.logger.log(`Fetched ${this.heroes.length} heroes.`);
        var _this = this;
        this.backend.getAll(hero_1.Hero).then(function (heroes) {
            _this.logger.log("Fetched " + heroes.length + " heroes.");
            (_a = _this.heroes).push.apply(_a, heroes); // fill cache
            var _a;
        });
        this.logger.log("returning " + this.heroes.length + " heroes the first time.");
        return this.heroes;
    };
    HeroService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [backend_service_1.BackendService, logger_service_1.Logger])
    ], HeroService);
    return HeroService;
}());
exports.HeroService = HeroService;
//# sourceMappingURL=hero.theory.service.js.map