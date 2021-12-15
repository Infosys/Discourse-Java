import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagsWebHooksUpdateComponent } from 'app/entities/tags-web-hooks/tags-web-hooks-update.component';
import { TagsWebHooksService } from 'app/entities/tags-web-hooks/tags-web-hooks.service';
import { TagsWebHooks } from 'app/shared/model/tags-web-hooks.model';

describe('Component Tests', () => {
  describe('TagsWebHooks Management Update Component', () => {
    let comp: TagsWebHooksUpdateComponent;
    let fixture: ComponentFixture<TagsWebHooksUpdateComponent>;
    let service: TagsWebHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagsWebHooksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagsWebHooksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagsWebHooksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagsWebHooksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagsWebHooks(123);
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
        const entity = new TagsWebHooks();
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
