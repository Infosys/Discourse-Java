import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupsDetailComponent } from 'app/entities/groups/groups-detail.component';
import { Groups } from 'app/shared/model/groups.model';

describe('Component Tests', () => {
  describe('Groups Management Detail Component', () => {
    let comp: GroupsDetailComponent;
    let fixture: ComponentFixture<GroupsDetailComponent>;
    const route = ({ data: of({ groups: new Groups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groups on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
