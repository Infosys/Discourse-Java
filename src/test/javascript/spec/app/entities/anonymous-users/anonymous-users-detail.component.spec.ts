import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { AnonymousUsersDetailComponent } from 'app/entities/anonymous-users/anonymous-users-detail.component';
import { AnonymousUsers } from 'app/shared/model/anonymous-users.model';

describe('Component Tests', () => {
  describe('AnonymousUsers Management Detail Component', () => {
    let comp: AnonymousUsersDetailComponent;
    let fixture: ComponentFixture<AnonymousUsersDetailComponent>;
    const route = ({ data: of({ anonymousUsers: new AnonymousUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AnonymousUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnonymousUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnonymousUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load anonymousUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anonymousUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
