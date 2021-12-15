import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DiscourseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { Oauth2UserInfosDeleteDialogComponent } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos-delete-dialog.component';
import { Oauth2UserInfosService } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos.service';

describe('Component Tests', () => {
  describe('Oauth2UserInfos Management Delete Component', () => {
    let comp: Oauth2UserInfosDeleteDialogComponent;
    let fixture: ComponentFixture<Oauth2UserInfosDeleteDialogComponent>;
    let service: Oauth2UserInfosService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [Oauth2UserInfosDeleteDialogComponent],
      })
        .overrideTemplate(Oauth2UserInfosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Oauth2UserInfosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Oauth2UserInfosService);
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
