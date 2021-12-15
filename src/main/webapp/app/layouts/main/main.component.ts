import { PrivacyPolicyComponent } from './../../home/privacy-policy/privacy-policy.component';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';
import { GetCategoriesService } from 'app/shared/services/get-categories.service';
import { GetUserService } from 'app/shared/services/get-user.service';
import { UserService } from 'app/shared/services/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit, OnDestroy {
  isViewSmall = false;
  getUserServiceSubscription!: Subscription;
  userIsLoggedIn = false;
  hasUserAcceptedPolicy = false;
  privacyPolicyDialogHasBeenOpened = false;
  viewportChangeDetectorSubscription: Subscription;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private getCategoriesService: GetCategoriesService,
    private getUserService: GetUserService,
    private dialog: MatDialog,
    private userService: UserService,
    private viewportChangeDetector: ViewportChangeDetector
  ) {
    this.viewportChangeDetectorSubscription = this.viewportChangeDetector
      .detectScreenSize({ xs: true, sm: true })
      .subscribe(isViewSmall => (this.isViewSmall = isViewSmall!));
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe();
    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.userIsLoggedIn = true;
        this.getUserService.getUser();
        this.getUserServiceSubscription = this.getUserService.currentlyLoggedInUser.subscribe(user => {
          this.hasUserAcceptedPolicy = user.privacy_accepted!;

          if (!this.hasUserAcceptedPolicy && Object.keys(user).length > 0 && !this.privacyPolicyDialogHasBeenOpened) {
            this.privacyPolicyDialogHasBeenOpened = true;
            const dialogRef = this.dialog.open(PrivacyPolicyComponent, { disableClose: true });

            dialogRef.afterClosed().subscribe(result => {
              if (result === true) {
                this.userService.usersAcceptPolicy().subscribe(
                  () => {
                    this.dialog.closeAll();
                    this.hasUserAcceptedPolicy = true;
                  },
                  (err: HttpErrorResponse) => {
                    console.error(err);
                  }
                );
              }
            });
          }
        });

        this.getCategoriesService.getCategories();
      } else {
        this.userIsLoggedIn = false;
      }
    });
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'Infosys Forums';
    }
    this.titleService.setTitle(pageTitle);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  ngOnDestroy(): void {
    this.getUserServiceSubscription.unsubscribe();
    this.viewportChangeDetectorSubscription.unsubscribe();
  }
}
