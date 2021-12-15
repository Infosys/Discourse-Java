import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostCustomFieldsUpdateComponent } from 'app/entities/post-custom-fields/post-custom-fields-update.component';
import { PostCustomFieldsService } from 'app/entities/post-custom-fields/post-custom-fields.service';
import { PostCustomFields } from 'app/shared/model/post-custom-fields.model';

describe('Component Tests', () => {
  describe('PostCustomFields Management Update Component', () => {
    let comp: PostCustomFieldsUpdateComponent;
    let fixture: ComponentFixture<PostCustomFieldsUpdateComponent>;
    let service: PostCustomFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostCustomFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PostCustomFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostCustomFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostCustomFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostCustomFields(123);
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
        const entity = new PostCustomFields();
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
