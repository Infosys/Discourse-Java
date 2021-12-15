import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostActionTypesDetailComponent } from 'app/entities/post-action-types/post-action-types-detail.component';
import { PostActionTypes } from 'app/shared/model/post-action-types.model';

describe('Component Tests', () => {
  describe('PostActionTypes Management Detail Component', () => {
    let comp: PostActionTypesDetailComponent;
    let fixture: ComponentFixture<PostActionTypesDetailComponent>;
    const route = ({ data: of({ postActionTypes: new PostActionTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostActionTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostActionTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostActionTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postActionTypes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postActionTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
