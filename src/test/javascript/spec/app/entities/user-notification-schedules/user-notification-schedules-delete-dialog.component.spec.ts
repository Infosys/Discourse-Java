import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { UserNotificationSchedulesDeleteDialogComponent } from 'app/entities/user-notification-schedules/user-notification-schedules-delete-dialog.component';
import { UserNotificationSchedulesService } from 'app/entities/user-notification-schedules/user-notification-schedules.service';

describe('Component Tests', () => {
  describe('UserNotificationSchedules Management Delete Component', () => {
    let comp: UserNotificationSchedulesDeleteDialogComponent;
    let fixture: ComponentFixture<UserNotificationSchedulesDeleteDialogComponent>;
    let service: UserNotificationSchedulesService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserNotificationSchedulesDeleteDialogComponent],
      })
        .overrideTemplate(UserNotificationSchedulesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserNotificationSchedulesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserNotificationSchedulesService);
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
