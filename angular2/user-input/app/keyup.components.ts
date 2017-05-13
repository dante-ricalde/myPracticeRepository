import { Component } from '@angular/core';
@Component({
	selector: 'key-up1',
	template: `
		<input (keyup)="onkey($event)">
		<p>{{values}}</p>
	`
})
export class KeyUpComponent_v1 {
	values = '';
	/*
	onkey(event: any) { // without type info
		this.values += event.target.value + ' | ';

	}*/
	onkey(event: KeyboardEvent) { // with type info
		this.values += (<HTMLInputElement>event.target).value + ' | ';
	}
}