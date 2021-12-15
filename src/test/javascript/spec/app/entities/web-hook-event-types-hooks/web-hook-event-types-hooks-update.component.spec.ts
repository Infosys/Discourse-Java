import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHookEventTypesHooksUpdateComponent } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks-update.component';
import { WebHookEventTypesHooksService } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks.service';
import { WebHookEventTypesHooks } from 'app/shared/model/web-hook-event-types-hooks.model';

describe('Component Tests', () => {
  describe('WebHookEventTypesHooks Management Update Component', () => {
    let comp: WebHookEventTypesHooksUpdateComponent;
    let fixture: ComponentFixture<WebHookEventTypesHooksUpdateComponent>;
    let service: WebHookEventTypesHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHookEventTypesHooksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WebHookEventTypesHooksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebHookEventTypesHooksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebHookEventTypesHooksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WebHookEventTypesHooks(123);
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
        const entity = new WebHookEventTypesHooks();
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
