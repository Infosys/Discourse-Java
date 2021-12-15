import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicTimersDetailComponent } from 'app/entities/topic-timers/topic-timers-detail.component';
import { TopicTimers } from 'app/shared/model/topic-timers.model';

describe('Component Tests', () => {
  describe('TopicTimers Management Detail Component', () => {
    let comp: TopicTimersDetailComponent;
    let fixture: ComponentFixture<TopicTimersDetailComponent>;
    const route = ({ data: of({ topicTimers: new TopicTimers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicTimersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicTimersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicTimersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicTimers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicTimers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
