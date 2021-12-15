import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserActionsDetailComponent } from 'app/entities/user-actions/user-actions-detail.component';
import { UserActions } from 'app/shared/model/user-actions.model';

describe('Component Tests', () => {
  describe('UserActions Management Detail Component', () => {
    let comp: UserActionsDetailComponent;
    let fixture: ComponentFixture<UserActionsDetailComponent>;
    const route = ({ data: of({ userActions: new UserActions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserActionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserActionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserActionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userActions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userActions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
