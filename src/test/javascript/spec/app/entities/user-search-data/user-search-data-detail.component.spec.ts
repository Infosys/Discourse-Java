import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSearchDataDetailComponent } from 'app/entities/user-search-data/user-search-data-detail.component';
import { UserSearchData } from 'app/shared/model/user-search-data.model';

describe('Component Tests', () => {
  describe('UserSearchData Management Detail Component', () => {
    let comp: UserSearchDataDetailComponent;
    let fixture: ComponentFixture<UserSearchDataDetailComponent>;
    const route = ({ data: of({ userSearchData: new UserSearchData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSearchDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserSearchDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserSearchDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userSearchData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userSearchData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
