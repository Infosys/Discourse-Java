import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostUploadsDetailComponent } from 'app/entities/post-uploads/post-uploads-detail.component';
import { PostUploads } from 'app/shared/model/post-uploads.model';

describe('Component Tests', () => {
  describe('PostUploads Management Detail Component', () => {
    let comp: PostUploadsDetailComponent;
    let fixture: ComponentFixture<PostUploadsDetailComponent>;
    const route = ({ data: of({ postUploads: new PostUploads(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostUploadsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostUploadsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostUploadsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postUploads on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postUploads).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
