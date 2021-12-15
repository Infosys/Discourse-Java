import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupDraftTopicsUpdateComponent } from 'app/entities/backup-draft-topics/backup-draft-topics-update.component';
import { BackupDraftTopicsService } from 'app/entities/backup-draft-topics/backup-draft-topics.service';
import { BackupDraftTopics } from 'app/shared/model/backup-draft-topics.model';

describe('Component Tests', () => {
  describe('BackupDraftTopics Management Update Component', () => {
    let comp: BackupDraftTopicsUpdateComponent;
    let fixture: ComponentFixture<BackupDraftTopicsUpdateComponent>;
    let service: BackupDraftTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupDraftTopicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BackupDraftTopicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BackupDraftTopicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BackupDraftTopicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BackupDraftTopics(123);
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
        const entity = new BackupDraftTopics();
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
