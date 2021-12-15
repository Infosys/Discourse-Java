import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { PollsDetailComponent } from 'app/entities/polls/polls-detail.component';
import { Polls } from 'app/shared/model/polls.model';

describe('Component Tests', () => {
  describe('Polls Management Detail Component', () => {
    let comp: PollsDetailComponent;
    let fixture: ComponentFixture<PollsDetailComponent>;
    const route = ({ data: of({ polls: new Polls(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [PollsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PollsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PollsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load polls on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.polls).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
