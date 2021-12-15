import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableHistoriesDetailComponent } from 'app/entities/reviewable-histories/reviewable-histories-detail.component';
import { ReviewableHistories } from 'app/shared/model/reviewable-histories.model';

describe('Component Tests', () => {
  describe('ReviewableHistories Management Detail Component', () => {
    let comp: ReviewableHistoriesDetailComponent;
    let fixture: ComponentFixture<ReviewableHistoriesDetailComponent>;
    const route = ({ data: of({ reviewableHistories: new ReviewableHistories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableHistoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReviewableHistoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewableHistoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reviewableHistories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reviewableHistories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
