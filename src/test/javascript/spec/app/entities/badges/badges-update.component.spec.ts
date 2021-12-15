import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgesUpdateComponent } from 'app/entities/badges/badges-update.component';
import { BadgesService } from 'app/entities/badges/badges.service';
import { Badges } from 'app/shared/model/badges.model';

describe('Component Tests', () => {
  describe('Badges Management Update Component', () => {
    let comp: BadgesUpdateComponent;
    let fixture: ComponentFixture<BadgesUpdateComponent>;
    let service: BadgesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BadgesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadgesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadgesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Badges(123);
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
        const entity = new Badges();
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
