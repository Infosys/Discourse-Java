import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ReviewableClaimedTopicsDetailComponent } from 'app/entities/reviewable-claimed-topics/reviewable-claimed-topics-detail.component';
import { ReviewableClaimedTopics } from 'app/shared/model/reviewable-claimed-topics.model';

describe('Component Tests', () => {
  describe('ReviewableClaimedTopics Management Detail Component', () => {
    let comp: ReviewableClaimedTopicsDetailComponent;
    let fixture: ComponentFixture<ReviewableClaimedTopicsDetailComponent>;
    const route = ({ data: of({ reviewableClaimedTopics: new ReviewableClaimedTopics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ReviewableClaimedTopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReviewableClaimedTopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewableClaimedTopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reviewableClaimedTopics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reviewableClaimedTopics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
