import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicTimersUpdateComponent } from 'app/entities/topic-timers/topic-timers-update.component';
import { TopicTimersService } from 'app/entities/topic-timers/topic-timers.service';
import { TopicTimers } from 'app/shared/model/topic-timers.model';

describe('Component Tests', () => {
  describe('TopicTimers Management Update Component', () => {
    let comp: TopicTimersUpdateComponent;
    let fixture: ComponentFixture<TopicTimersUpdateComponent>;
    let service: TopicTimersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicTimersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicTimersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicTimersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicTimersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicTimers(123);
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
        const entity = new TopicTimers();
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
