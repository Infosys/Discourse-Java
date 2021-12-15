import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserOptionsDetailComponent } from 'app/entities/user-options/user-options-detail.component';
import { UserOptions } from 'app/shared/model/user-options.model';

describe('Component Tests', () => {
  describe('UserOptions Management Detail Component', () => {
    let comp: UserOptionsDetailComponent;
    let fixture: ComponentFixture<UserOptionsDetailComponent>;
    const route = ({ data: of({ userOptions: new UserOptions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserOptionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserOptionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserOptionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userOptions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userOptions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
