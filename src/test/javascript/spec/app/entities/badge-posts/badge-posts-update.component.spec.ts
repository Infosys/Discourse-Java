import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { BadgePostsUpdateComponent } from 'app/entities/badge-posts/badge-posts-update.component';
import { BadgePostsService } from 'app/entities/badge-posts/badge-posts.service';
import { BadgePosts } from 'app/shared/model/badge-posts.model';

describe('Component Tests', () => {
  describe('BadgePosts Management Update Component', () => {
    let comp: BadgePostsUpdateComponent;
    let fixture: ComponentFixture<BadgePostsUpdateComponent>;
    let service: BadgePostsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [BadgePostsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BadgePostsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BadgePostsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadgePostsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BadgePosts(123);
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
        const entity = new BadgePosts();
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
