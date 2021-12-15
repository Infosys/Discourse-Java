import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicLinksDetailComponent } from 'app/entities/topic-links/topic-links-detail.component';
import { TopicLinks } from 'app/shared/model/topic-links.model';

describe('Component Tests', () => {
  describe('TopicLinks Management Detail Component', () => {
    let comp: TopicLinksDetailComponent;
    let fixture: ComponentFixture<TopicLinksDetailComponent>;
    const route = ({ data: of({ topicLinks: new TopicLinks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicLinksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicLinksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicLinksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicLinks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicLinks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
