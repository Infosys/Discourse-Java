import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSecondFactorsDetailComponent } from 'app/entities/user-second-factors/user-second-factors-detail.component';
import { UserSecondFactors } from 'app/shared/model/user-second-factors.model';

describe('Component Tests', () => {
  describe('UserSecondFactors Management Detail Component', () => {
    let comp: UserSecondFactorsDetailComponent;
    let fixture: ComponentFixture<UserSecondFactorsDetailComponent>;
    const route = ({ data: of({ userSecondFactors: new UserSecondFactors(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSecondFactorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserSecondFactorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserSecondFactorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userSecondFactors on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userSecondFactors).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
