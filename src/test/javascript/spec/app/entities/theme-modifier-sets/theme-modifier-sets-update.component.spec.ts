import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeModifierSetsUpdateComponent } from 'app/entities/theme-modifier-sets/theme-modifier-sets-update.component';
import { ThemeModifierSetsService } from 'app/entities/theme-modifier-sets/theme-modifier-sets.service';
import { ThemeModifierSets } from 'app/shared/model/theme-modifier-sets.model';

describe('Component Tests', () => {
  describe('ThemeModifierSets Management Update Component', () => {
    let comp: ThemeModifierSetsUpdateComponent;
    let fixture: ComponentFixture<ThemeModifierSetsUpdateComponent>;
    let service: ThemeModifierSetsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeModifierSetsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThemeModifierSetsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeModifierSetsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeModifierSetsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeModifierSets(123);
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
        const entity = new ThemeModifierSets();
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
