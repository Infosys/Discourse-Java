import { NgModule } from '@angular/core';
import { DiscourseSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { ToastrModule } from 'ngx-toastr';
import { AvatarModule } from 'ngx-avatar';
import { CategoryComponent } from './category/category.component';
import { LoaderComponent } from './loader/loader.component';

@NgModule({
  imports: [
    DiscourseSharedLibsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
      maxOpened: 1,
      timeOut: 3000,
      progressBar: true,
      autoDismiss: true,
      closeButton: true,
    }),
    AvatarModule,
  ],
  declarations: [AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective, CategoryComponent, LoaderComponent],
  exports: [DiscourseSharedLibsModule, AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective, CategoryComponent, LoaderComponent],
})
export class DiscourseSharedModule {}
