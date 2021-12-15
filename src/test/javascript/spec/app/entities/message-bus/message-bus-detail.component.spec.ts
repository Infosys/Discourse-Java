import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { MessageBusDetailComponent } from 'app/entities/message-bus/message-bus-detail.component';
import { MessageBus } from 'app/shared/model/message-bus.model';

describe('Component Tests', () => {
  describe('MessageBus Management Detail Component', () => {
    let comp: MessageBusDetailComponent;
    let fixture: ComponentFixture<MessageBusDetailComponent>;
    const route = ({ data: of({ messageBus: new MessageBus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [MessageBusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MessageBusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MessageBusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load messageBus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.messageBus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
