import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { AllowedPmUsersDetailComponent } from 'app/entities/allowed-pm-users/allowed-pm-users-detail.component';
import { AllowedPmUsers } from 'app/shared/model/allowed-pm-users.model';

describe('Component Tests', () => {
  describe('AllowedPmUsers Management Detail Component', () => {
    let comp: AllowedPmUsersDetailComponent;
    let fixture: ComponentFixture<AllowedPmUsersDetailComponent>;
    const route = ({ data: of({ allowedPmUsers: new AllowedPmUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AllowedPmUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AllowedPmUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllowedPmUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load allowedPmUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allowedPmUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
