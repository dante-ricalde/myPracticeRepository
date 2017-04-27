import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';
import { HEROES } from './mock-heroes';
import { Logger } from './logger.service';
import { BackendService } from './backend.service';

@Injectable()
export class HeroService {

	private heroes: Hero[] = [];
	//private heroes: Hero[] = HEROES;

	constructor(
		private backend: BackendService,
		private logger: Logger) { }

	getHeroes() {
		//this.logger.log(`Fetched ${this.heroes.length} heroes.`);

		this.backend.getAll(Hero).then( (heroes: Hero[]) => {
			this.logger.log(`Fetched ${heroes.length} heroes.`);
			this.heroes.push(...heroes); // fill cache
		});
		this.logger.log(`returning ${this.heroes.length} heroes the first time.`);
		return this.heroes;
	}
}