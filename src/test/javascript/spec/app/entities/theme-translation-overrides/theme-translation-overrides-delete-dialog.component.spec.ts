import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ThemeTranslationOverridesDeleteDialogComponent } from 'app/entities/theme-translation-overrides/theme-translation-overrides-delete-dialog.component';
import { ThemeTranslationOverridesService } from 'app/entities/theme-translation-overrides/theme-translation-overrides.service';

describe('Component Tests', () => {
  describe('ThemeTranslationOverrides Management Delete Component', () => {
    let comp: ThemeTranslationOverridesDeleteDialogComponent;
    let fixture: ComponentFixture<ThemeTranslationOverridesDeleteDialogComponent>;
    let service: ThemeTranslationOverridesService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeTranslationOverridesDeleteDialogComponent],
      })
        .overrideTemplate(ThemeTranslationOverridesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeTranslationOverridesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeTranslationOverridesService);
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
