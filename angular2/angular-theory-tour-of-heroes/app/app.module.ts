import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } 	 from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';

// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';

import { Logger } from './logger.service';

import { AppComponent }  from './app.component';
import { HeroDetailComponent } from './hero-detail.component';
import { HeroesComponent } from './heroes.component';
import { BackendService } from './backend.service';
import { HeroService } from './hero.service';
import { DashboardComponent } from './dashboard.component'
import { HeroSearchComponent } from './hero-search.component'
import { HeroListComponent } from './hero-list.component'

import { AppRoutingModule } from './app-routing.module';


@NgModule({
  imports:      [ 
  	BrowserModule,
  	FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
  	AppRoutingModule
  ],
  declarations: [ 
  	AppComponent,
    DashboardComponent,
  	HeroesComponent,
  	HeroDetailComponent,
    HeroSearchComponent,
    HeroListComponent
  ],
  providers: [ BackendService, HeroService, Logger ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
