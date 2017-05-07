// Keep the Input import for now, we'll remove it later.
import { Component, Input, OnInit } from '@angular/core';
//import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import 'rxjs/add/operator/switchMap';

import { Hero } from './hero';
import { HeroService } from './hero.service';

@Component({
    moduleId: module.id,
	selector: 'my-hero-detail',
    templateUrl: './hero-detail.component.html',
    styleUrls: [ 'hero-detail.component.css' ]
})

export class HeroDetailComponent implements OnInit {

    constructor(private heroService: HeroService, private route: ActivatedRoute, private location: Location) { }

	// We enable this @Input because hero-list.component.html has a [hero] property binding that passes 
    // the value of selectedHero from the parent HeroListComponent to the hero property of the child 
    // HeroDetailComponent, otherwise it would'nt be necessary 
    @Input()
	hero: Hero;

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.heroService.getHero(+params['id']))
            .subscribe(hero => this.hero = hero);
    };

    goBack(): void {
        this.location.back();
    }

    save(): void {
        this.heroService.update(this.hero)
            .then(() => this.goBack());
    }
}