import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHookEventsUpdateComponent } from 'app/entities/web-hook-events/web-hook-events-update.component';
import { WebHookEventsService } from 'app/entities/web-hook-events/web-hook-events.service';
import { WebHookEvents } from 'app/shared/model/web-hook-events.model';

describe('Component Tests', () => {
  describe('WebHookEvents Management Update Component', () => {
    let comp: WebHookEventsUpdateComponent;
    let fixture: ComponentFixture<WebHookEventsUpdateComponent>;
    let service: WebHookEventsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHookEventsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WebHookEventsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebHookEventsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebHookEventsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WebHookEvents(123);
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
        const entity = new WebHookEvents();
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
