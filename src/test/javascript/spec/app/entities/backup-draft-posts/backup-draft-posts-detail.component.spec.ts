import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupDraftPostsDetailComponent } from 'app/entities/backup-draft-posts/backup-draft-posts-detail.component';
import { BackupDraftPosts } from 'app/shared/model/backup-draft-posts.model';

describe('Component Tests', () => {
  describe('BackupDraftPosts Management Detail Component', () => {
    let comp: BackupDraftPostsDetailComponent;
    let fixture: ComponentFixture<BackupDraftPostsDetailComponent>;
    const route = ({ data: of({ backupDraftPosts: new BackupDraftPosts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupDraftPostsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BackupDraftPostsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BackupDraftPostsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load backupDraftPosts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.backupDraftPosts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
