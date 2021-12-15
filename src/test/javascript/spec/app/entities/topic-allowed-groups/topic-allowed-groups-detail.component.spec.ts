import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicAllowedGroupsDetailComponent } from 'app/entities/topic-allowed-groups/topic-allowed-groups-detail.component';
import { TopicAllowedGroups } from 'app/shared/model/topic-allowed-groups.model';

describe('Component Tests', () => {
  describe('TopicAllowedGroups Management Detail Component', () => {
    let comp: TopicAllowedGroupsDetailComponent;
    let fixture: ComponentFixture<TopicAllowedGroupsDetailComponent>;
    const route = ({ data: of({ topicAllowedGroups: new TopicAllowedGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicAllowedGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicAllowedGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicAllowedGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicAllowedGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicAllowedGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
