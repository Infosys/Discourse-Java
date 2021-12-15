import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SingleSignOnRecordsDeleteDialogComponent } from 'app/entities/single-sign-on-records/single-sign-on-records-delete-dialog.component';
import { SingleSignOnRecordsService } from 'app/entities/single-sign-on-records/single-sign-on-records.service';

describe('Component Tests', () => {
  describe('SingleSignOnRecords Management Delete Component', () => {
    let comp: SingleSignOnRecordsDeleteDialogComponent;
    let fixture: ComponentFixture<SingleSignOnRecordsDeleteDialogComponent>;
    let service: SingleSignOnRecordsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SingleSignOnRecordsDeleteDialogComponent],
      })
        .overrideTemplate(SingleSignOnRecordsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SingleSignOnRecordsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SingleSignOnRecordsService);
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
