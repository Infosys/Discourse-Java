import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserProfileViewsDetailComponent } from 'app/entities/user-profile-views/user-profile-views-detail.component';
import { UserProfileViews } from 'app/shared/model/user-profile-views.model';

describe('Component Tests', () => {
  describe('UserProfileViews Management Detail Component', () => {
    let comp: UserProfileViewsDetailComponent;
    let fixture: ComponentFixture<UserProfileViewsDetailComponent>;
    const route = ({ data: of({ userProfileViews: new UserProfileViews(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserProfileViewsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserProfileViewsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileViewsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userProfileViews on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProfileViews).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
