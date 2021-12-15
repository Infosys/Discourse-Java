import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostActionTypesUpdateComponent } from 'app/entities/post-action-types/post-action-types-update.component';
import { PostActionTypesService } from 'app/entities/post-action-types/post-action-types.service';
import { PostActionTypes } from 'app/shared/model/post-action-types.model';

describe('Component Tests', () => {
  describe('PostActionTypes Management Update Component', () => {
    let comp: PostActionTypesUpdateComponent;
    let fixture: ComponentFixture<PostActionTypesUpdateComponent>;
    let service: PostActionTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostActionTypesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostActionTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostActionTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostActionTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostActionTypes(123);
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
        const entity = new PostActionTypes();
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
