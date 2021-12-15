import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserOpenIdsDetailComponent } from 'app/entities/user-open-ids/user-open-ids-detail.component';
import { UserOpenIds } from 'app/shared/model/user-open-ids.model';

describe('Component Tests', () => {
  describe('UserOpenIds Management Detail Component', () => {
    let comp: UserOpenIdsDetailComponent;
    let fixture: ComponentFixture<UserOpenIdsDetailComponent>;
    const route = ({ data: of({ userOpenIds: new UserOpenIds(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserOpenIdsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserOpenIdsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserOpenIdsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userOpenIds on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userOpenIds).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
