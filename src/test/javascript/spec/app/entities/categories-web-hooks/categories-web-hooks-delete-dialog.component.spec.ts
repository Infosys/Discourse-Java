import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CategoriesWebHooksDeleteDialogComponent } from 'app/entities/categories-web-hooks/categories-web-hooks-delete-dialog.component';
import { CategoriesWebHooksService } from 'app/entities/categories-web-hooks/categories-web-hooks.service';

describe('Component Tests', () => {
  describe('CategoriesWebHooks Management Delete Component', () => {
    let comp: CategoriesWebHooksDeleteDialogComponent;
    let fixture: ComponentFixture<CategoriesWebHooksDeleteDialogComponent>;
    let service: CategoriesWebHooksService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoriesWebHooksDeleteDialogComponent],
      })
        .overrideTemplate(CategoriesWebHooksDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriesWebHooksDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriesWebHooksService);
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
