import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Hero } from './hero';
import { HEROES } from './mock-heroes';

@Injectable()
export class HeroService {

	private heroesUrl = 'api/heroes'; // URL to web api

	constructor(private http: Http) {}

	/*
	getHeroes(): Promise<Hero[]> { // stub
		return Promise.resolve(HEROES);
	};*/
	getHeroes(): Promise<Hero[]> {
		return this.http.get(this.heroesUrl)
				.toPromise()
				.then(response => response.json().data as Hero[])
				.catch()
	}

	getHeroesSlowly(): Promise<Hero[]> {
		return new Promise(resolve => {
			// Simulate server latency with 2 second delay
			setTimeout(() => resolve(this.getHeroes()), 2000);
		});
	};
	getHero(id: number): Promise<Hero> {
		return this.getHeroes()
				   .then(heroes => heroes.find(hero => hero.id === id));
	}
}