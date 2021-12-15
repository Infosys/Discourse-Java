import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserApiKeysDetailComponent } from 'app/entities/user-api-keys/user-api-keys-detail.component';
import { UserApiKeys } from 'app/shared/model/user-api-keys.model';

describe('Component Tests', () => {
  describe('UserApiKeys Management Detail Component', () => {
    let comp: UserApiKeysDetailComponent;
    let fixture: ComponentFixture<UserApiKeysDetailComponent>;
    const route = ({ data: of({ userApiKeys: new UserApiKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserApiKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserApiKeysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserApiKeysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userApiKeys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userApiKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
