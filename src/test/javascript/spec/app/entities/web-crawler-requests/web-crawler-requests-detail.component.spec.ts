import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { WebCrawlerRequestsDetailComponent } from 'app/entities/web-crawler-requests/web-crawler-requests-detail.component';
import { WebCrawlerRequests } from 'app/shared/model/web-crawler-requests.model';

describe('Component Tests', () => {
  describe('WebCrawlerRequests Management Detail Component', () => {
    let comp: WebCrawlerRequestsDetailComponent;
    let fixture: ComponentFixture<WebCrawlerRequestsDetailComponent>;
    const route = ({ data: of({ webCrawlerRequests: new WebCrawlerRequests(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [WebCrawlerRequestsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WebCrawlerRequestsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WebCrawlerRequestsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load webCrawlerRequests on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.webCrawlerRequests).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
