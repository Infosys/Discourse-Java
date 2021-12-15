import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicSearchDataDetailComponent } from 'app/entities/topic-search-data/topic-search-data-detail.component';
import { TopicSearchData } from 'app/shared/model/topic-search-data.model';

describe('Component Tests', () => {
  describe('TopicSearchData Management Detail Component', () => {
    let comp: TopicSearchDataDetailComponent;
    let fixture: ComponentFixture<TopicSearchDataDetailComponent>;
    const route = ({ data: of({ topicSearchData: new TopicSearchData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicSearchDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicSearchDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicSearchDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicSearchData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicSearchData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
