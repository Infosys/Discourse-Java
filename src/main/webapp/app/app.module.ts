import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DiscourseSharedModule } from 'app/shared/shared.module';
import { DiscourseCoreModule } from 'app/core/core.module';
import { DiscourseAppRoutingModule } from './app-routing.module';
import { DiscourseHomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AvatarModule } from 'ngx-avatar';
import { ResizableModule } from 'angular-resizable-element';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppUtilServices } from './shared/services/app-util.services';
import { CategoryService } from './shared/services/category.service';
import { PostService } from './shared/services/post.service';
import { SearchService } from './shared/services/search.service';
import { AnnouncementService } from './shared/services/announcement.service';
import { UserService } from './shared/services/user.service';
import { MessagingService } from './shared/services/messaging.service';
import { NotificationService } from './shared/services/notifications.service';
import { TopicService } from './shared/services/topic.service';
import { NewTopicPostPrivateMessageService } from './shared/services/new-topic-post-private-message.service';
import { GetCategoriesService } from './shared/services/get-categories.service';
import { GetUserService } from './shared/services/get-user.service';
import { ViewportChangeDetector } from './shared/services/viewport-change-detector';
import { BootstrapBreakpoints } from './shared/custom-viewport-breakpoints';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';

import { environment } from '../environments/environment';
import { ClickOutsideModule } from 'ng-click-outside';

@NgModule({
  imports: [
    BrowserModule,
    DiscourseSharedModule,
    DiscourseCoreModule,
    DiscourseHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DiscourseAppRoutingModule,
    BrowserAnimationsModule,
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebase),
    FormsModule,
    ReactiveFormsModule,
    AvatarModule,
    ResizableModule,
    FlexLayoutModule.withConfig({}, BootstrapBreakpoints),
    ClickOutsideModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
    AppUtilServices,
    CategoryService,
    TopicService,
    MessagingService,
    UserService,
    PostService,
    SearchService,
    NewTopicPostPrivateMessageService,
    GetCategoriesService,
    GetUserService,
    AnnouncementService,
    ViewportChangeDetector,
    NotificationService,
  ],
  bootstrap: [MainComponent],
})
export class DiscourseAppModule {}
