import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicThumbnailsDetailComponent } from 'app/entities/topic-thumbnails/topic-thumbnails-detail.component';
import { TopicThumbnails } from 'app/shared/model/topic-thumbnails.model';

describe('Component Tests', () => {
  describe('TopicThumbnails Management Detail Component', () => {
    let comp: TopicThumbnailsDetailComponent;
    let fixture: ComponentFixture<TopicThumbnailsDetailComponent>;
    const route = ({ data: of({ topicThumbnails: new TopicThumbnails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicThumbnailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicThumbnailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicThumbnailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicThumbnails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicThumbnails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
