import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostRevisionsUpdateComponent } from 'app/entities/post-revisions/post-revisions-update.component';
import { PostRevisionsService } from 'app/entities/post-revisions/post-revisions.service';
import { PostRevisions } from 'app/shared/model/post-revisions.model';

describe('Component Tests', () => {
  describe('PostRevisions Management Update Component', () => {
    let comp: PostRevisionsUpdateComponent;
    let fixture: ComponentFixture<PostRevisionsUpdateComponent>;
    let service: PostRevisionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostRevisionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostRevisionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostRevisionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostRevisionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostRevisions(123);
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
        const entity = new PostRevisions();
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
