import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { Oauth2UserInfosComponent } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos.component';
import { Oauth2UserInfosService } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos.service';
import { Oauth2UserInfos } from 'app/shared/model/oauth-2-user-infos.model';

describe('Component Tests', () => {
  describe('Oauth2UserInfos Management Component', () => {
    let comp: Oauth2UserInfosComponent;
    let fixture: ComponentFixture<Oauth2UserInfosComponent>;
    let service: Oauth2UserInfosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [Oauth2UserInfosComponent],
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
        .overrideTemplate(Oauth2UserInfosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Oauth2UserInfosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Oauth2UserInfosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Oauth2UserInfos(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.oauth2UserInfos && comp.oauth2UserInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Oauth2UserInfos(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.oauth2UserInfos && comp.oauth2UserInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
