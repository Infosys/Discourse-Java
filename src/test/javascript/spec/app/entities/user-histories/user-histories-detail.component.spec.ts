import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserHistoriesDetailComponent } from 'app/entities/user-histories/user-histories-detail.component';
import { UserHistories } from 'app/shared/model/user-histories.model';

describe('Component Tests', () => {
  describe('UserHistories Management Detail Component', () => {
    let comp: UserHistoriesDetailComponent;
    let fixture: ComponentFixture<UserHistoriesDetailComponent>;
    const route = ({ data: of({ userHistories: new UserHistories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserHistoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserHistoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserHistoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userHistories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userHistories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
