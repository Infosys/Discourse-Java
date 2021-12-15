import { Injectable } from '@angular/core';
import { Location } from '@angular/common';

import { AuthServerProvider } from 'app/core/auth/auth-session.service';
import { Logout } from './logout.model';
import { GetUserService } from 'app/shared/services/get-user.service';

@Injectable({ providedIn: 'root' })
export class LoginService {
  account: any;
  constructor(private location: Location, private authServerProvider: AuthServerProvider, private getUserService: GetUserService) {}

  login(): void {
    // If you have configured multiple OIDC providers, then, you can update this URL to /login.
    // It will show a Spring Security generated login page with links to configured OIDC providers.
    location.href = `${location.origin}${this.location.prepareExternalUrl('oauth2/authorization/oidc')}`;
  }

  setAccount(data: any): void {
    this.account = data;
  }
  getAccountLocal(): any {
    return this.account;
  }
  logout(): void {
    this.authServerProvider.logout().subscribe((logout: Logout) => {
      let logoutUrl = logout.logoutUrl;
      this.getUserService.currentlyLoggedInUser.next({});
      const redirectUri = `${location.origin}${this.location.prepareExternalUrl('/')}`;

      // if Keycloak, uri has protocol/openid-connect/token
      if (logoutUrl.includes('/protocol')) {
        logoutUrl = logoutUrl + '?redirect_uri=' + redirectUri;
      } else {
        // Okta
        logoutUrl = logoutUrl + '?id_token_hint=' + logout.idToken + '&post_logout_redirect_uri=' + redirectUri;
      }
      window.location.href = logoutUrl;
    });
  }
}
