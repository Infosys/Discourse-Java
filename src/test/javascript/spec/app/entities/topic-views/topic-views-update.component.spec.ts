import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicViewsUpdateComponent } from 'app/entities/topic-views/topic-views-update.component';
import { TopicViewsService } from 'app/entities/topic-views/topic-views.service';
import { TopicViews } from 'app/shared/model/topic-views.model';

describe('Component Tests', () => {
  describe('TopicViews Management Update Component', () => {
    let comp: TopicViewsUpdateComponent;
    let fixture: ComponentFixture<TopicViewsUpdateComponent>;
    let service: TopicViewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicViewsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicViewsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicViewsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicViewsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicViews(123);
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
        const entity = new TopicViews();
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
