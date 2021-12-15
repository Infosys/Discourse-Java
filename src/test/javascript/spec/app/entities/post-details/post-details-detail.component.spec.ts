import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostDetailsDetailComponent } from 'app/entities/post-details/post-details-detail.component';
import { PostDetails } from 'app/shared/model/post-details.model';

describe('Component Tests', () => {
  describe('PostDetails Management Detail Component', () => {
    let comp: PostDetailsDetailComponent;
    let fixture: ComponentFixture<PostDetailsDetailComponent>;
    const route = ({ data: of({ postDetails: new PostDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
