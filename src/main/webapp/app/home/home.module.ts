import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscourseSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ViewTopicDiscussionComponent } from './view-topic-discussion/view-topic-discussion.component';
import { SafeHtmlPipe } from './safe-html.pipe';
import { FormsModule } from '@angular/forms';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { AngularRichTextEditorComponent } from './angular-rich-text-editor/angular-rich-text-editor.component';
import { MAT_COLOR_FORMATS, NgxMatColorPickerModule, NGX_MAT_COLOR_FORMATS } from '@angular-material-components/color-picker';
import { AvatarModule } from 'ngx-avatar';
import { ResizableModule } from 'angular-resizable-element';
import { CategoriesComponent } from './categories/categories.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { AllTopicsComponent } from './all-topics/all-topics.component';
import { ViewAnnouncementComponent } from './view-announcement/view-announcement.component';
import { PrivacyPolicyComponent } from './privacy-policy/privacy-policy.component';

@NgModule({
  imports: [
    PopoverModule.forRoot(),
    DiscourseSharedModule,
    FormsModule,
    AngularEditorModule,
    AvatarModule,
    NgxMatColorPickerModule,
    ResizableModule,
    RouterModule.forChild([HOME_ROUTE]),
  ],
  providers: [{ provide: MAT_COLOR_FORMATS, useValue: NGX_MAT_COLOR_FORMATS }],
  declarations: [
    HomeComponent,
    ViewTopicDiscussionComponent,
    ViewAnnouncementComponent,
    UserProfileComponent,
    SafeHtmlPipe,
    AngularRichTextEditorComponent,
    CategoriesComponent,
    AllTopicsComponent,
    ViewAnnouncementComponent,
    PrivacyPolicyComponent,
  ],
})
export class DiscourseHomeModule {}
