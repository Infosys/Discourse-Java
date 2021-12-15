import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { GroupCategoryNotificationDefaultsComponent } from 'app/entities/group-category-notification-defaults/group-category-notification-defaults.component';
import { GroupCategoryNotificationDefaultsService } from 'app/entities/group-category-notification-defaults/group-category-notification-defaults.service';
import { GroupCategoryNotificationDefaults } from 'app/shared/model/group-category-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupCategoryNotificationDefaults Management Component', () => {
    let comp: GroupCategoryNotificationDefaultsComponent;
    let fixture: ComponentFixture<GroupCategoryNotificationDefaultsComponent>;
    let service: GroupCategoryNotificationDefaultsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCategoryNotificationDefaultsComponent],
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
        .overrideTemplate(GroupCategoryNotificationDefaultsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupCategoryNotificationDefaultsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupCategoryNotificationDefaultsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupCategoryNotificationDefaults(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupCategoryNotificationDefaults && comp.groupCategoryNotificationDefaults[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupCategoryNotificationDefaults(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupCategoryNotificationDefaults && comp.groupCategoryNotificationDefaults[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
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
