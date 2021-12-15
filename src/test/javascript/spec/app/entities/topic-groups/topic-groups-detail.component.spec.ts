import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicGroupsDetailComponent } from 'app/entities/topic-groups/topic-groups-detail.component';
import { TopicGroups } from 'app/shared/model/topic-groups.model';

describe('Component Tests', () => {
  describe('TopicGroups Management Detail Component', () => {
    let comp: TopicGroupsDetailComponent;
    let fixture: ComponentFixture<TopicGroupsDetailComponent>;
    const route = ({ data: of({ topicGroups: new TopicGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
