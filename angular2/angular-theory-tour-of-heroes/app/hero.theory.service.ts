import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';
import { HEROES } from './mock-heroes';
import { Logger } from './logger.service';

@Injectable()
export class HeroService {

	//private heroes: Hero[] = [];
	private heroes: Hero[] = HEROES;

	constructor(private logger: Logger) { }

	getHeroes() {
		this.logger.log(`Fetched ${this.heroes.length} heroes.`);
		return this.heroes;
	}
}