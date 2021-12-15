import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserProfilesDetailComponent } from 'app/entities/user-profiles/user-profiles-detail.component';
import { UserProfiles } from 'app/shared/model/user-profiles.model';

describe('Component Tests', () => {
  describe('UserProfiles Management Detail Component', () => {
    let comp: UserProfilesDetailComponent;
    let fixture: ComponentFixture<UserProfilesDetailComponent>;
    const route = ({ data: of({ userProfiles: new UserProfiles(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserProfilesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserProfilesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfilesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userProfiles on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProfiles).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
