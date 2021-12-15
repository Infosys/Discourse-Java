import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MaterialModule } from 'app/material-module';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { AppPluginsModule } from './modules/app-plugins.module';
import { AppBootstrapModule } from './modules/app-bootstrap.module';

@NgModule({
  exports: [
    MaterialModule,
    FormsModule,
    CommonModule,
    NgbModule,
    NgJhipsterModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    AppPluginsModule,
    AppBootstrapModule,
    NgxMaterialTimepickerModule,
  ],
})
export class DiscourseSharedLibsModule {}
