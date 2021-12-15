import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicThumbnailsUpdateComponent } from 'app/entities/topic-thumbnails/topic-thumbnails-update.component';
import { TopicThumbnailsService } from 'app/entities/topic-thumbnails/topic-thumbnails.service';
import { TopicThumbnails } from 'app/shared/model/topic-thumbnails.model';

describe('Component Tests', () => {
  describe('TopicThumbnails Management Update Component', () => {
    let comp: TopicThumbnailsUpdateComponent;
    let fixture: ComponentFixture<TopicThumbnailsUpdateComponent>;
    let service: TopicThumbnailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicThumbnailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicThumbnailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicThumbnailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicThumbnailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicThumbnails(123);
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
        const entity = new TopicThumbnails();
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
