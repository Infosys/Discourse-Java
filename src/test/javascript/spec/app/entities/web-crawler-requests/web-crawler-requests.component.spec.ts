import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { WebCrawlerRequestsComponent } from 'app/entities/web-crawler-requests/web-crawler-requests.component';
import { WebCrawlerRequestsService } from 'app/entities/web-crawler-requests/web-crawler-requests.service';
import { WebCrawlerRequests } from 'app/shared/model/web-crawler-requests.model';

describe('Component Tests', () => {
  describe('WebCrawlerRequests Management Component', () => {
    let comp: WebCrawlerRequestsComponent;
    let fixture: ComponentFixture<WebCrawlerRequestsComponent>;
    let service: WebCrawlerRequestsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebCrawlerRequestsComponent],
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
        .overrideTemplate(WebCrawlerRequestsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WebCrawlerRequestsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WebCrawlerRequestsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WebCrawlerRequests(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.webCrawlerRequests && comp.webCrawlerRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WebCrawlerRequests(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.webCrawlerRequests && comp.webCrawlerRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
