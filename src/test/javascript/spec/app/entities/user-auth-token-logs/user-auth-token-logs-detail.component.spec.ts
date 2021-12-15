import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAuthTokenLogsDetailComponent } from 'app/entities/user-auth-token-logs/user-auth-token-logs-detail.component';
import { UserAuthTokenLogs } from 'app/shared/model/user-auth-token-logs.model';

describe('Component Tests', () => {
  describe('UserAuthTokenLogs Management Detail Component', () => {
    let comp: UserAuthTokenLogsDetailComponent;
    let fixture: ComponentFixture<UserAuthTokenLogsDetailComponent>;
    const route = ({ data: of({ userAuthTokenLogs: new UserAuthTokenLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAuthTokenLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAuthTokenLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAuthTokenLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAuthTokenLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAuthTokenLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
