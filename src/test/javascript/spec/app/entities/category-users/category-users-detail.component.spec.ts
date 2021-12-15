import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryUsersDetailComponent } from 'app/entities/category-users/category-users-detail.component';
import { CategoryUsers } from 'app/shared/model/category-users.model';

describe('Component Tests', () => {
  describe('CategoryUsers Management Detail Component', () => {
    let comp: CategoryUsersDetailComponent;
    let fixture: ComponentFixture<CategoryUsersDetailComponent>;
    const route = ({ data: of({ categoryUsers: new CategoryUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
