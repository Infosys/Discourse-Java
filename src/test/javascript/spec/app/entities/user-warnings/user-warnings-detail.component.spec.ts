import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserWarningsDetailComponent } from 'app/entities/user-warnings/user-warnings-detail.component';
import { UserWarnings } from 'app/shared/model/user-warnings.model';

describe('Component Tests', () => {
  describe('UserWarnings Management Detail Component', () => {
    let comp: UserWarningsDetailComponent;
    let fixture: ComponentFixture<UserWarningsDetailComponent>;
    const route = ({ data: of({ userWarnings: new UserWarnings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserWarningsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserWarningsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserWarningsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userWarnings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userWarnings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
