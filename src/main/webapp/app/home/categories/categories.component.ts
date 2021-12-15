import { AppUtilServices } from '../../shared/services/app-util.services';
import { CategoryService } from '../../shared/services/category.service';
import { Component, ElementRef, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { ICategory } from 'app/shared/model/category/category.model';
import { GetCategoriesService } from 'app/shared/services/get-categories.service';
import { ThemePalette } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Color } from '@angular-material-components/color-picker';
import { ViewportChangeDetector } from 'app/shared/services/viewport-change-detector';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
})
export class CategoriesComponent implements OnInit, OnDestroy {
  @ViewChild('categoryDialog') categoryDialog!: ElementRef;
  isViewSmall = false;
  categoryForm!: FormGroup;
  getCategoryListServiceSubscription: Subscription;
  categoryList: ICategory[] = [];
  editMode = false;
  currentCategoryId: any;
  changeView = false;
  viewportChangeDetectorSubsription: Subscription;

  public disabled = false;
  public color: ThemePalette = 'primary';
  public textColor: ThemePalette = 'primary';
  public touchUi = false;

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private getCategoriesService: GetCategoriesService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar,
    private appUtilServices: AppUtilServices,
    private viewportChangeDetector: ViewportChangeDetector,
    private loader: NgxSpinnerService
  ) {
    this.viewportChangeDetectorSubsription = this.viewportChangeDetector
      .detectScreenSize({ xs: true })
      .subscribe(isViewSmall => (this.isViewSmall = isViewSmall!));
    this.getCategoryListServiceSubscription = this.getCategoriesService.categoryList.subscribe((categories: ICategory[]) => {
      this.categoryList = categories;
    });
  }

  ngOnInit(): void {
    this.initialiseFormGroup();
  }

  ngOnDestroy(): void {
    this.getCategoryListServiceSubscription.unsubscribe();
    this.viewportChangeDetectorSubsription.unsubscribe();
  }

  initialiseFormGroup(): void {
    this.categoryForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: [''],
      color: ['', [Validators.required, Validators.pattern(/^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/)]],
      text_color: ['', [Validators.required, Validators.pattern(/^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/)]],
    });
  }

  changeCategoryView(): void {
    this.changeView = !this.changeView;
  }

  initiateCreate(): void {
    this.editMode = false;
    this.categoryForm.reset();
    this.open(this.categoryDialog);
  }

  inititateEdit(category: ICategory): void {
    this.editMode = true;
    const color = this.hexToRgb(category?.color);
    const text_color = this.hexToRgb(category?.text_color);
    this.categoryForm.patchValue({
      name: category?.name,
      description: category?.description,
      color: new Color(color.r, color.g, color.b, 1),
      text_color: new Color(text_color.r, text_color.g, text_color.b, 1),
    });
    this.currentCategoryId = category?.id;
    this.open(this.categoryDialog);
  }

  createNewCategory(): void {
    const newCategory = {
      name: this.categoryForm.value.name,
      description: this.categoryForm.value.description,
      color: `#${this.categoryForm.value.color.hex}`,
      text_color: `#${this.categoryForm.value.text_color.hex}`,
    };
    this.loader.show();
    this.categoryService.createCategory(newCategory).subscribe(
      () => {
        this.modalService.dismissAll();
        this.getAllCategories();
        this.appUtilServices.openSnackBar('New category created Successfully', '', 3000);
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        this.appUtilServices.openSnackBar('Please try again', '', 3000);
        console.error(err);
        this.loader.hide();
      }
    );
  }

  editCategory(): void {
    const editedCategory = {
      name: this.categoryForm.value.name,
      description: this.categoryForm.value.description,
      color: `#${this.categoryForm.value.color.hex}`,
      text_color: `#${this.categoryForm.value.text_color.hex}`,
    };
    this.loader.show();
    this.categoryService.updateCategory(this.currentCategoryId, editedCategory).subscribe(
      () => {
        this.modalService.dismissAll();
        this.getAllCategories();
        this.appUtilServices.openSnackBar('Updated category successfully', '', 3000);
        this.loader.hide();
      },
      (err: HttpErrorResponse) => {
        this.appUtilServices.openSnackBar('Please try again', '', 3000);
        console.error(err);
        this.loader.hide();
      }
    );
  }

  // deleteCategory(category: ICategory): void {
  // }

  resetCategory(): void {
    this.categoryForm.reset();
  }

  open(content: any): void {
    this.modalService.open(content, { windowClass: 'web_custom_modal DSA_modal-sm' });
  }

  getAllCategories(): void {
    this.getCategoriesService.getCategories();
  }

  hexToRgb(hex: any): any {
    const shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
    hex = hex.replace(shorthandRegex, (m: any, r: any, g: any, b: any) => {
      return r + r + g + g + b + b;
    });
    const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result
      ? {
          r: parseInt(result[1], 16),
          g: parseInt(result[2], 16),
          b: parseInt(result[3], 16),
        }
      : null;
  }

  categoryCardStyle(category: ICategory): Object {
    let style = {};
    if (this.changeView) {
      style = { borderLeftStyle: 'solid', borderLeftWidth: '12px', background: category.color, borderLeftColor: category.color };
    } else {
      style = {
        borderLeftStyle: 'solid',
        borderLeftWidth: '12px',
        borderLeftColor: category.color,
      };
    }
    return style;
  }
}
