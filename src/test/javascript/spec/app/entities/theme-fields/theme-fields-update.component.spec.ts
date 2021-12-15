import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeFieldsUpdateComponent } from 'app/entities/theme-fields/theme-fields-update.component';
import { ThemeFieldsService } from 'app/entities/theme-fields/theme-fields.service';
import { ThemeFields } from 'app/shared/model/theme-fields.model';

describe('Component Tests', () => {
  describe('ThemeFields Management Update Component', () => {
    let comp: ThemeFieldsUpdateComponent;
    let fixture: ComponentFixture<ThemeFieldsUpdateComponent>;
    let service: ThemeFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThemeFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeFields(123);
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
        const entity = new ThemeFields();
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
