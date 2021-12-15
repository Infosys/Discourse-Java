import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ImapSyncLogsDetailComponent } from 'app/entities/imap-sync-logs/imap-sync-logs-detail.component';
import { ImapSyncLogs } from 'app/shared/model/imap-sync-logs.model';

describe('Component Tests', () => {
  describe('ImapSyncLogs Management Detail Component', () => {
    let comp: ImapSyncLogsDetailComponent;
    let fixture: ComponentFixture<ImapSyncLogsDetailComponent>;
    const route = ({ data: of({ imapSyncLogs: new ImapSyncLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ImapSyncLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ImapSyncLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImapSyncLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load imapSyncLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imapSyncLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
