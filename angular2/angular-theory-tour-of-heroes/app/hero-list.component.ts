import { Component, OnInit } from '@angular/core';

import { Hero } from './hero';
import { HeroService } from './hero.theory.service';
//import { Logger } from './logger.service';

@Component({
	moduleId: module.id,
	selector: 'hero-list',
	templateUrl: './hero-list.component.html',
	providers: [ HeroService ]
	//providers: [ HeroService, Logger ]
})
export class HeroListComponent implements OnInit {
	heroes: Hero[];
	selectedHero: Hero;

	constructor(private service: HeroService) {}

	ngOnInit() {
		this.heroes = this.service.getHeroes();
	}

	selectHero(hero: Hero) { this.selectedHero = hero; }
}