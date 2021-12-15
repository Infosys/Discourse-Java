import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { ThemeTranslationOverridesComponent } from 'app/entities/theme-translation-overrides/theme-translation-overrides.component';
import { ThemeTranslationOverridesService } from 'app/entities/theme-translation-overrides/theme-translation-overrides.service';
import { ThemeTranslationOverrides } from 'app/shared/model/theme-translation-overrides.model';

describe('Component Tests', () => {
  describe('ThemeTranslationOverrides Management Component', () => {
    let comp: ThemeTranslationOverridesComponent;
    let fixture: ComponentFixture<ThemeTranslationOverridesComponent>;
    let service: ThemeTranslationOverridesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ThemeTranslationOverridesComponent],
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
        .overrideTemplate(ThemeTranslationOverridesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeTranslationOverridesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeTranslationOverridesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ThemeTranslationOverrides(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.themeTranslationOverrides && comp.themeTranslationOverrides[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ThemeTranslationOverrides(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.themeTranslationOverrides && comp.themeTranslationOverrides[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
