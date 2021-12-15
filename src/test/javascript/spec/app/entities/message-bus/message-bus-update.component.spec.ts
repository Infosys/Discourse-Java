import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { MessageBusUpdateComponent } from 'app/entities/message-bus/message-bus-update.component';
import { MessageBusService } from 'app/entities/message-bus/message-bus.service';
import { MessageBus } from 'app/shared/model/message-bus.model';

describe('Component Tests', () => {
  describe('MessageBus Management Update Component', () => {
    let comp: MessageBusUpdateComponent;
    let fixture: ComponentFixture<MessageBusUpdateComponent>;
    let service: MessageBusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [MessageBusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MessageBusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MessageBusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MessageBusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MessageBus(123);
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
        const entity = new MessageBus();
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
