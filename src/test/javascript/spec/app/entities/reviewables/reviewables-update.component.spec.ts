import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewablesUpdateComponent } from 'app/entities/reviewables/reviewables-update.component';
import { ReviewablesService } from 'app/entities/reviewables/reviewables.service';
import { Reviewables } from 'app/shared/model/reviewables.model';

describe('Component Tests', () => {
  describe('Reviewables Management Update Component', () => {
    let comp: ReviewablesUpdateComponent;
    let fixture: ComponentFixture<ReviewablesUpdateComponent>;
    let service: ReviewablesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewablesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReviewablesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewablesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewablesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reviewables(123);
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
        const entity = new Reviewables();
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
