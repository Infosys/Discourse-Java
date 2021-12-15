import { createRequestOption } from 'app/shared/util/request-util';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { ICategoryResponse } from '../model/category/category-response.model';

type EntityCategoryResponse = HttpResponse<ICategoryResponse>;

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private categoryResourceUrl = SERVER_API_URL + 'api/categories';

  constructor(private http: HttpClient) {}

  getCategoryList(request?: any): Observable<EntityCategoryResponse> {
    const params = createRequestOption(request);
    return this.http.get<ICategoryResponse>(this.categoryResourceUrl, { params, observe: 'response' });
  }

  createCategory(category: any): Observable<EntityCategoryResponse> {
    return this.http.post<ICategoryResponse>(this.categoryResourceUrl, category, { observe: 'response' });
  }

  updateCategory(id: string, category: any): Observable<EntityCategoryResponse> {
    return this.http.put<ICategoryResponse>(`${this.categoryResourceUrl}/${id}`, category, { observe: 'response' });
  }
}
