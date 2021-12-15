import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagGroupMembershipsDetailComponent } from 'app/entities/tag-group-memberships/tag-group-memberships-detail.component';
import { TagGroupMemberships } from 'app/shared/model/tag-group-memberships.model';

describe('Component Tests', () => {
  describe('TagGroupMemberships Management Detail Component', () => {
    let comp: TagGroupMembershipsDetailComponent;
    let fixture: ComponentFixture<TagGroupMembershipsDetailComponent>;
    const route = ({ data: of({ tagGroupMemberships: new TagGroupMemberships(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagGroupMembershipsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagGroupMembershipsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagGroupMembershipsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagGroupMemberships on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagGroupMemberships).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
