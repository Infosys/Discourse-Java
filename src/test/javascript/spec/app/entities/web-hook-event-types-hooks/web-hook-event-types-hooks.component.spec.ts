import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { WebHookEventTypesHooksComponent } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks.component';
import { WebHookEventTypesHooksService } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks.service';
import { WebHookEventTypesHooks } from 'app/shared/model/web-hook-event-types-hooks.model';

describe('Component Tests', () => {
  describe('WebHookEventTypesHooks Management Component', () => {
    let comp: WebHookEventTypesHooksComponent;
    let fixture: ComponentFixture<WebHookEventTypesHooksComponent>;
    let service: WebHookEventTypesHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebHookEventTypesHooksComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(WebHookEventTypesHooksComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebHookEventTypesHooksComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebHookEventTypesHooksService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WebHookEventTypesHooks(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.webHookEventTypesHooks && comp.webHookEventTypesHooks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WebHookEventTypesHooks(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.webHookEventTypesHooks && comp.webHookEventTypesHooks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
