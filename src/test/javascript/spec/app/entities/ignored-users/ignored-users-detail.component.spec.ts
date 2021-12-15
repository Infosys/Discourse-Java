import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IgnoredUsersDetailComponent } from 'app/entities/ignored-users/ignored-users-detail.component';
import { IgnoredUsers } from 'app/shared/model/ignored-users.model';

describe('Component Tests', () => {
  describe('IgnoredUsers Management Detail Component', () => {
    let comp: IgnoredUsersDetailComponent;
    let fixture: ComponentFixture<IgnoredUsersDetailComponent>;
    const route = ({ data: of({ ignoredUsers: new IgnoredUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IgnoredUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IgnoredUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IgnoredUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ignoredUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ignoredUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
