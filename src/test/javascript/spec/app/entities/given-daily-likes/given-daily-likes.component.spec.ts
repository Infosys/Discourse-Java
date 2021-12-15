import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { GivenDailyLikesComponent } from 'app/entities/given-daily-likes/given-daily-likes.component';
import { GivenDailyLikesService } from 'app/entities/given-daily-likes/given-daily-likes.service';
import { GivenDailyLikes } from 'app/shared/model/given-daily-likes.model';

describe('Component Tests', () => {
  describe('GivenDailyLikes Management Component', () => {
    let comp: GivenDailyLikesComponent;
    let fixture: ComponentFixture<GivenDailyLikesComponent>;
    let service: GivenDailyLikesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GivenDailyLikesComponent],
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
        .overrideTemplate(GivenDailyLikesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GivenDailyLikesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GivenDailyLikesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GivenDailyLikes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.givenDailyLikes && comp.givenDailyLikes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GivenDailyLikes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.givenDailyLikes && comp.givenDailyLikes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
