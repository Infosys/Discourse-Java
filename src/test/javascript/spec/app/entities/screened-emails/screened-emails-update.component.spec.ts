import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedEmailsUpdateComponent } from 'app/entities/screened-emails/screened-emails-update.component';
import { ScreenedEmailsService } from 'app/entities/screened-emails/screened-emails.service';
import { ScreenedEmails } from 'app/shared/model/screened-emails.model';

describe('Component Tests', () => {
  describe('ScreenedEmails Management Update Component', () => {
    let comp: ScreenedEmailsUpdateComponent;
    let fixture: ComponentFixture<ScreenedEmailsUpdateComponent>;
    let service: ScreenedEmailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedEmailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ScreenedEmailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScreenedEmailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScreenedEmailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScreenedEmails(123);
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
        const entity = new ScreenedEmails();
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
