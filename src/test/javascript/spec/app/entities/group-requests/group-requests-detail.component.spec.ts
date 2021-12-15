import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupRequestsDetailComponent } from 'app/entities/group-requests/group-requests-detail.component';
import { GroupRequests } from 'app/shared/model/group-requests.model';

describe('Component Tests', () => {
  describe('GroupRequests Management Detail Component', () => {
    let comp: GroupRequestsDetailComponent;
    let fixture: ComponentFixture<GroupRequestsDetailComponent>;
    const route = ({ data: of({ groupRequests: new GroupRequests(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupRequestsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupRequestsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupRequestsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupRequests on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupRequests).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
