import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupArchivedMessagesUpdateComponent } from 'app/entities/group-archived-messages/group-archived-messages-update.component';
import { GroupArchivedMessagesService } from 'app/entities/group-archived-messages/group-archived-messages.service';
import { GroupArchivedMessages } from 'app/shared/model/group-archived-messages.model';

describe('Component Tests', () => {
  describe('GroupArchivedMessages Management Update Component', () => {
    let comp: GroupArchivedMessagesUpdateComponent;
    let fixture: ComponentFixture<GroupArchivedMessagesUpdateComponent>;
    let service: GroupArchivedMessagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupArchivedMessagesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupArchivedMessagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupArchivedMessagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupArchivedMessagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupArchivedMessages(123);
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
        const entity = new GroupArchivedMessages();
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
