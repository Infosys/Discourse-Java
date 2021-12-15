import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DraftSequencesUpdateComponent } from 'app/entities/draft-sequences/draft-sequences-update.component';
import { DraftSequencesService } from 'app/entities/draft-sequences/draft-sequences.service';
import { DraftSequences } from 'app/shared/model/draft-sequences.model';

describe('Component Tests', () => {
  describe('DraftSequences Management Update Component', () => {
    let comp: DraftSequencesUpdateComponent;
    let fixture: ComponentFixture<DraftSequencesUpdateComponent>;
    let service: DraftSequencesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DraftSequencesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DraftSequencesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DraftSequencesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DraftSequencesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DraftSequences(123);
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
        const entity = new DraftSequences();
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
