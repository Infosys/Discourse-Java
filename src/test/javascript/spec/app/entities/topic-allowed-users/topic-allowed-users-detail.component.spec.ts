import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicAllowedUsersDetailComponent } from 'app/entities/topic-allowed-users/topic-allowed-users-detail.component';
import { TopicAllowedUsers } from 'app/shared/model/topic-allowed-users.model';

describe('Component Tests', () => {
  describe('TopicAllowedUsers Management Detail Component', () => {
    let comp: TopicAllowedUsersDetailComponent;
    let fixture: ComponentFixture<TopicAllowedUsersDetailComponent>;
    const route = ({ data: of({ topicAllowedUsers: new TopicAllowedUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicAllowedUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicAllowedUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicAllowedUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicAllowedUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicAllowedUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
