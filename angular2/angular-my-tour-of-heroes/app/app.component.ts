import { Component } from '@angular/core';

// This AppComponent is a Router Component because (The AppComponent is now attached to a router and displaying routed views).
@Component({
	selector: 'my-app',
	template: `
		<h1>{{title}}</h1>
		<nav>
			<a routerLink="/dashboard">Dashboard</a>
			<a routerLink="/heroes">Heroes</a>
			<a routerLink="/detail/12">Narco</a>
		</nav>
		<router-outlet></router-outlet>
	`
})
export class AppComponent {
	title = 'Tour of Heroes';
	
}	