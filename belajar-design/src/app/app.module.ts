import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav/nav-bar/nav-bar.component';
import { ProfileComponent } from './content-profile/profile/profile.component';
import { OverviewPageComponent } from './overview-page/overview-page.component';
import { ProjectComponent } from './project/project.component';
import { SkillsComponent } from './skills/skills.component';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ProfileComponent,
    OverviewPageComponent,
    ProjectComponent,
    SkillsComponent,
    ContactComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {
        path: '',
        component: ProfileComponent,
      },
      {
        path: 'overview',
        component: OverviewPageComponent,
      },
      {
        path: 'project',
        component: ProjectComponent,
      },
      {
        path: 'skills',
        component: SkillsComponent,
      },
      {
        path: 'contact',
        component: ContactComponent,
      },
    ]),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
