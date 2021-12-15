import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicViewsDetailComponent } from 'app/entities/topic-views/topic-views-detail.component';
import { TopicViews } from 'app/shared/model/topic-views.model';

describe('Component Tests', () => {
  describe('TopicViews Management Detail Component', () => {
    let comp: TopicViewsDetailComponent;
    let fixture: ComponentFixture<TopicViewsDetailComponent>;
    const route = ({ data: of({ topicViews: new TopicViews(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicViewsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicViewsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicViewsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicViews on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicViews).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
