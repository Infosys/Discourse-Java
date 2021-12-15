import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UploadsUpdateComponent } from 'app/entities/uploads/uploads-update.component';
import { UploadsService } from 'app/entities/uploads/uploads.service';
import { Uploads } from 'app/shared/model/uploads.model';

describe('Component Tests', () => {
  describe('Uploads Management Update Component', () => {
    let comp: UploadsUpdateComponent;
    let fixture: ComponentFixture<UploadsUpdateComponent>;
    let service: UploadsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UploadsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UploadsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Uploads(123);
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
        const entity = new Uploads();
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
