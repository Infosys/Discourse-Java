import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicLinkClicksDetailComponent } from 'app/entities/topic-link-clicks/topic-link-clicks-detail.component';
import { TopicLinkClicks } from 'app/shared/model/topic-link-clicks.model';

describe('Component Tests', () => {
  describe('TopicLinkClicks Management Detail Component', () => {
    let comp: TopicLinkClicksDetailComponent;
    let fixture: ComponentFixture<TopicLinkClicksDetailComponent>;
    const route = ({ data: of({ topicLinkClicks: new TopicLinkClicks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicLinkClicksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicLinkClicksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicLinkClicksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicLinkClicks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicLinkClicks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
