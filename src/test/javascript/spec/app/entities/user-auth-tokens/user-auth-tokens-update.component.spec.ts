import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAuthTokensUpdateComponent } from 'app/entities/user-auth-tokens/user-auth-tokens-update.component';
import { UserAuthTokensService } from 'app/entities/user-auth-tokens/user-auth-tokens.service';
import { UserAuthTokens } from 'app/shared/model/user-auth-tokens.model';

describe('Component Tests', () => {
  describe('UserAuthTokens Management Update Component', () => {
    let comp: UserAuthTokensUpdateComponent;
    let fixture: ComponentFixture<UserAuthTokensUpdateComponent>;
    let service: UserAuthTokensService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAuthTokensUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAuthTokensUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAuthTokensUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAuthTokensService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAuthTokens(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAuthTokens();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
