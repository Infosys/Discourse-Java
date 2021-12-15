import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicTagsDetailComponent } from 'app/entities/topic-tags/topic-tags-detail.component';
import { TopicTags } from 'app/shared/model/topic-tags.model';

describe('Component Tests', () => {
  describe('TopicTags Management Detail Component', () => {
    let comp: TopicTagsDetailComponent;
    let fixture: ComponentFixture<TopicTagsDetailComponent>;
    const route = ({ data: of({ topicTags: new TopicTags(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicTagsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicTagsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicTagsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicTags on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicTags).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
