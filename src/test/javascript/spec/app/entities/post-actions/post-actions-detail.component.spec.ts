import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostActionsDetailComponent } from 'app/entities/post-actions/post-actions-detail.component';
import { PostActions } from 'app/shared/model/post-actions.model';

describe('Component Tests', () => {
  describe('PostActions Management Detail Component', () => {
    let comp: PostActionsDetailComponent;
    let fixture: ComponentFixture<PostActionsDetailComponent>;
    const route = ({ data: of({ postActions: new PostActions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostActionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostActionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostActionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postActions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postActions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
