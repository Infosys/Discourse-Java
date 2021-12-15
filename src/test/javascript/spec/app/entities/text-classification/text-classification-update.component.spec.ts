import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TextClassificationUpdateComponent } from 'app/entities/text-classification/text-classification-update.component';
import { TextClassificationService } from 'app/entities/text-classification/text-classification.service';
import { TextClassification } from 'app/shared/model/text-classification.model';

describe('Component Tests', () => {
  describe('TextClassification Management Update Component', () => {
    let comp: TextClassificationUpdateComponent;
    let fixture: ComponentFixture<TextClassificationUpdateComponent>;
    let service: TextClassificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TextClassificationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TextClassificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TextClassificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TextClassificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TextClassification(123);
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
        const entity = new TextClassification();
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
