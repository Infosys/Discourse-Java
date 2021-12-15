import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SingleSignOnRecordsUpdateComponent } from 'app/entities/single-sign-on-records/single-sign-on-records-update.component';
import { SingleSignOnRecordsService } from 'app/entities/single-sign-on-records/single-sign-on-records.service';
import { SingleSignOnRecords } from 'app/shared/model/single-sign-on-records.model';

describe('Component Tests', () => {
  describe('SingleSignOnRecords Management Update Component', () => {
    let comp: SingleSignOnRecordsUpdateComponent;
    let fixture: ComponentFixture<SingleSignOnRecordsUpdateComponent>;
    let service: SingleSignOnRecordsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SingleSignOnRecordsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SingleSignOnRecordsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SingleSignOnRecordsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SingleSignOnRecordsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SingleSignOnRecords(123);
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
        const entity = new SingleSignOnRecords();
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
