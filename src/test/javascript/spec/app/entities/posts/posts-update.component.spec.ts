import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostsUpdateComponent } from 'app/entities/posts/posts-update.component';
import { PostsService } from 'app/entities/posts/posts.service';
import { Posts } from 'app/shared/model/posts.model';

describe('Component Tests', () => {
  describe('Posts Management Update Component', () => {
    let comp: PostsUpdateComponent;
    let fixture: ComponentFixture<PostsUpdateComponent>;
    let service: PostsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Posts(123);
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
        const entity = new Posts();
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
