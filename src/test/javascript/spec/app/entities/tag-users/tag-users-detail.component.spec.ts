import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagUsersDetailComponent } from 'app/entities/tag-users/tag-users-detail.component';
import { TagUsers } from 'app/shared/model/tag-users.model';

describe('Component Tests', () => {
  describe('TagUsers Management Detail Component', () => {
    let comp: TagUsersDetailComponent;
    let fixture: ComponentFixture<TagUsersDetailComponent>;
    const route = ({ data: of({ tagUsers: new TagUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TagUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tagUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
