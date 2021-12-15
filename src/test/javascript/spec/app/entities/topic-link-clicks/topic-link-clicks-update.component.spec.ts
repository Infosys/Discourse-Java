import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicLinkClicksUpdateComponent } from 'app/entities/topic-link-clicks/topic-link-clicks-update.component';
import { TopicLinkClicksService } from 'app/entities/topic-link-clicks/topic-link-clicks.service';
import { TopicLinkClicks } from 'app/shared/model/topic-link-clicks.model';

describe('Component Tests', () => {
  describe('TopicLinkClicks Management Update Component', () => {
    let comp: TopicLinkClicksUpdateComponent;
    let fixture: ComponentFixture<TopicLinkClicksUpdateComponent>;
    let service: TopicLinkClicksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicLinkClicksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicLinkClicksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicLinkClicksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicLinkClicksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicLinkClicks(123);
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
        const entity = new TopicLinkClicks();
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
