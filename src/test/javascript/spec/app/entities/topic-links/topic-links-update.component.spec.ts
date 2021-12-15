import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicLinksUpdateComponent } from 'app/entities/topic-links/topic-links-update.component';
import { TopicLinksService } from 'app/entities/topic-links/topic-links.service';
import { TopicLinks } from 'app/shared/model/topic-links.model';

describe('Component Tests', () => {
  describe('TopicLinks Management Update Component', () => {
    let comp: TopicLinksUpdateComponent;
    let fixture: ComponentFixture<TopicLinksUpdateComponent>;
    let service: TopicLinksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicLinksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicLinksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicLinksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicLinksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicLinks(123);
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
        const entity = new TopicLinks();
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
