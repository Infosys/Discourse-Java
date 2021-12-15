import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITextClassification } from 'app/shared/model/text-classification.model';

type EntityResponseType = HttpResponse<ITextClassification>;
type EntityArrayResponseType = HttpResponse<ITextClassification[]>;

@Injectable({ providedIn: 'root' })
export class TextClassificationService {
  public resourceUrl = SERVER_API_URL + 'api/text-classifications';

  constructor(protected http: HttpClient) {}

  create(textClassification: ITextClassification): Observable<EntityResponseType> {
    return this.http.post<ITextClassification>(this.resourceUrl, textClassification, { observe: 'response' });
  }

  update(textClassification: ITextClassification): Observable<EntityResponseType> {
    return this.http.put<ITextClassification>(this.resourceUrl, textClassification, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITextClassification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITextClassification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
