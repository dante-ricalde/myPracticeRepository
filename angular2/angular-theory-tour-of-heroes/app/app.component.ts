import { Component } from '@angular/core';

// This AppComponent is a Router Component because (The AppComponent is now attached to a router and displaying routed views).
@Component({
	moduleId: module.id,
	selector: 'my-app',
	template: `
		<h1>{{title}}</h1>
		<nav>
			<a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
			<a routerLink="/heroes" routerLinkActive="active">Heroes</a>
			<a routerLink="/detail/12" routerLinkActive="active">Narco</a>
			<a routerLink="/heroesList" routerLinkActive="active">Heroes List</a>
		</nav>
		<router-outlet></router-outlet>
	`,
	styleUrls: [ './app.component.css' ]
})
export class AppComponent {
	title = 'Tour of Heroes';
	
}	