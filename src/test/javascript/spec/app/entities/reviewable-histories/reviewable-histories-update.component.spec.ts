import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableHistoriesUpdateComponent } from 'app/entities/reviewable-histories/reviewable-histories-update.component';
import { ReviewableHistoriesService } from 'app/entities/reviewable-histories/reviewable-histories.service';
import { ReviewableHistories } from 'app/shared/model/reviewable-histories.model';

describe('Component Tests', () => {
  describe('ReviewableHistories Management Update Component', () => {
    let comp: ReviewableHistoriesUpdateComponent;
    let fixture: ComponentFixture<ReviewableHistoriesUpdateComponent>;
    let service: ReviewableHistoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableHistoriesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReviewableHistoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewableHistoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewableHistoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReviewableHistories(123);
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
        const entity = new ReviewableHistories();
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
