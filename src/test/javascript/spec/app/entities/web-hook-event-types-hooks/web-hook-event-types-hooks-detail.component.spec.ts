import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebHookEventTypesHooksDetailComponent } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks-detail.component';
import { WebHookEventTypesHooks } from 'app/shared/model/web-hook-event-types-hooks.model';

describe('Component Tests', () => {
  describe('WebHookEventTypesHooks Management Detail Component', () => {
    let comp: WebHookEventTypesHooksDetailComponent;
    let fixture: ComponentFixture<WebHookEventTypesHooksDetailComponent>;
    const route = ({ data: of({ webHookEventTypesHooks: new WebHookEventTypesHooks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHookEventTypesHooksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WebHookEventTypesHooksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WebHookEventTypesHooksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load webHookEventTypesHooks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.webHookEventTypesHooks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
