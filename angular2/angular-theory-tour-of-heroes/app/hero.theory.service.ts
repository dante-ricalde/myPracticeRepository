import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';
import { HEROES } from './mock-heroes';

export class HeroService {

	//private heroes: Hero[] = [];
	private heroes: Hero[] = HEROES;

	getHeroes() {
		return this.heroes;
	}
}