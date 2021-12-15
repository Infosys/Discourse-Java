import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupsWebHooksDetailComponent } from 'app/entities/groups-web-hooks/groups-web-hooks-detail.component';
import { GroupsWebHooks } from 'app/shared/model/groups-web-hooks.model';

describe('Component Tests', () => {
  describe('GroupsWebHooks Management Detail Component', () => {
    let comp: GroupsWebHooksDetailComponent;
    let fixture: ComponentFixture<GroupsWebHooksDetailComponent>;
    const route = ({ data: of({ groupsWebHooks: new GroupsWebHooks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupsWebHooksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupsWebHooksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupsWebHooksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupsWebHooks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupsWebHooks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
