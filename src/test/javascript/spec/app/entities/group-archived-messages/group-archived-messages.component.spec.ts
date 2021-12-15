import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { GroupArchivedMessagesComponent } from 'app/entities/group-archived-messages/group-archived-messages.component';
import { GroupArchivedMessagesService } from 'app/entities/group-archived-messages/group-archived-messages.service';
import { GroupArchivedMessages } from 'app/shared/model/group-archived-messages.model';

describe('Component Tests', () => {
  describe('GroupArchivedMessages Management Component', () => {
    let comp: GroupArchivedMessagesComponent;
    let fixture: ComponentFixture<GroupArchivedMessagesComponent>;
    let service: GroupArchivedMessagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupArchivedMessagesComponent],
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
        .overrideTemplate(GroupArchivedMessagesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupArchivedMessagesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupArchivedMessagesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupArchivedMessages(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupArchivedMessages && comp.groupArchivedMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupArchivedMessages(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupArchivedMessages && comp.groupArchivedMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
