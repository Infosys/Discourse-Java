import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PushSubscriptionsDetailComponent } from 'app/entities/push-subscriptions/push-subscriptions-detail.component';
import { PushSubscriptions } from 'app/shared/model/push-subscriptions.model';

describe('Component Tests', () => {
  describe('PushSubscriptions Management Detail Component', () => {
    let comp: PushSubscriptionsDetailComponent;
    let fixture: ComponentFixture<PushSubscriptionsDetailComponent>;
    const route = ({ data: of({ pushSubscriptions: new PushSubscriptions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PushSubscriptionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PushSubscriptionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PushSubscriptionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pushSubscriptions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pushSubscriptions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
