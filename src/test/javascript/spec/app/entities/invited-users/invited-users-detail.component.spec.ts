import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitedUsersDetailComponent } from 'app/entities/invited-users/invited-users-detail.component';
import { InvitedUsers } from 'app/shared/model/invited-users.model';

describe('Component Tests', () => {
  describe('InvitedUsers Management Detail Component', () => {
    let comp: InvitedUsersDetailComponent;
    let fixture: ComponentFixture<InvitedUsersDetailComponent>;
    const route = ({ data: of({ invitedUsers: new InvitedUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitedUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvitedUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitedUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invitedUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitedUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
