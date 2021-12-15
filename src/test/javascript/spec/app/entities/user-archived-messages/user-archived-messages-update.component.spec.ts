import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserArchivedMessagesUpdateComponent } from 'app/entities/user-archived-messages/user-archived-messages-update.component';
import { UserArchivedMessagesService } from 'app/entities/user-archived-messages/user-archived-messages.service';
import { UserArchivedMessages } from 'app/shared/model/user-archived-messages.model';

describe('Component Tests', () => {
  describe('UserArchivedMessages Management Update Component', () => {
    let comp: UserArchivedMessagesUpdateComponent;
    let fixture: ComponentFixture<UserArchivedMessagesUpdateComponent>;
    let service: UserArchivedMessagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserArchivedMessagesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserArchivedMessagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserArchivedMessagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserArchivedMessagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserArchivedMessages(123);
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
        const entity = new UserArchivedMessages();
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
