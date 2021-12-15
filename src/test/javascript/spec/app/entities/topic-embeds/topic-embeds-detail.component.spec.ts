import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicEmbedsDetailComponent } from 'app/entities/topic-embeds/topic-embeds-detail.component';
import { TopicEmbeds } from 'app/shared/model/topic-embeds.model';

describe('Component Tests', () => {
  describe('TopicEmbeds Management Detail Component', () => {
    let comp: TopicEmbedsDetailComponent;
    let fixture: ComponentFixture<TopicEmbedsDetailComponent>;
    const route = ({ data: of({ topicEmbeds: new TopicEmbeds(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicEmbedsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicEmbedsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicEmbedsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicEmbeds on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicEmbeds).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
