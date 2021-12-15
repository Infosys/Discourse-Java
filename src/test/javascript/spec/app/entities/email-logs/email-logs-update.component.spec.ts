import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailLogsUpdateComponent } from 'app/entities/email-logs/email-logs-update.component';
import { EmailLogsService } from 'app/entities/email-logs/email-logs.service';
import { EmailLogs } from 'app/shared/model/email-logs.model';

describe('Component Tests', () => {
  describe('EmailLogs Management Update Component', () => {
    let comp: EmailLogsUpdateComponent;
    let fixture: ComponentFixture<EmailLogsUpdateComponent>;
    let service: EmailLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmailLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailLogs(123);
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
        const entity = new EmailLogs();
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
