import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ThemeModifierSetsDeleteDialogComponent } from 'app/entities/theme-modifier-sets/theme-modifier-sets-delete-dialog.component';
import { ThemeModifierSetsService } from 'app/entities/theme-modifier-sets/theme-modifier-sets.service';

describe('Component Tests', () => {
  describe('ThemeModifierSets Management Delete Component', () => {
    let comp: ThemeModifierSetsDeleteDialogComponent;
    let fixture: ComponentFixture<ThemeModifierSetsDeleteDialogComponent>;
    let service: ThemeModifierSetsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeModifierSetsDeleteDialogComponent],
      })
        .overrideTemplate(ThemeModifierSetsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeModifierSetsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeModifierSetsService);
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
