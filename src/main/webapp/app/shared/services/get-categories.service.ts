import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ICategoryResponse } from '../model/category/category-response.model';
import { ICategory } from '../model/category/category.model';
import { CategoryService } from '../services/category.service';

@Injectable({
  providedIn: 'root',
})
export class GetCategoriesService {
  categoriesJson: BehaviorSubject<object> = new BehaviorSubject<object>({});
  categoryList: BehaviorSubject<ICategory[]> = new BehaviorSubject<ICategory[]>([]);

  constructor(private categoryService: CategoryService) {}

  getCategories(): void {
    const requestOption = { size: 50 };
    this.categoryService.getCategoryList(requestOption).subscribe(
      (res: HttpResponse<ICategoryResponse>) => {
        this.categoryList.next(res.body!.categoryResponses!);
        const categoriesJson = {};
        for (let i = 0; i < res.body!.categoryResponses!.length; i++) {
          categoriesJson[res.body!.categoryResponses![i].id!] = res.body!.categoryResponses![i];
        }
        this.categoriesJson.next(categoriesJson);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
      }
    );
  }
}
