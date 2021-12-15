import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SiteSettingsUpdateComponent } from 'app/entities/site-settings/site-settings-update.component';
import { SiteSettingsService } from 'app/entities/site-settings/site-settings.service';
import { SiteSettings } from 'app/shared/model/site-settings.model';

describe('Component Tests', () => {
  describe('SiteSettings Management Update Component', () => {
    let comp: SiteSettingsUpdateComponent;
    let fixture: ComponentFixture<SiteSettingsUpdateComponent>;
    let service: SiteSettingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SiteSettingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SiteSettingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SiteSettingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SiteSettingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SiteSettings(123);
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
        const entity = new SiteSettings();
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
