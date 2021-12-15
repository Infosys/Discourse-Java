import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostSearchDataDetailComponent } from 'app/entities/post-search-data/post-search-data-detail.component';
import { PostSearchData } from 'app/shared/model/post-search-data.model';

describe('Component Tests', () => {
  describe('PostSearchData Management Detail Component', () => {
    let comp: PostSearchDataDetailComponent;
    let fixture: ComponentFixture<PostSearchDataDetailComponent>;
    const route = ({ data: of({ postSearchData: new PostSearchData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostSearchDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostSearchDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostSearchDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postSearchData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postSearchData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
