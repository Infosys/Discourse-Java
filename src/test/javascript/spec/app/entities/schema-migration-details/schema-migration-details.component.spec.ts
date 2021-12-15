import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { SchemaMigrationDetailsComponent } from 'app/entities/schema-migration-details/schema-migration-details.component';
import { SchemaMigrationDetailsService } from 'app/entities/schema-migration-details/schema-migration-details.service';
import { SchemaMigrationDetails } from 'app/shared/model/schema-migration-details.model';

describe('Component Tests', () => {
  describe('SchemaMigrationDetails Management Component', () => {
    let comp: SchemaMigrationDetailsComponent;
    let fixture: ComponentFixture<SchemaMigrationDetailsComponent>;
    let service: SchemaMigrationDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchemaMigrationDetailsComponent],
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
        .overrideTemplate(SchemaMigrationDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemaMigrationDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemaMigrationDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SchemaMigrationDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.schemaMigrationDetails && comp.schemaMigrationDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SchemaMigrationDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.schemaMigrationDetails && comp.schemaMigrationDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
