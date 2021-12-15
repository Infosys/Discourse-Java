import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagGroupsDetailComponent } from 'app/entities/tag-groups/tag-groups-detail.component';
import { TagGroups } from 'app/shared/model/tag-groups.model';

describe('Component Tests', () => {
  describe('TagGroups Management Detail Component', () => {
    let comp: TagGroupsDetailComponent;
    let fixture: ComponentFixture<TagGroupsDetailComponent>;
    const route = ({ data: of({ tagGroups: new TagGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
