import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } 	 from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { HeroFormComponent } from './hero-form.component';


@NgModule({
  imports:      [ 
  	BrowserModule,
  	FormsModule,
    HttpModule
  ],
  declarations: [ 
  	AppComponent,
    HeroFormComponent
  ],
  providers: [ ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
