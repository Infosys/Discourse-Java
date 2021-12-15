import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PostStatsDetailComponent } from 'app/entities/post-stats/post-stats-detail.component';
import { PostStats } from 'app/shared/model/post-stats.model';

describe('Component Tests', () => {
  describe('PostStats Management Detail Component', () => {
    let comp: PostStatsDetailComponent;
    let fixture: ComponentFixture<PostStatsDetailComponent>;
    const route = ({ data: of({ postStats: new PostStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PostStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
