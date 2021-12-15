import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { UnsubscribeKeysComponent } from 'app/entities/unsubscribe-keys/unsubscribe-keys.component';
import { UnsubscribeKeysService } from 'app/entities/unsubscribe-keys/unsubscribe-keys.service';
import { UnsubscribeKeys } from 'app/shared/model/unsubscribe-keys.model';

describe('Component Tests', () => {
  describe('UnsubscribeKeys Management Component', () => {
    let comp: UnsubscribeKeysComponent;
    let fixture: ComponentFixture<UnsubscribeKeysComponent>;
    let service: UnsubscribeKeysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UnsubscribeKeysComponent],
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
        .overrideTemplate(UnsubscribeKeysComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnsubscribeKeysComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnsubscribeKeysService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UnsubscribeKeys(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.unsubscribeKeys && comp.unsubscribeKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UnsubscribeKeys(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.unsubscribeKeys && comp.unsubscribeKeys[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
