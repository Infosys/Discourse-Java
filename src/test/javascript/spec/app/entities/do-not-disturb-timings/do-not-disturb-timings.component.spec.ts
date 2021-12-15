import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { DoNotDisturbTimingsComponent } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings.component';
import { DoNotDisturbTimingsService } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings.service';
import { DoNotDisturbTimings } from 'app/shared/model/do-not-disturb-timings.model';

describe('Component Tests', () => {
  describe('DoNotDisturbTimings Management Component', () => {
    let comp: DoNotDisturbTimingsComponent;
    let fixture: ComponentFixture<DoNotDisturbTimingsComponent>;
    let service: DoNotDisturbTimingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DoNotDisturbTimingsComponent],
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
        .overrideTemplate(DoNotDisturbTimingsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoNotDisturbTimingsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoNotDisturbTimingsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoNotDisturbTimings(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doNotDisturbTimings && comp.doNotDisturbTimings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoNotDisturbTimings(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doNotDisturbTimings && comp.doNotDisturbTimings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
