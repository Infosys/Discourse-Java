import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeSettingsUpdateComponent } from 'app/entities/theme-settings/theme-settings-update.component';
import { ThemeSettingsService } from 'app/entities/theme-settings/theme-settings.service';
import { ThemeSettings } from 'app/shared/model/theme-settings.model';

describe('Component Tests', () => {
  describe('ThemeSettings Management Update Component', () => {
    let comp: ThemeSettingsUpdateComponent;
    let fixture: ComponentFixture<ThemeSettingsUpdateComponent>;
    let service: ThemeSettingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeSettingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThemeSettingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeSettingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeSettingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeSettings(123);
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
        const entity = new ThemeSettings();
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
