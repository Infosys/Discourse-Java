import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserEmailsUpdateComponent } from 'app/entities/user-emails/user-emails-update.component';
import { UserEmailsService } from 'app/entities/user-emails/user-emails.service';
import { UserEmails } from 'app/shared/model/user-emails.model';

describe('Component Tests', () => {
  describe('UserEmails Management Update Component', () => {
    let comp: UserEmailsUpdateComponent;
    let fixture: ComponentFixture<UserEmailsUpdateComponent>;
    let service: UserEmailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserEmailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserEmailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserEmailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserEmailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserEmails(123);
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
        const entity = new UserEmails();
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
