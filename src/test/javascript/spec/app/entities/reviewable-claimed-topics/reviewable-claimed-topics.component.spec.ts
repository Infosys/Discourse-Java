import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableClaimedTopicsComponent } from 'app/entities/reviewable-claimed-topics/reviewable-claimed-topics.component';
import { ReviewableClaimedTopicsService } from 'app/entities/reviewable-claimed-topics/reviewable-claimed-topics.service';
import { ReviewableClaimedTopics } from 'app/shared/model/reviewable-claimed-topics.model';

describe('Component Tests', () => {
  describe('ReviewableClaimedTopics Management Component', () => {
    let comp: ReviewableClaimedTopicsComponent;
    let fixture: ComponentFixture<ReviewableClaimedTopicsComponent>;
    let service: ReviewableClaimedTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableClaimedTopicsComponent],
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
        .overrideTemplate(ReviewableClaimedTopicsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewableClaimedTopicsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewableClaimedTopicsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReviewableClaimedTopics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reviewableClaimedTopics && comp.reviewableClaimedTopics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReviewableClaimedTopics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reviewableClaimedTopics && comp.reviewableClaimedTopics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
