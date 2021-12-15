import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagStatsUpdateComponent } from 'app/entities/category-tag-stats/category-tag-stats-update.component';
import { CategoryTagStatsService } from 'app/entities/category-tag-stats/category-tag-stats.service';
import { CategoryTagStats } from 'app/shared/model/category-tag-stats.model';

describe('Component Tests', () => {
  describe('CategoryTagStats Management Update Component', () => {
    let comp: CategoryTagStatsUpdateComponent;
    let fixture: ComponentFixture<CategoryTagStatsUpdateComponent>;
    let service: CategoryTagStatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagStatsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryTagStatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryTagStatsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryTagStatsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryTagStats(123);
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
        const entity = new CategoryTagStats();
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
