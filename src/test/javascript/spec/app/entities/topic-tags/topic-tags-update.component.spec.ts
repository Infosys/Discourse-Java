import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicTagsUpdateComponent } from 'app/entities/topic-tags/topic-tags-update.component';
import { TopicTagsService } from 'app/entities/topic-tags/topic-tags.service';
import { TopicTags } from 'app/shared/model/topic-tags.model';

describe('Component Tests', () => {
  describe('TopicTags Management Update Component', () => {
    let comp: TopicTagsUpdateComponent;
    let fixture: ComponentFixture<TopicTagsUpdateComponent>;
    let service: TopicTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicTagsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicTagsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicTagsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicTagsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicTags(123);
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
        const entity = new TopicTags();
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
