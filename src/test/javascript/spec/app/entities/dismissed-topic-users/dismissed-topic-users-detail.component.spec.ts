import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DismissedTopicUsersDetailComponent } from 'app/entities/dismissed-topic-users/dismissed-topic-users-detail.component';
import { DismissedTopicUsers } from 'app/shared/model/dismissed-topic-users.model';

describe('Component Tests', () => {
  describe('DismissedTopicUsers Management Detail Component', () => {
    let comp: DismissedTopicUsersDetailComponent;
    let fixture: ComponentFixture<DismissedTopicUsersDetailComponent>;
    const route = ({ data: of({ dismissedTopicUsers: new DismissedTopicUsers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DismissedTopicUsersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DismissedTopicUsersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DismissedTopicUsersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dismissedTopicUsers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dismissedTopicUsers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
