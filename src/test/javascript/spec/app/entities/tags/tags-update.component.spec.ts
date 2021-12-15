import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagsUpdateComponent } from 'app/entities/tags/tags-update.component';
import { TagsService } from 'app/entities/tags/tags.service';
import { Tags } from 'app/shared/model/tags.model';

describe('Component Tests', () => {
  describe('Tags Management Update Component', () => {
    let comp: TagsUpdateComponent;
    let fixture: ComponentFixture<TagsUpdateComponent>;
    let service: TagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tags(123);
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
        const entity = new Tags();
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
