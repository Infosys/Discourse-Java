import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PushSubscriptionsUpdateComponent } from 'app/entities/push-subscriptions/push-subscriptions-update.component';
import { PushSubscriptionsService } from 'app/entities/push-subscriptions/push-subscriptions.service';
import { PushSubscriptions } from 'app/shared/model/push-subscriptions.model';

describe('Component Tests', () => {
  describe('PushSubscriptions Management Update Component', () => {
    let comp: PushSubscriptionsUpdateComponent;
    let fixture: ComponentFixture<PushSubscriptionsUpdateComponent>;
    let service: PushSubscriptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PushSubscriptionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PushSubscriptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PushSubscriptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PushSubscriptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PushSubscriptions(123);
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
        const entity = new PushSubscriptions();
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
