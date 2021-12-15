import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { QuotedPostsDetailComponent } from 'app/entities/quoted-posts/quoted-posts-detail.component';
import { QuotedPosts } from 'app/shared/model/quoted-posts.model';

describe('Component Tests', () => {
  describe('QuotedPosts Management Detail Component', () => {
    let comp: QuotedPostsDetailComponent;
    let fixture: ComponentFixture<QuotedPostsDetailComponent>;
    const route = ({ data: of({ quotedPosts: new QuotedPosts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [QuotedPostsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuotedPostsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuotedPostsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load quotedPosts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.quotedPosts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
