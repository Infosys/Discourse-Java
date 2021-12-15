import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnnouncment, Announcment } from 'app/shared/model/announcment.model';
import { AnnouncmentService } from './announcment.service';
import { AnnouncmentComponent } from './announcment.component';
import { AnnouncmentDetailComponent } from './announcment-detail.component';
import { AnnouncmentUpdateComponent } from './announcment-update.component';

@Injectable({ providedIn: 'root' })
export class AnnouncmentResolve implements Resolve<IAnnouncment> {
  constructor(private service: AnnouncmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnnouncment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((announcment: HttpResponse<Announcment>) => {
          if (announcment.body) {
            return of(announcment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Announcment());
  }
}

export const announcmentRoute: Routes = [
  {
    path: '',
    component: AnnouncmentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Announcments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnnouncmentDetailComponent,
    resolve: {
      announcment: AnnouncmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Announcments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnnouncmentUpdateComponent,
    resolve: {
      announcment: AnnouncmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Announcments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnnouncmentUpdateComponent,
    resolve: {
      announcment: AnnouncmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Announcments',
    },
    canActivate: [UserRouteAccessService],
  },
];
