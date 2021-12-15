import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { GroupTagNotificationDefaultsComponent } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults.component';
import { GroupTagNotificationDefaultsService } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults.service';
import { GroupTagNotificationDefaults } from 'app/shared/model/group-tag-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupTagNotificationDefaults Management Component', () => {
    let comp: GroupTagNotificationDefaultsComponent;
    let fixture: ComponentFixture<GroupTagNotificationDefaultsComponent>;
    let service: GroupTagNotificationDefaultsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupTagNotificationDefaultsComponent],
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
        .overrideTemplate(GroupTagNotificationDefaultsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupTagNotificationDefaultsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupTagNotificationDefaultsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupTagNotificationDefaults(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupTagNotificationDefaults && comp.groupTagNotificationDefaults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupTagNotificationDefaults(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupTagNotificationDefaults && comp.groupTagNotificationDefaults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
