import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscourseSharedModule } from 'app/shared/shared.module';
import { TextClassificationComponent } from './text-classification.component';
import { TextClassificationDetailComponent } from './text-classification-detail.component';
import { TextClassificationUpdateComponent } from './text-classification-update.component';
import { TextClassificationDeleteDialogComponent } from './text-classification-delete-dialog.component';
import { textClassificationRoute } from './text-classification.route';

@NgModule({
  imports: [DiscourseSharedModule, RouterModule.forChild(textClassificationRoute)],
  declarations: [
    TextClassificationComponent,
    TextClassificationDetailComponent,
    TextClassificationUpdateComponent,
    TextClassificationDeleteDialogComponent,
  ],
  entryComponents: [TextClassificationDeleteDialogComponent],
})
export class DiscourseTextClassificationModule {}
