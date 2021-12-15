import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { AnnouncmentUpdateComponent } from 'app/entities/announcment/announcment-update.component';
import { AnnouncmentService } from 'app/entities/announcment/announcment.service';
import { Announcment } from 'app/shared/model/announcment.model';

describe('Component Tests', () => {
  describe('Announcment Management Update Component', () => {
    let comp: AnnouncmentUpdateComponent;
    let fixture: ComponentFixture<AnnouncmentUpdateComponent>;
    let service: AnnouncmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AnnouncmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnnouncmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnnouncmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnnouncmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Announcment(123);
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
        const entity = new Announcment();
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
