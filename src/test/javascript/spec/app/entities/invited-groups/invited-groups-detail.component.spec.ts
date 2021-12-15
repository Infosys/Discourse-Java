import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitedGroupsDetailComponent } from 'app/entities/invited-groups/invited-groups-detail.component';
import { InvitedGroups } from 'app/shared/model/invited-groups.model';

describe('Component Tests', () => {
  describe('InvitedGroups Management Detail Component', () => {
    let comp: InvitedGroupsDetailComponent;
    let fixture: ComponentFixture<InvitedGroupsDetailComponent>;
    const route = ({ data: of({ invitedGroups: new InvitedGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitedGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvitedGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitedGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invitedGroups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitedGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
