import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedIpAddressesComponent } from 'app/entities/screened-ip-addresses/screened-ip-addresses.component';
import { ScreenedIpAddressesService } from 'app/entities/screened-ip-addresses/screened-ip-addresses.service';
import { ScreenedIpAddresses } from 'app/shared/model/screened-ip-addresses.model';

describe('Component Tests', () => {
  describe('ScreenedIpAddresses Management Component', () => {
    let comp: ScreenedIpAddressesComponent;
    let fixture: ComponentFixture<ScreenedIpAddressesComponent>;
    let service: ScreenedIpAddressesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedIpAddressesComponent],
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
        .overrideTemplate(ScreenedIpAddressesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScreenedIpAddressesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScreenedIpAddressesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ScreenedIpAddresses(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.screenedIpAddresses && comp.screenedIpAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ScreenedIpAddresses(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.screenedIpAddresses && comp.screenedIpAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
