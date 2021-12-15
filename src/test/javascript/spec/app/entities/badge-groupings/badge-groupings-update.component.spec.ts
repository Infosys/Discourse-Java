import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgeGroupingsUpdateComponent } from 'app/entities/badge-groupings/badge-groupings-update.component';
import { BadgeGroupingsService } from 'app/entities/badge-groupings/badge-groupings.service';
import { BadgeGroupings } from 'app/shared/model/badge-groupings.model';

describe('Component Tests', () => {
  describe('BadgeGroupings Management Update Component', () => {
    let comp: BadgeGroupingsUpdateComponent;
    let fixture: ComponentFixture<BadgeGroupingsUpdateComponent>;
    let service: BadgeGroupingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgeGroupingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BadgeGroupingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadgeGroupingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadgeGroupingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BadgeGroupings(123);
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
        const entity = new BadgeGroupings();
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
