/*
 * Copyright (C) 2019 - 2020 Infosys Limited, Bangalore, India. All Rights Reserved.
 * Version:1.0.0 alpha
 * Except for any free or open source software components embedded in
 * this Infosys proprietary software program ("Program"), this Program is protected
 * by copyright laws, international treaties and other pending or existing
 * intellectual property rights in India, the United States and other countries.
 * Except as expressly permitted, any unauthorized reproduction, storage,
 * transmission in any form or by any means (including without limitation
 * electronic, mechanical, printing, photocopying, recording or otherwise), or any
 * distribution of this Program, or any portion of it, may result in severe civil
 * and criminal penalties, and will be prosecuted to the maximum extent possible
 * under the law.
 */

/* eslint-disable @typescript-eslint/tslint/config */
// import { MomentDateAdapter } from '@angular/material-moment-adapter';
/* eslint-disable no-console */
/* eslint-disable @typescript-eslint/tslint/config */
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng5SliderModule } from 'ng5-slider';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { PerfectScrollbarConfigInterface, PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  minScrollbarLength: 20,
  maxScrollbarLength: 80,
};

@NgModule({
  declarations: [],
  imports: [
    BsDatepickerModule.forRoot(),
    // PopoverModule.forRoot(),
    PerfectScrollbarModule,
    NgbModule,
    CarouselModule,
    NgxMaterialTimepickerModule,
    Ng5SliderModule,
  ],
  exports: [
    PerfectScrollbarModule,
    NgbModule,
    CarouselModule,
    NgxMaterialTimepickerModule,
    BsDatepickerModule,
    // PopoverModule,
    Ng5SliderModule,
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },
  ],
})
export class AppPluginsModule {}
