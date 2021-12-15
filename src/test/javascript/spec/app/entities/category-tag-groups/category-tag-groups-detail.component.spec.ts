import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagGroupsDetailComponent } from 'app/entities/category-tag-groups/category-tag-groups-detail.component';
import { CategoryTagGroups } from 'app/shared/model/category-tag-groups.model';

describe('Component Tests', () => {
  describe('CategoryTagGroups Management Detail Component', () => {
    let comp: CategoryTagGroupsDetailComponent;
    let fixture: ComponentFixture<CategoryTagGroupsDetailComponent>;
    const route = ({ data: of({ categoryTagGroups: new CategoryTagGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryTagGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryTagGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryTagGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryTagGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
