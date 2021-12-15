import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITextClassification, TextClassification } from 'app/shared/model/text-classification.model';
import { TextClassificationService } from './text-classification.service';
import { TextClassificationComponent } from './text-classification.component';
import { TextClassificationDetailComponent } from './text-classification-detail.component';
import { TextClassificationUpdateComponent } from './text-classification-update.component';

@Injectable({ providedIn: 'root' })
export class TextClassificationResolve implements Resolve<ITextClassification> {
  constructor(private service: TextClassificationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITextClassification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((textClassification: HttpResponse<TextClassification>) => {
          if (textClassification.body) {
            return of(textClassification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TextClassification());
  }
}

export const textClassificationRoute: Routes = [
  {
    path: '',
    component: TextClassificationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TextClassifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TextClassificationDetailComponent,
    resolve: {
      textClassification: TextClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TextClassifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TextClassificationUpdateComponent,
    resolve: {
      textClassification: TextClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TextClassifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TextClassificationUpdateComponent,
    resolve: {
      textClassification: TextClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TextClassifications',
    },
    canActivate: [UserRouteAccessService],
  },
];
