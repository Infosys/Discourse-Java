import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { Oauth2UserInfosDetailComponent } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos-detail.component';
import { Oauth2UserInfos } from 'app/shared/model/oauth-2-user-infos.model';

describe('Component Tests', () => {
  describe('Oauth2UserInfos Management Detail Component', () => {
    let comp: Oauth2UserInfosDetailComponent;
    let fixture: ComponentFixture<Oauth2UserInfosDetailComponent>;
    const route = ({ data: of({ oauth2UserInfos: new Oauth2UserInfos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [Oauth2UserInfosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(Oauth2UserInfosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Oauth2UserInfosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load oauth2UserInfos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.oauth2UserInfos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
