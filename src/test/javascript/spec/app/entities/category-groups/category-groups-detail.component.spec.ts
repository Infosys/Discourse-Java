import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryGroupsDetailComponent } from 'app/entities/category-groups/category-groups-detail.component';
import { CategoryGroups } from 'app/shared/model/category-groups.model';

describe('Component Tests', () => {
  describe('CategoryGroups Management Detail Component', () => {
    let comp: CategoryGroupsDetailComponent;
    let fixture: ComponentFixture<CategoryGroupsDetailComponent>;
    const route = ({ data: of({ categoryGroups: new CategoryGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
