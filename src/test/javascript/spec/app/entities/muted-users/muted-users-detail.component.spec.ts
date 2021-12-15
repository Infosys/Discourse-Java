import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { MutedUsersDetailComponent } from 'app/entities/muted-users/muted-users-detail.component';
import { MutedUsers } from 'app/shared/model/muted-users.model';

describe('Component Tests', () => {
  describe('MutedUsers Management Detail Component', () => {
    let comp: MutedUsersDetailComponent;
    let fixture: ComponentFixture<MutedUsersDetailComponent>;
    const route = ({ data: of({ mutedUsers: new MutedUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [MutedUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MutedUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MutedUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mutedUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mutedUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
