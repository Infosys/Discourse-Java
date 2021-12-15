import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { WebCrawlerRequestsDeleteDialogComponent } from 'app/entities/web-crawler-requests/web-crawler-requests-delete-dialog.component';
import { WebCrawlerRequestsService } from 'app/entities/web-crawler-requests/web-crawler-requests.service';

describe('Component Tests', () => {
  describe('WebCrawlerRequests Management Delete Component', () => {
    let comp: WebCrawlerRequestsDeleteDialogComponent;
    let fixture: ComponentFixture<WebCrawlerRequestsDeleteDialogComponent>;
    let service: WebCrawlerRequestsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebCrawlerRequestsDeleteDialogComponent],
      })
        .overrideTemplate(WebCrawlerRequestsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WebCrawlerRequestsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebCrawlerRequestsService);
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
