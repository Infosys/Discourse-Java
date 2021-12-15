import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagGroupPermissionsDetailComponent } from 'app/entities/tag-group-permissions/tag-group-permissions-detail.component';
import { TagGroupPermissions } from 'app/shared/model/tag-group-permissions.model';

describe('Component Tests', () => {
  describe('TagGroupPermissions Management Detail Component', () => {
    let comp: TagGroupPermissionsDetailComponent;
    let fixture: ComponentFixture<TagGroupPermissionsDetailComponent>;
    const route = ({ data: of({ tagGroupPermissions: new TagGroupPermissions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagGroupPermissionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagGroupPermissionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagGroupPermissionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagGroupPermissions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagGroupPermissions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
