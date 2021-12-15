import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscourseSharedModule } from 'app/shared/shared.module';
import { AnnouncmentComponent } from './announcment.component';
import { AnnouncmentDetailComponent } from './announcment-detail.component';
import { AnnouncmentUpdateComponent } from './announcment-update.component';
import { AnnouncmentDeleteDialogComponent } from './announcment-delete-dialog.component';
import { announcmentRoute } from './announcment.route';

@NgModule({
  imports: [DiscourseSharedModule, RouterModule.forChild(announcmentRoute)],
  declarations: [AnnouncmentComponent, AnnouncmentDetailComponent, AnnouncmentUpdateComponent, AnnouncmentDeleteDialogComponent],
  entryComponents: [AnnouncmentDeleteDialogComponent],
})
export class DiscourseAnnouncmentModule {}
