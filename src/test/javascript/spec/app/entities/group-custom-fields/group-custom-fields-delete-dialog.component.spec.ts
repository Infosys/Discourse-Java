import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { GroupCustomFieldsDeleteDialogComponent } from 'app/entities/group-custom-fields/group-custom-fields-delete-dialog.component';
import { GroupCustomFieldsService } from 'app/entities/group-custom-fields/group-custom-fields.service';

describe('Component Tests', () => {
  describe('GroupCustomFields Management Delete Component', () => {
    let comp: GroupCustomFieldsDeleteDialogComponent;
    let fixture: ComponentFixture<GroupCustomFieldsDeleteDialogComponent>;
    let service: GroupCustomFieldsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCustomFieldsDeleteDialogComponent],
      })
        .overrideTemplate(GroupCustomFieldsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupCustomFieldsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupCustomFieldsService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
