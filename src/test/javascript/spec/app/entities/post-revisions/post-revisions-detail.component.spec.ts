import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostRevisionsDetailComponent } from 'app/entities/post-revisions/post-revisions-detail.component';
import { PostRevisions } from 'app/shared/model/post-revisions.model';

describe('Component Tests', () => {
  describe('PostRevisions Management Detail Component', () => {
    let comp: PostRevisionsDetailComponent;
    let fixture: ComponentFixture<PostRevisionsDetailComponent>;
    const route = ({ data: of({ postRevisions: new PostRevisions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostRevisionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostRevisionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostRevisionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postRevisions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postRevisions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
