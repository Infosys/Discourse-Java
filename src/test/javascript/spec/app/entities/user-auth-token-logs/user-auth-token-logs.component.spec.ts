import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { UserAuthTokenLogsComponent } from 'app/entities/user-auth-token-logs/user-auth-token-logs.component';
import { UserAuthTokenLogsService } from 'app/entities/user-auth-token-logs/user-auth-token-logs.service';
import { UserAuthTokenLogs } from 'app/shared/model/user-auth-token-logs.model';

describe('Component Tests', () => {
  describe('UserAuthTokenLogs Management Component', () => {
    let comp: UserAuthTokenLogsComponent;
    let fixture: ComponentFixture<UserAuthTokenLogsComponent>;
    let service: UserAuthTokenLogsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAuthTokenLogsComponent],
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
        .overrideTemplate(UserAuthTokenLogsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAuthTokenLogsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAuthTokenLogsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserAuthTokenLogs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userAuthTokenLogs && comp.userAuthTokenLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserAuthTokenLogs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userAuthTokenLogs && comp.userAuthTokenLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
