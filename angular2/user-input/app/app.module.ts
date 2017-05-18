import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } 	 from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { ClickMeComponent } from './click-me.component';
import { KeyUpComponent_v1, KeyUpComponent_v2 } from './keyup.components';
import { LoopbackComponent } from './loop-back.component';


@NgModule({
  imports:      [ 
  	BrowserModule,
  	FormsModule,
    HttpModule
  ],
  declarations: [ 
  	AppComponent,
    ClickMeComponent,
    KeyUpComponent_v1,
    KeyUpComponent_v2,
    LoopbackComponent
  ],
  providers: [ ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
