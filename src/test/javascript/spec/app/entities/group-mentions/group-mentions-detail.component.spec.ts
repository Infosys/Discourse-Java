import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupMentionsDetailComponent } from 'app/entities/group-mentions/group-mentions-detail.component';
import { GroupMentions } from 'app/shared/model/group-mentions.model';

describe('Component Tests', () => {
  describe('GroupMentions Management Detail Component', () => {
    let comp: GroupMentionsDetailComponent;
    let fixture: ComponentFixture<GroupMentionsDetailComponent>;
    const route = ({ data: of({ groupMentions: new GroupMentions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupMentionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupMentionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupMentionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupMentions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupMentions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
