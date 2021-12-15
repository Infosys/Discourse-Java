import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupTagNotificationDefaultsDetailComponent } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults-detail.component';
import { GroupTagNotificationDefaults } from 'app/shared/model/group-tag-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupTagNotificationDefaults Management Detail Component', () => {
    let comp: GroupTagNotificationDefaultsDetailComponent;
    let fixture: ComponentFixture<GroupTagNotificationDefaultsDetailComponent>;
    const route = ({ data: of({ groupTagNotificationDefaults: new GroupTagNotificationDefaults(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupTagNotificationDefaultsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupTagNotificationDefaultsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupTagNotificationDefaultsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupTagNotificationDefaults on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupTagNotificationDefaults).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
