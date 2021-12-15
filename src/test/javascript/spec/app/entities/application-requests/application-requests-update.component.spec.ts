import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApplicationRequestsUpdateComponent } from 'app/entities/application-requests/application-requests-update.component';
import { ApplicationRequestsService } from 'app/entities/application-requests/application-requests.service';
import { ApplicationRequests } from 'app/shared/model/application-requests.model';

describe('Component Tests', () => {
  describe('ApplicationRequests Management Update Component', () => {
    let comp: ApplicationRequestsUpdateComponent;
    let fixture: ComponentFixture<ApplicationRequestsUpdateComponent>;
    let service: ApplicationRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApplicationRequestsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicationRequestsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationRequestsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationRequestsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationRequests(123);
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
        const entity = new ApplicationRequests();
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
