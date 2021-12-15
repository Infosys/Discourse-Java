import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ImapSyncLogsUpdateComponent } from 'app/entities/imap-sync-logs/imap-sync-logs-update.component';
import { ImapSyncLogsService } from 'app/entities/imap-sync-logs/imap-sync-logs.service';
import { ImapSyncLogs } from 'app/shared/model/imap-sync-logs.model';

describe('Component Tests', () => {
  describe('ImapSyncLogs Management Update Component', () => {
    let comp: ImapSyncLogsUpdateComponent;
    let fixture: ComponentFixture<ImapSyncLogsUpdateComponent>;
    let service: ImapSyncLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ImapSyncLogsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ImapSyncLogsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImapSyncLogsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImapSyncLogsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImapSyncLogs(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImapSyncLogs();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
