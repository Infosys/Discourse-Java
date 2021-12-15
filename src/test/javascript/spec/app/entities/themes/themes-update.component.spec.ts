import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemesUpdateComponent } from 'app/entities/themes/themes-update.component';
import { ThemesService } from 'app/entities/themes/themes.service';
import { Themes } from 'app/shared/model/themes.model';

describe('Component Tests', () => {
  describe('Themes Management Update Component', () => {
    let comp: ThemesUpdateComponent;
    let fixture: ComponentFixture<ThemesUpdateComponent>;
    let service: ThemesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThemesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Themes(123);
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
        const entity = new Themes();
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
