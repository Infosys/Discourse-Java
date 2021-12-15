import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TopicUsersDetailComponent } from 'app/entities/topic-users/topic-users-detail.component';
import { TopicUsers } from 'app/shared/model/topic-users.model';

describe('Component Tests', () => {
  describe('TopicUsers Management Detail Component', () => {
    let comp: TopicUsersDetailComponent;
    let fixture: ComponentFixture<TopicUsersDetailComponent>;
    const route = ({ data: of({ topicUsers: new TopicUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TopicUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopicUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topicUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topicUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
