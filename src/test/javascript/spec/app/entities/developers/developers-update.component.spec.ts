import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DevelopersUpdateComponent } from 'app/entities/developers/developers-update.component';
import { DevelopersService } from 'app/entities/developers/developers.service';
import { Developers } from 'app/shared/model/developers.model';

describe('Component Tests', () => {
  describe('Developers Management Update Component', () => {
    let comp: DevelopersUpdateComponent;
    let fixture: ComponentFixture<DevelopersUpdateComponent>;
    let service: DevelopersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DevelopersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DevelopersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevelopersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevelopersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Developers(123);
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
        const entity = new Developers();
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
