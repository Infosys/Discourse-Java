import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserBadgesDetailComponent } from 'app/entities/user-badges/user-badges-detail.component';
import { UserBadges } from 'app/shared/model/user-badges.model';

describe('Component Tests', () => {
  describe('UserBadges Management Detail Component', () => {
    let comp: UserBadgesDetailComponent;
    let fixture: ComponentFixture<UserBadgesDetailComponent>;
    const route = ({ data: of({ userBadges: new UserBadges(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserBadgesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserBadgesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserBadgesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userBadges on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userBadges).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
