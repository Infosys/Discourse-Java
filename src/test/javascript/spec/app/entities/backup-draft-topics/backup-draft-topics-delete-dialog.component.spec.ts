import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BackupDraftTopicsDeleteDialogComponent } from 'app/entities/backup-draft-topics/backup-draft-topics-delete-dialog.component';
import { BackupDraftTopicsService } from 'app/entities/backup-draft-topics/backup-draft-topics.service';

describe('Component Tests', () => {
  describe('BackupDraftTopics Management Delete Component', () => {
    let comp: BackupDraftTopicsDeleteDialogComponent;
    let fixture: ComponentFixture<BackupDraftTopicsDeleteDialogComponent>;
    let service: BackupDraftTopicsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupDraftTopicsDeleteDialogComponent],
      })
        .overrideTemplate(BackupDraftTopicsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BackupDraftTopicsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BackupDraftTopicsService);
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
