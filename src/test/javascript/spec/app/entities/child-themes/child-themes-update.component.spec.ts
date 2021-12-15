import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ChildThemesUpdateComponent } from 'app/entities/child-themes/child-themes-update.component';
import { ChildThemesService } from 'app/entities/child-themes/child-themes.service';
import { ChildThemes } from 'app/shared/model/child-themes.model';

describe('Component Tests', () => {
  describe('ChildThemes Management Update Component', () => {
    let comp: ChildThemesUpdateComponent;
    let fixture: ComponentFixture<ChildThemesUpdateComponent>;
    let service: ChildThemesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ChildThemesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ChildThemesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChildThemesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChildThemesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChildThemes(123);
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
        const entity = new ChildThemes();
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
