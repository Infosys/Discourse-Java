import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { DoNotDisturbTimingsDeleteDialogComponent } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings-delete-dialog.component';
import { DoNotDisturbTimingsService } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings.service';

describe('Component Tests', () => {
  describe('DoNotDisturbTimings Management Delete Component', () => {
    let comp: DoNotDisturbTimingsDeleteDialogComponent;
    let fixture: ComponentFixture<DoNotDisturbTimingsDeleteDialogComponent>;
    let service: DoNotDisturbTimingsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DoNotDisturbTimingsDeleteDialogComponent],
      })
        .overrideTemplate(DoNotDisturbTimingsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoNotDisturbTimingsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoNotDisturbTimingsService);
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
