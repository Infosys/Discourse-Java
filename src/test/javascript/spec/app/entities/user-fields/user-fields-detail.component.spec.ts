import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserFieldsDetailComponent } from 'app/entities/user-fields/user-fields-detail.component';
import { UserFields } from 'app/shared/model/user-fields.model';

describe('Component Tests', () => {
  describe('UserFields Management Detail Component', () => {
    let comp: UserFieldsDetailComponent;
    let fixture: ComponentFixture<UserFieldsDetailComponent>;
    const route = ({ data: of({ userFields: new UserFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
