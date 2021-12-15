import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserExportsDetailComponent } from 'app/entities/user-exports/user-exports-detail.component';
import { UserExports } from 'app/shared/model/user-exports.model';

describe('Component Tests', () => {
  describe('UserExports Management Detail Component', () => {
    let comp: UserExportsDetailComponent;
    let fixture: ComponentFixture<UserExportsDetailComponent>;
    const route = ({ data: of({ userExports: new UserExports(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserExportsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserExportsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExportsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userExports on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userExports).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
