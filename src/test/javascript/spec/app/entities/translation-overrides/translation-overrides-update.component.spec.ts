import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TranslationOverridesUpdateComponent } from 'app/entities/translation-overrides/translation-overrides-update.component';
import { TranslationOverridesService } from 'app/entities/translation-overrides/translation-overrides.service';
import { TranslationOverrides } from 'app/shared/model/translation-overrides.model';

describe('Component Tests', () => {
  describe('TranslationOverrides Management Update Component', () => {
    let comp: TranslationOverridesUpdateComponent;
    let fixture: ComponentFixture<TranslationOverridesUpdateComponent>;
    let service: TranslationOverridesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TranslationOverridesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TranslationOverridesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TranslationOverridesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TranslationOverridesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TranslationOverrides(123);
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
        const entity = new TranslationOverrides();
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
