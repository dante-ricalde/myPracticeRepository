import { OnInit } from '@angular/core';

import { Hero } from './hero';
import { HeroService } from './hero.theory.service';

export class HeroListComponent implements OnInit {
	heroes: Hero[];
	selectedHero: Hero;

	constructor(private service: HeroService) {}

	ngOnInit() {
		this.heroes = this.service.getHeroes();
	}

	selectHero(hero: Hero) { this.selectedHero = hero; }
}