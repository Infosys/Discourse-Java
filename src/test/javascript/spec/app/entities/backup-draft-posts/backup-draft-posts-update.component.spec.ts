import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BackupDraftPostsUpdateComponent } from 'app/entities/backup-draft-posts/backup-draft-posts-update.component';
import { BackupDraftPostsService } from 'app/entities/backup-draft-posts/backup-draft-posts.service';
import { BackupDraftPosts } from 'app/shared/model/backup-draft-posts.model';

describe('Component Tests', () => {
  describe('BackupDraftPosts Management Update Component', () => {
    let comp: BackupDraftPostsUpdateComponent;
    let fixture: ComponentFixture<BackupDraftPostsUpdateComponent>;
    let service: BackupDraftPostsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BackupDraftPostsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BackupDraftPostsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BackupDraftPostsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BackupDraftPostsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BackupDraftPosts(123);
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
        const entity = new BackupDraftPosts();
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
