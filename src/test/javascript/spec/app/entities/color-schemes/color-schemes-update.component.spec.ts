import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ColorSchemesUpdateComponent } from 'app/entities/color-schemes/color-schemes-update.component';
import { ColorSchemesService } from 'app/entities/color-schemes/color-schemes.service';
import { ColorSchemes } from 'app/shared/model/color-schemes.model';

describe('Component Tests', () => {
  describe('ColorSchemes Management Update Component', () => {
    let comp: ColorSchemesUpdateComponent;
    let fixture: ComponentFixture<ColorSchemesUpdateComponent>;
    let service: ColorSchemesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ColorSchemesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ColorSchemesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColorSchemesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColorSchemesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ColorSchemes(123);
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
        const entity = new ColorSchemes();
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
