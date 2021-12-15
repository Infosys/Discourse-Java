import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostRepliesUpdateComponent } from 'app/entities/post-replies/post-replies-update.component';
import { PostRepliesService } from 'app/entities/post-replies/post-replies.service';
import { PostReplies } from 'app/shared/model/post-replies.model';

describe('Component Tests', () => {
  describe('PostReplies Management Update Component', () => {
    let comp: PostRepliesUpdateComponent;
    let fixture: ComponentFixture<PostRepliesUpdateComponent>;
    let service: PostRepliesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostRepliesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostRepliesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostRepliesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostRepliesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostReplies(123);
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
        const entity = new PostReplies();
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
