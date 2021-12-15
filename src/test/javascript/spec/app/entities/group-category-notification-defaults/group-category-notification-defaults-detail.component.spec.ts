import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupCategoryNotificationDefaultsDetailComponent } from 'app/entities/group-category-notification-defaults/group-category-notification-defaults-detail.component';
import { GroupCategoryNotificationDefaults } from 'app/shared/model/group-category-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupCategoryNotificationDefaults Management Detail Component', () => {
    let comp: GroupCategoryNotificationDefaultsDetailComponent;
    let fixture: ComponentFixture<GroupCategoryNotificationDefaultsDetailComponent>;
    const route = ({
      data: of({ groupCategoryNotificationDefaults: new GroupCategoryNotificationDefaults(123) }),
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCategoryNotificationDefaultsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupCategoryNotificationDefaultsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupCategoryNotificationDefaultsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupCategoryNotificationDefaults on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupCategoryNotificationDefaults).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
