import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableScoresUpdateComponent } from 'app/entities/reviewable-scores/reviewable-scores-update.component';
import { ReviewableScoresService } from 'app/entities/reviewable-scores/reviewable-scores.service';
import { ReviewableScores } from 'app/shared/model/reviewable-scores.model';

describe('Component Tests', () => {
  describe('ReviewableScores Management Update Component', () => {
    let comp: ReviewableScoresUpdateComponent;
    let fixture: ComponentFixture<ReviewableScoresUpdateComponent>;
    let service: ReviewableScoresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableScoresUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReviewableScoresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewableScoresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewableScoresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReviewableScores(123);
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
        const entity = new ReviewableScores();
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
