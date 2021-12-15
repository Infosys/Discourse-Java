import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserNotificationSchedulesDetailComponent } from 'app/entities/user-notification-schedules/user-notification-schedules-detail.component';
import { UserNotificationSchedules } from 'app/shared/model/user-notification-schedules.model';

describe('Component Tests', () => {
  describe('UserNotificationSchedules Management Detail Component', () => {
    let comp: UserNotificationSchedulesDetailComponent;
    let fixture: ComponentFixture<UserNotificationSchedulesDetailComponent>;
    const route = ({ data: of({ userNotificationSchedules: new UserNotificationSchedules(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserNotificationSchedulesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserNotificationSchedulesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserNotificationSchedulesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userNotificationSchedules on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userNotificationSchedules).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
