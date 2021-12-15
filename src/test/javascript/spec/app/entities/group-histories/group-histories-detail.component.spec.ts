import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupHistoriesDetailComponent } from 'app/entities/group-histories/group-histories-detail.component';
import { GroupHistories } from 'app/shared/model/group-histories.model';

describe('Component Tests', () => {
  describe('GroupHistories Management Detail Component', () => {
    let comp: GroupHistoriesDetailComponent;
    let fixture: ComponentFixture<GroupHistoriesDetailComponent>;
    const route = ({ data: of({ groupHistories: new GroupHistories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupHistoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupHistoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupHistoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupHistories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupHistories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
