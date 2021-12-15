import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupDraftTopicsDetailComponent } from 'app/entities/backup-draft-topics/backup-draft-topics-detail.component';
import { BackupDraftTopics } from 'app/shared/model/backup-draft-topics.model';

describe('Component Tests', () => {
  describe('BackupDraftTopics Management Detail Component', () => {
    let comp: BackupDraftTopicsDetailComponent;
    let fixture: ComponentFixture<BackupDraftTopicsDetailComponent>;
    const route = ({ data: of({ backupDraftTopics: new BackupDraftTopics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupDraftTopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BackupDraftTopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BackupDraftTopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load backupDraftTopics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.backupDraftTopics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
