import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { GeneralRecomendationComponent } from './general-recomendation/general-recomendation/general-recomendation.component';
import { HomepageComponent } from './homepage/homepage.component';
import { PlaylistComponent } from './playlist/playlist.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { trigger } from '@angular/animations';

const routes: Routes = [
  {
    path: '',
    component: HomepageComponent,
  },
  {
    path: '**',
    component: PlaylistComponent,
  },
];
@NgModule({
  declarations: [
    AppComponent,
    GeneralRecomendationComponent,
    HomepageComponent,
    PlaylistComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
  ],
  providers: [PlaylistComponent, HomepageComponent],
  bootstrap: [AppComponent],
})
export class AppModule {}
