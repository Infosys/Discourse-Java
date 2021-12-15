import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSecurityKeysDetailComponent } from 'app/entities/user-security-keys/user-security-keys-detail.component';
import { UserSecurityKeys } from 'app/shared/model/user-security-keys.model';

describe('Component Tests', () => {
  describe('UserSecurityKeys Management Detail Component', () => {
    let comp: UserSecurityKeysDetailComponent;
    let fixture: ComponentFixture<UserSecurityKeysDetailComponent>;
    const route = ({ data: of({ userSecurityKeys: new UserSecurityKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSecurityKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserSecurityKeysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserSecurityKeysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userSecurityKeys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userSecurityKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
