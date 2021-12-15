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

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatTabsModule } from '@angular/material/tabs';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTooltipModule } from '@angular/material/tooltip';

// import {MatAutocompleteSelectedEvent, MatAutocomplete} from '@angular/material/autocomplete';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatTabsModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    MatRadioModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatSnackBarModule,
    MatSidenavModule,
    MatButtonModule,
    MatRippleModule,
    MatBottomSheetModule,
    MatExpansionModule,
    MatStepperModule,
    MatTooltipModule,
    // MatAutocompleteSelectedEvent,
    // MatAutocomplete
  ],
  exports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatTabsModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    MatRadioModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatSnackBarModule,
    MatSidenavModule,
    MatButtonModule,
    MatRippleModule,
    MatBottomSheetModule,
    MatExpansionModule,
    MatStepperModule,
    MatTooltipModule,
    // MatAutocompleteSelectedEvent,
    // MatAutocomplete
  ],
  providers: [MatSnackBar],
})
export class AppMaterialModule {}
