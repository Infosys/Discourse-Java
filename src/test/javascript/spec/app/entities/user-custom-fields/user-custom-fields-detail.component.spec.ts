import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserCustomFieldsDetailComponent } from 'app/entities/user-custom-fields/user-custom-fields-detail.component';
import { UserCustomFields } from 'app/shared/model/user-custom-fields.model';

describe('Component Tests', () => {
  describe('UserCustomFields Management Detail Component', () => {
    let comp: UserCustomFieldsDetailComponent;
    let fixture: ComponentFixture<UserCustomFieldsDetailComponent>;
    const route = ({ data: of({ userCustomFields: new UserCustomFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserCustomFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserCustomFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserCustomFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userCustomFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userCustomFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
