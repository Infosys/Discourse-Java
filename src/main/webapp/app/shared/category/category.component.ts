import { Component, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { GetCategoriesService } from '../services/get-categories.service';

@Component({
  selector: 'jhi-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnDestroy {
  @Input() categoryId!: number | string;

  getCategoryJsonServiceSubscription: Subscription;
  categoriesJson = {};

  constructor(private getCategoriesService: GetCategoriesService) {
    this.getCategoryJsonServiceSubscription = this.getCategoriesService.categoriesJson.subscribe(json => {
      this.categoriesJson = json;
    });
  }

  ngOnDestroy(): void {
    this.getCategoryJsonServiceSubscription.unsubscribe();
  }
}
