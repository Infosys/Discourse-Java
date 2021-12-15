import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingEmailsUpdateComponent } from 'app/entities/incoming-emails/incoming-emails-update.component';
import { IncomingEmailsService } from 'app/entities/incoming-emails/incoming-emails.service';
import { IncomingEmails } from 'app/shared/model/incoming-emails.model';

describe('Component Tests', () => {
  describe('IncomingEmails Management Update Component', () => {
    let comp: IncomingEmailsUpdateComponent;
    let fixture: ComponentFixture<IncomingEmailsUpdateComponent>;
    let service: IncomingEmailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingEmailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomingEmailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomingEmailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomingEmailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncomingEmails(123);
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
        const entity = new IncomingEmails();
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
