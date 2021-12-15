import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserIpAddressHistoriesDetailComponent } from 'app/entities/user-ip-address-histories/user-ip-address-histories-detail.component';
import { UserIpAddressHistories } from 'app/shared/model/user-ip-address-histories.model';

describe('Component Tests', () => {
  describe('UserIpAddressHistories Management Detail Component', () => {
    let comp: UserIpAddressHistoriesDetailComponent;
    let fixture: ComponentFixture<UserIpAddressHistoriesDetailComponent>;
    const route = ({ data: of({ userIpAddressHistories: new UserIpAddressHistories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserIpAddressHistoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserIpAddressHistoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserIpAddressHistoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userIpAddressHistories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userIpAddressHistories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
