import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategorySearchDataUpdateComponent } from 'app/entities/category-search-data/category-search-data-update.component';
import { CategorySearchDataService } from 'app/entities/category-search-data/category-search-data.service';
import { CategorySearchData } from 'app/shared/model/category-search-data.model';

describe('Component Tests', () => {
  describe('CategorySearchData Management Update Component', () => {
    let comp: CategorySearchDataUpdateComponent;
    let fixture: ComponentFixture<CategorySearchDataUpdateComponent>;
    let service: CategorySearchDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategorySearchDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategorySearchDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorySearchDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorySearchDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorySearchData(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorySearchData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
