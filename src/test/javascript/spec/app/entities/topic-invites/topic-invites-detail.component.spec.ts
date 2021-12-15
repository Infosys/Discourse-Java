import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicInvitesDetailComponent } from 'app/entities/topic-invites/topic-invites-detail.component';
import { TopicInvites } from 'app/shared/model/topic-invites.model';

describe('Component Tests', () => {
  describe('TopicInvites Management Detail Component', () => {
    let comp: TopicInvitesDetailComponent;
    let fixture: ComponentFixture<TopicInvitesDetailComponent>;
    const route = ({ data: of({ topicInvites: new TopicInvites(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicInvitesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicInvitesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicInvitesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicInvites on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicInvites).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
