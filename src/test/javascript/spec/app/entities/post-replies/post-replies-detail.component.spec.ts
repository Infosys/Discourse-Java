import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostRepliesDetailComponent } from 'app/entities/post-replies/post-replies-detail.component';
import { PostReplies } from 'app/shared/model/post-replies.model';

describe('Component Tests', () => {
  describe('PostReplies Management Detail Component', () => {
    let comp: PostRepliesDetailComponent;
    let fixture: ComponentFixture<PostRepliesDetailComponent>;
    const route = ({ data: of({ postReplies: new PostReplies(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostRepliesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostRepliesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostRepliesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postReplies on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postReplies).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
