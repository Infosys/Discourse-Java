import { CategoriesComponent } from './categories/categories.component';
import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';

import { HomeComponent } from './home.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ViewTopicDiscussionComponent } from './view-topic-discussion/view-topic-discussion.component';
import { AllTopicsComponent } from './all-topics/all-topics.component';
import { ViewAnnouncementComponent } from './view-announcement/view-announcement.component';

export const HOME_ROUTE: Route = {
  path: '',
  data: {
    authorities: [],
    breadcrumb: 'Home',
  },
  children: [
    {
      path: '',
      component: HomeComponent,
      data: {
        authorities: [],
        pageTitle: 'Welcome to Infosys Forums!',
      },
      pathMatch: 'full',
    },
    {
      path: 'all-topics',
      component: AllTopicsComponent,
      data: {
        authorities: [Authority.ADMIN, Authority.USER],
        pageTitle: 'Welcome to Infosys Forums!',
      },
      pathMatch: 'full',
      canActivate: [UserRouteAccessService],
    },
    {
      path: 'topic/:id',
      component: ViewTopicDiscussionComponent,
      data: {
        authorities: [Authority.ADMIN, Authority.USER],
        pageTitle: 'Discuss',
        breadcrumb: 'View Discussion',
      },
      pathMatch: 'full',
      canActivate: [UserRouteAccessService],
    },
    {
      path: 'categories',
      component: CategoriesComponent,
      data: {
        authorities: [Authority.ADMIN],
        pageTitle: 'Categories',
        breadcrumb: 'Admin',
      },
      pathMatch: 'full',
      canActivate: [UserRouteAccessService],
    },
    {
      path: 'user-profile',
      component: UserProfileComponent,
      data: {
        authorities: [Authority.ADMIN, Authority.USER],
        pageTitle: 'Profile',
        breadcrumb: 'View Profile',
      },
      pathMatch: 'full',
      canActivate: [UserRouteAccessService],
    },
    {
      path: 'announcement/:id',
      component: ViewAnnouncementComponent,
      data: {
        authorities: [],
        pageTitle: 'Announcements',
        breadcrumb: 'Announcements',
      },
      pathMatch: 'full',
    },
  ],
};
