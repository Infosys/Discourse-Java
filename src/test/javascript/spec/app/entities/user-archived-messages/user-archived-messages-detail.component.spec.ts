import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserArchivedMessagesDetailComponent } from 'app/entities/user-archived-messages/user-archived-messages-detail.component';
import { UserArchivedMessages } from 'app/shared/model/user-archived-messages.model';

describe('Component Tests', () => {
  describe('UserArchivedMessages Management Detail Component', () => {
    let comp: UserArchivedMessagesDetailComponent;
    let fixture: ComponentFixture<UserArchivedMessagesDetailComponent>;
    const route = ({ data: of({ userArchivedMessages: new UserArchivedMessages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserArchivedMessagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserArchivedMessagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserArchivedMessagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userArchivedMessages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userArchivedMessages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
