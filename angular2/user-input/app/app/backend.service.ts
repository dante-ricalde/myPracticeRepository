import { Injectable, Type } from '@angular/core';

import { Logger } from './logger.service';
import { Hero } from './hero';

import { HEROES } from './mock-heroes';

@Injectable()
export class BackendService {
	constructor(private logger: Logger) {}

	getAll(type: Type<any>): PromiseLike<any[]> {
		if (type === Hero) {
			// TODO: get from the database
			return Promise.resolve<Hero[]>(HEROES);
		}
		let err = new Error('Cannot get object of this type');
		this.logger.error(err);
		throw err;
	}
}