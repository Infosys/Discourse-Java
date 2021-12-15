import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UsersDetailComponent } from 'app/entities/users/users-detail.component';
import { Users } from 'app/shared/model/users.model';

describe('Component Tests', () => {
  describe('Users Management Detail Component', () => {
    let comp: UsersDetailComponent;
    let fixture: ComponentFixture<UsersDetailComponent>;
    const route = ({ data: of({ users: new Users(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load users on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.users).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
