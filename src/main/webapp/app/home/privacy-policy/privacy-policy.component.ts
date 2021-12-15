import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'app/core/login/login.service';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-privacy-policy',
  templateUrl: './privacy-policy.component.html',
  styleUrls: ['./privacy-policy.component.scss'],
})
export class PrivacyPolicyComponent implements OnDestroy {
  isViewXsOrSmOrMdOrLg = false;
  viewportChangeDetectorSubscriptionForXsSmMdLg: Subscription;
  constructor(private loginService: LoginService, private router: Router, private viewportChangeDetector: ViewportChangeDetector) {
    this.viewportChangeDetectorSubscriptionForXsSmMdLg = this.viewportChangeDetector
      .detectScreenSize({ xs: true, sm: true, md: true, lg: true })
      .subscribe(isViewXsOrSmOrMdOrLg => {
        this.isViewXsOrSmOrMdOrLg = isViewXsOrSmOrMdOrLg!;
      });
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }

  ngOnDestroy(): void {
    this.viewportChangeDetectorSubscriptionForXsSmMdLg.unsubscribe();
  }
}
