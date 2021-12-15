import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableScoresDetailComponent } from 'app/entities/reviewable-scores/reviewable-scores-detail.component';
import { ReviewableScores } from 'app/shared/model/reviewable-scores.model';

describe('Component Tests', () => {
  describe('ReviewableScores Management Detail Component', () => {
    let comp: ReviewableScoresDetailComponent;
    let fixture: ComponentFixture<ReviewableScoresDetailComponent>;
    const route = ({ data: of({ reviewableScores: new ReviewableScores(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableScoresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReviewableScoresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewableScoresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reviewableScores on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reviewableScores).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
