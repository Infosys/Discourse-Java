import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailChangeRequestsUpdateComponent } from 'app/entities/email-change-requests/email-change-requests-update.component';
import { EmailChangeRequestsService } from 'app/entities/email-change-requests/email-change-requests.service';
import { EmailChangeRequests } from 'app/shared/model/email-change-requests.model';

describe('Component Tests', () => {
  describe('EmailChangeRequests Management Update Component', () => {
    let comp: EmailChangeRequestsUpdateComponent;
    let fixture: ComponentFixture<EmailChangeRequestsUpdateComponent>;
    let service: EmailChangeRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailChangeRequestsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmailChangeRequestsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailChangeRequestsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailChangeRequestsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailChangeRequests(123);
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
        const entity = new EmailChangeRequests();
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
