import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';

import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Subscription } from 'rxjs';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';
import { AnnouncementService } from 'app/shared/services/announcement.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IAnnouncement } from 'app/shared/model/announcment.model';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  isViewXsOrSm = false;
  isViewXsOrSmOrMdOrLg = false;
  overflowScrollOnHtmlContent = false;
  newsAndAnnouncements: IAnnouncement[] = [];
  newsAndAnnouncementsDisplayIndex = 0;
  enableSliding = true;
  viewportChangeDetectorSubscriptionForXsSm: Subscription;
  viewportChangeDetectorSubscriptionForXsSmMdLg: Subscription;
  @ViewChild('overflowDiv') overflowDiv!: any;

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private viewportChangeDetector: ViewportChangeDetector,
    private announcementService: AnnouncementService,
    private router: Router,
    private loader: NgxSpinnerService
  ) {
    this.viewportChangeDetectorSubscriptionForXsSm = this.viewportChangeDetector
      .detectScreenSize({ xs: true, sm: true })
      .subscribe(isViewXsOrSm => {
        this.isViewXsOrSm = isViewXsOrSm!;
      });
    this.viewportChangeDetectorSubscriptionForXsSmMdLg = this.viewportChangeDetector
      .detectScreenSize({ xs: true, sm: true, md: true, lg: true })
      .subscribe(isViewXsOrSmOrMdOrLg => {
        this.isViewXsOrSmOrMdOrLg = isViewXsOrSmOrMdOrLg!;
      });
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.account = account));
    this.loadNewsAndAnnouncements();
    this.rotateNewsAndAnnouncementsDisplayIndex();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginService.login();
  }

  checkOverflow(element: any): boolean {
    if (element.offsetHeight < element.scrollHeight) {
      return true;
    }
    return false;
  }

  enableOverflowScrollOnHtmlContent(): void {
    this.overflowScrollOnHtmlContent = true;
  }

  previousSlide(): void {
    this.overflowDiv?.nativeElement.scrollTo(0, 0);
    this.overflowScrollOnHtmlContent = false;
    this.newsAndAnnouncementsDisplayIndex =
      this.newsAndAnnouncementsDisplayIndex === 0 ? this.newsAndAnnouncements.length - 1 : this.newsAndAnnouncementsDisplayIndex - 1;
  }

  nextSlide(): void {
    this.overflowDiv?.nativeElement.scrollTo(0, 0);
    this.overflowScrollOnHtmlContent = false;
    this.newsAndAnnouncementsDisplayIndex =
      this.newsAndAnnouncementsDisplayIndex === this.newsAndAnnouncements.length - 1 ? 0 : this.newsAndAnnouncementsDisplayIndex + 1;
  }

  rotateNewsAndAnnouncementsDisplayIndex(): void {
    setInterval(() => {
      if (this.newsAndAnnouncements.length > 1 && this.enableSliding) {
        this.nextSlide();
      }
    }, 5000);
  }

  stopSlidingSlides(): void {
    this.enableSliding = false;
  }

  startSlidingSlides(): void {
    this.enableSliding = true;
  }

  loadNewsAndAnnouncements(): void {
    this.loader.show();
    this.announcementService.getAnnouncements().subscribe(
      (res: HttpResponse<IAnnouncement[]>) => {
        this.newsAndAnnouncements = res.body!;
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        console.error(err);
        this.loader.hide();
      }
    );
  }

  navigateToAnnouncement(): void {
    this.router.navigateByUrl(`announcement/${this.newsAndAnnouncements[this.newsAndAnnouncementsDisplayIndex]?.id}`);
  }

  ngOnDestroy(): void {
    this.viewportChangeDetectorSubscriptionForXsSm.unsubscribe();
    this.viewportChangeDetectorSubscriptionForXsSmMdLg.unsubscribe();
  }
}
