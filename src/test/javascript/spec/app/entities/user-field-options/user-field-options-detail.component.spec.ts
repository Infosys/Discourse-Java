import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserFieldOptionsDetailComponent } from 'app/entities/user-field-options/user-field-options-detail.component';
import { UserFieldOptions } from 'app/shared/model/user-field-options.model';

describe('Component Tests', () => {
  describe('UserFieldOptions Management Detail Component', () => {
    let comp: UserFieldOptionsDetailComponent;
    let fixture: ComponentFixture<UserFieldOptionsDetailComponent>;
    const route = ({ data: of({ userFieldOptions: new UserFieldOptions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserFieldOptionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserFieldOptionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFieldOptionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userFieldOptions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userFieldOptions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
