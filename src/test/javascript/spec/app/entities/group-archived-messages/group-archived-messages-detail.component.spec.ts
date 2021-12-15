import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupArchivedMessagesDetailComponent } from 'app/entities/group-archived-messages/group-archived-messages-detail.component';
import { GroupArchivedMessages } from 'app/shared/model/group-archived-messages.model';

describe('Component Tests', () => {
  describe('GroupArchivedMessages Management Detail Component', () => {
    let comp: GroupArchivedMessagesDetailComponent;
    let fixture: ComponentFixture<GroupArchivedMessagesDetailComponent>;
    const route = ({ data: of({ groupArchivedMessages: new GroupArchivedMessages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupArchivedMessagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupArchivedMessagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupArchivedMessagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupArchivedMessages on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupArchivedMessages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
