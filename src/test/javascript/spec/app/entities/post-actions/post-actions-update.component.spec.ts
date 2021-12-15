import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostActionsUpdateComponent } from 'app/entities/post-actions/post-actions-update.component';
import { PostActionsService } from 'app/entities/post-actions/post-actions.service';
import { PostActions } from 'app/shared/model/post-actions.model';

describe('Component Tests', () => {
  describe('PostActions Management Update Component', () => {
    let comp: PostActionsUpdateComponent;
    let fixture: ComponentFixture<PostActionsUpdateComponent>;
    let service: PostActionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostActionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostActionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostActionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostActionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostActions(123);
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
        const entity = new PostActions();
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
