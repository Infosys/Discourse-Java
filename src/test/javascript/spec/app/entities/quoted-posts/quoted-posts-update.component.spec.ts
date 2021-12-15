import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { QuotedPostsUpdateComponent } from 'app/entities/quoted-posts/quoted-posts-update.component';
import { QuotedPostsService } from 'app/entities/quoted-posts/quoted-posts.service';
import { QuotedPosts } from 'app/shared/model/quoted-posts.model';

describe('Component Tests', () => {
  describe('QuotedPosts Management Update Component', () => {
    let comp: QuotedPostsUpdateComponent;
    let fixture: ComponentFixture<QuotedPostsUpdateComponent>;
    let service: QuotedPostsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [QuotedPostsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuotedPostsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuotedPostsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuotedPostsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuotedPosts(123);
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
        const entity = new QuotedPosts();
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
