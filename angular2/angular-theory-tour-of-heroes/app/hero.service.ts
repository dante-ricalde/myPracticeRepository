import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './hero';
//import { HEROES } from './mock-heroes';

@Injectable()
export class HeroService {

	private heroesUrl = 'api/heroes'; // URL to web api

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http) {}
	/*
	getHeroes(): Promise<Hero[]> { // stub
		return Promise.resolve(HEROES);
	};*/
	getHeroes(): Promise<Hero[]> {
		console.log('hero service getHeroes method invoked...');
		return this.http.get(this.heroesUrl)
				.toPromise()
				.then(response => response.json().data as Hero[])
				.catch(this.handleError);
	}

	private handleError(error: any): Promise<any> {
		console.error('An error ocurred', error); // for demo purposes only
		return Promise.reject(error.message || error);
	}

	getHeroesSlowly(): Promise<Hero[]> {
		return new Promise(resolve => {
			// Simulate server latency with 2 second delay
			setTimeout(() => resolve(this.getHeroes()), 2000);
		});
	};
	/*
	getHero(id: number): Promise<Hero> {
		return this.getHeroes()
				   .then(heroes => heroes.find(hero => hero.id === id));
	}*/
	getHero(id: number): Promise<Hero> {
		console.log('hero service getHero method invoked...');
		const url = `${this.heroesUrl}/${id}`;
		return this.http.get(url)
				.toPromise()
				.then(response => response.json().data as Hero)
				.catch(this.handleError);
	}

	update(hero: Hero): Promise<Hero> {
		console.log('hero service update method invoked...');
		const url = `${this.heroesUrl}/${hero.id}`;
		return this.http
				.put(url, JSON.stringify(hero), {headers: this.headers})
				.toPromise()
				.then(() => hero)
				.catch(this.handleError);
	}

	create(name: string): Promise<Hero> {
		return this.http
			.post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
			.toPromise()
			.then(res => res.json().data)
			.catch(this.handleError);
	}

	delete(id: number): Promise<void> {
		const url = `${this.heroesUrl}/${id}`;
		return this.http.delete(url, {headers: this.headers})
			.toPromise()
			.then(() => null)
			//.then(this.handleError)
			.catch(this.handleError);
	}
}