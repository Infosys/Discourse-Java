import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHookEventsDetailComponent } from 'app/entities/web-hook-events/web-hook-events-detail.component';
import { WebHookEvents } from 'app/shared/model/web-hook-events.model';

describe('Component Tests', () => {
  describe('WebHookEvents Management Detail Component', () => {
    let comp: WebHookEventsDetailComponent;
    let fixture: ComponentFixture<WebHookEventsDetailComponent>;
    const route = ({ data: of({ webHookEvents: new WebHookEvents(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHookEventsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WebHookEventsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WebHookEventsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load webHookEvents on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.webHookEvents).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
