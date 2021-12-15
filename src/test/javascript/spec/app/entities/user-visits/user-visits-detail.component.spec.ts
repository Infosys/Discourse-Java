import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserVisitsDetailComponent } from 'app/entities/user-visits/user-visits-detail.component';
import { UserVisits } from 'app/shared/model/user-visits.model';

describe('Component Tests', () => {
  describe('UserVisits Management Detail Component', () => {
    let comp: UserVisitsDetailComponent;
    let fixture: ComponentFixture<UserVisitsDetailComponent>;
    const route = ({ data: of({ userVisits: new UserVisits(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserVisitsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserVisitsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserVisitsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userVisits on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userVisits).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
