import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { NotificationStatus } from 'app/shared/model/enumerations/notification-status.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';
import { INotification } from 'app/shared/model/notification/notification.model';
import { ITopicOrPost } from 'app/shared/model/topic-or-post.model';
import { IUser } from 'app/shared/model/user/user.model';
import { AppUtilServices } from 'app/shared/services/app-util.services';
import { GetUserService } from 'app/shared/services/get-user.service';
import { MessagingService } from 'app/shared/services/messaging.service';
import { NotificationService } from 'app/shared/services/notifications.service';
import { SearchService } from 'app/shared/services/search.service';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  isViewSmall!: boolean;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  title = 'push-notification';
  swaggerEnabled?: boolean;
  version: string;
  getUserServiceSubscription: Subscription;
  currentlyLoggedInUser!: IUser;
  showSearchInputInSmallDevices = false;
  searchInputValue = '';
  searchByTitleResults: ITopicOrPost[] = [];
  searchByTagsResults: ITopicOrPost[] = [];
  showSearchResultsBox = false;
  notificationList: INotification[] = [];
  notificationsRead = true;
  notificationStatusEnumValues = {
    seen: NotificationStatus.SEEN,
    created: NotificationStatus.CREATED,
  };

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private getUserService: GetUserService,
    private codeCp: AppUtilServices,
    private messagingService: MessagingService,
    private viewportChangeDetector: ViewportChangeDetector,
    private searchService: SearchService,
    private notificationService: NotificationService,
    private loader: NgxSpinnerService
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
    this.viewportChangeDetector.detectScreenSize({ xs: true, sm: true }).subscribe(isViewSmall => {
      this.isViewSmall = isViewSmall!;
      if (!isViewSmall) {
        this.showSearchInputInSmallDevices = false;
      }
    });
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction!;
      this.swaggerEnabled = profileInfo.swaggerEnabled!;
    });
    this.getUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
      this.currentlyLoggedInUser = user;
    });
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.notificationService.getNotifications().subscribe(
          (res: HttpResponse<INotification[]>) => {
            this.notificationList.push(...res.body!);
          },
          (err: HttpErrorResponse) => {
            console.error(err);
          }
        );
        if (this.currentlyLoggedInUser.notification_subscription) {
          this.enablePushNotifications();
        }
      }
    });
  }

  enablePushNotifications(): void {
    this.messagingService.requestPermission();
    this.messagingService.receiveMessage();
    this.messagingService.currentMessage.subscribe((msg: any) => {
      if (msg) {
        const newNotification: INotification = {
          id: msg?.data?.notificationId,
          title: msg?.notification?.title,
          text: msg?.notification?.body,
          topicId: msg?.data?.topicId,
          notificationType: msg?.data?.notificationType,
          notificationStatus: NotificationStatus.CREATED,
        };
        this.notificationList = [newNotification, ...this.notificationList];
        this.notificationsRead = false;
      }
    });
  }

  togglePushNotificationSubscription(): void {
    this.loader.show();
    this.notificationService.togglePushNotificationsSubscription(!this.currentlyLoggedInUser.notification_subscription!).subscribe(
      () => {
        if (this.currentlyLoggedInUser.notification_subscription!) {
          this.enablePushNotifications();
        }
        this.getUserService.getUser();
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.currentlyLoggedInUser.notification_subscription = !this.currentlyLoggedInUser.notification_subscription;
        this.loader.hide();
      }
    );
  }

  markNotificationsIconAsRead(): void {
    this.notificationsRead = true;
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginService.login();
  }
  clickactive(eventObj: any): void {
    this.codeCp.clickactiveClass(eventObj);
  }
  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }

  enableSearchInputInSmallDevices(): void {
    this.showSearchInputInSmallDevices = true;
  }

  disableSearchInputInSmallDevices(): void {
    this.showSearchInputInSmallDevices = false;
  }

  search(): void {
    if (this.searchInputValue.length > 3) {
      this.searchService.searchTopicByTitle(this.searchInputValue).subscribe(
        (res: HttpResponse<ITopicOrPost[]>) => {
          this.searchByTitleResults = res.body!;
        },
        (err: HttpErrorResponse) => {
          console.error(err);
        }
      );
    } else {
      this.searchByTitleResults = [];
    }
    this.searchService.searchTopicByTags(this.searchInputValue).subscribe(
      (res: HttpResponse<ITopicOrPost[]>) => {
        this.searchByTagsResults = res.body!;
      },
      (err: HttpErrorResponse) => {
        console.error(err);
      }
    );
  }

  navigateToTopic(topicId: string): void {
    this.hideSearchResults();
    this.router.navigateByUrl(`topic/${topicId}`);
  }

  hideSearchResults(): void {
    this.showSearchResultsBox = false;
  }

  showSearchResults(): void {
    this.showSearchResultsBox = true;
  }

  markNotificationAsRead(notificationId: string, topicId: string, notificationType: NotificationType): void {
    this.notificationService.markSingleNotificationAsRead(notificationId).subscribe(
      () => {},
      (err: HttpErrorResponse) => {
        console.error(err);
      }
    );
    if (notificationType === NotificationType.HIDDEN) {
      this.router.navigateByUrl('/user-profile');
    } else {
      this.navigateToTopic(topicId);
    }
  }

  ngOnDestroy(): void {
    this.getUserServiceSubscription.unsubscribe();
  }
}
