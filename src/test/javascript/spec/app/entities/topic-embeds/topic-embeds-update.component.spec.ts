import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicEmbedsUpdateComponent } from 'app/entities/topic-embeds/topic-embeds-update.component';
import { TopicEmbedsService } from 'app/entities/topic-embeds/topic-embeds.service';
import { TopicEmbeds } from 'app/shared/model/topic-embeds.model';

describe('Component Tests', () => {
  describe('TopicEmbeds Management Update Component', () => {
    let comp: TopicEmbedsUpdateComponent;
    let fixture: ComponentFixture<TopicEmbedsUpdateComponent>;
    let service: TopicEmbedsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicEmbedsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicEmbedsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicEmbedsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicEmbedsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicEmbeds(123);
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
        const entity = new TopicEmbeds();
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
