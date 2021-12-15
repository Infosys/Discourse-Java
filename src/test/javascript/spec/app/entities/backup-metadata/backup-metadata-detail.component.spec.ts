import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupMetadataDetailComponent } from 'app/entities/backup-metadata/backup-metadata-detail.component';
import { BackupMetadata } from 'app/shared/model/backup-metadata.model';

describe('Component Tests', () => {
  describe('BackupMetadata Management Detail Component', () => {
    let comp: BackupMetadataDetailComponent;
    let fixture: ComponentFixture<BackupMetadataDetailComponent>;
    const route = ({ data: of({ backupMetadata: new BackupMetadata(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupMetadataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BackupMetadataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BackupMetadataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load backupMetadata on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.backupMetadata).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
