import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupMetadataUpdateComponent } from 'app/entities/backup-metadata/backup-metadata-update.component';
import { BackupMetadataService } from 'app/entities/backup-metadata/backup-metadata.service';
import { BackupMetadata } from 'app/shared/model/backup-metadata.model';

describe('Component Tests', () => {
  describe('BackupMetadata Management Update Component', () => {
    let comp: BackupMetadataUpdateComponent;
    let fixture: ComponentFixture<BackupMetadataUpdateComponent>;
    let service: BackupMetadataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupMetadataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BackupMetadataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BackupMetadataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BackupMetadataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BackupMetadata(123);
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
        const entity = new BackupMetadata();
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
