import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnnouncment } from 'app/shared/model/announcment.model';

type EntityResponseType = HttpResponse<IAnnouncment>;
type EntityArrayResponseType = HttpResponse<IAnnouncment[]>;

@Injectable({ providedIn: 'root' })
export class AnnouncmentService {
  public resourceUrl = SERVER_API_URL + 'api/announcments';

  constructor(protected http: HttpClient) {}

  create(announcment: IAnnouncment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcment);
    return this.http
      .post<IAnnouncment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(announcment: IAnnouncment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(announcment);
    return this.http
      .put<IAnnouncment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnnouncment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnnouncment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(announcment: IAnnouncment): IAnnouncment {
    const copy: IAnnouncment = Object.assign({}, announcment, {
      deletedAt: announcment.deletedAt && announcment.deletedAt.isValid() ? announcment.deletedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deletedAt = res.body.deletedAt ? moment(res.body.deletedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((announcment: IAnnouncment) => {
        announcment.deletedAt = announcment.deletedAt ? moment(announcment.deletedAt) : undefined;
      });
    }
    return res;
  }
}
