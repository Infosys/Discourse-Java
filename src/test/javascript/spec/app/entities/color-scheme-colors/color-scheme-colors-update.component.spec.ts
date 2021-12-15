import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ColorSchemeColorsUpdateComponent } from 'app/entities/color-scheme-colors/color-scheme-colors-update.component';
import { ColorSchemeColorsService } from 'app/entities/color-scheme-colors/color-scheme-colors.service';
import { ColorSchemeColors } from 'app/shared/model/color-scheme-colors.model';

describe('Component Tests', () => {
  describe('ColorSchemeColors Management Update Component', () => {
    let comp: ColorSchemeColorsUpdateComponent;
    let fixture: ComponentFixture<ColorSchemeColorsUpdateComponent>;
    let service: ColorSchemeColorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ColorSchemeColorsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ColorSchemeColorsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColorSchemeColorsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColorSchemeColorsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ColorSchemeColors(123);
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
        const entity = new ColorSchemeColors();
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
