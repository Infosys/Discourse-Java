import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeTranslationOverridesUpdateComponent } from 'app/entities/theme-translation-overrides/theme-translation-overrides-update.component';
import { ThemeTranslationOverridesService } from 'app/entities/theme-translation-overrides/theme-translation-overrides.service';
import { ThemeTranslationOverrides } from 'app/shared/model/theme-translation-overrides.model';

describe('Component Tests', () => {
  describe('ThemeTranslationOverrides Management Update Component', () => {
    let comp: ThemeTranslationOverridesUpdateComponent;
    let fixture: ComponentFixture<ThemeTranslationOverridesUpdateComponent>;
    let service: ThemeTranslationOverridesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeTranslationOverridesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThemeTranslationOverridesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeTranslationOverridesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeTranslationOverridesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeTranslationOverrides(123);
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
        const entity = new ThemeTranslationOverrides();
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
