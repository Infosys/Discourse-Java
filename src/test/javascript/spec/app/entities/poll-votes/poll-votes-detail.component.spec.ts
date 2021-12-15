import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollVotesDetailComponent } from 'app/entities/poll-votes/poll-votes-detail.component';
import { PollVotes } from 'app/shared/model/poll-votes.model';

describe('Component Tests', () => {
  describe('PollVotes Management Detail Component', () => {
    let comp: PollVotesDetailComponent;
    let fixture: ComponentFixture<PollVotesDetailComponent>;
    const route = ({ data: of({ pollVotes: new PollVotes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollVotesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PollVotesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PollVotesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pollVotes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pollVotes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
