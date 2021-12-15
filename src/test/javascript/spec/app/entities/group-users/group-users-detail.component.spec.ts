import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupUsersDetailComponent } from 'app/entities/group-users/group-users-detail.component';
import { GroupUsers } from 'app/shared/model/group-users.model';

describe('Component Tests', () => {
  describe('GroupUsers Management Detail Component', () => {
    let comp: GroupUsersDetailComponent;
    let fixture: ComponentFixture<GroupUsersDetailComponent>;
    const route = ({ data: of({ groupUsers: new GroupUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
