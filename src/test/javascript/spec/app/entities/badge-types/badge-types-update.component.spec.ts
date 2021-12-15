import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgeTypesUpdateComponent } from 'app/entities/badge-types/badge-types-update.component';
import { BadgeTypesService } from 'app/entities/badge-types/badge-types.service';
import { BadgeTypes } from 'app/shared/model/badge-types.model';

describe('Component Tests', () => {
  describe('BadgeTypes Management Update Component', () => {
    let comp: BadgeTypesUpdateComponent;
    let fixture: ComponentFixture<BadgeTypesUpdateComponent>;
    let service: BadgeTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgeTypesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BadgeTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadgeTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadgeTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BadgeTypes(123);
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
        const entity = new BadgeTypes();
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
