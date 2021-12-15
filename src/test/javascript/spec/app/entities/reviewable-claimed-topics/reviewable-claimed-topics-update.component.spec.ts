import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableClaimedTopicsUpdateComponent } from 'app/entities/reviewable-claimed-topics/reviewable-claimed-topics-update.component';
import { ReviewableClaimedTopicsService } from 'app/entities/reviewable-claimed-topics/reviewable-claimed-topics.service';
import { ReviewableClaimedTopics } from 'app/shared/model/reviewable-claimed-topics.model';

describe('Component Tests', () => {
  describe('ReviewableClaimedTopics Management Update Component', () => {
    let comp: ReviewableClaimedTopicsUpdateComponent;
    let fixture: ComponentFixture<ReviewableClaimedTopicsUpdateComponent>;
    let service: ReviewableClaimedTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableClaimedTopicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReviewableClaimedTopicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewableClaimedTopicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewableClaimedTopicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReviewableClaimedTopics(123);
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
        const entity = new ReviewableClaimedTopics();
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
