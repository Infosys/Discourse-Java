import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAvatarsDetailComponent } from 'app/entities/user-avatars/user-avatars-detail.component';
import { UserAvatars } from 'app/shared/model/user-avatars.model';

describe('Component Tests', () => {
  describe('UserAvatars Management Detail Component', () => {
    let comp: UserAvatarsDetailComponent;
    let fixture: ComponentFixture<UserAvatarsDetailComponent>;
    const route = ({ data: of({ userAvatars: new UserAvatars(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAvatarsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAvatarsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAvatarsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAvatars on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAvatars).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
